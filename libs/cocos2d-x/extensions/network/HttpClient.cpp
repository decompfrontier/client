/****************************************************************************
 Copyright (c) 2010-2012 cocos2d-x.org
 Copyright (c) 2012 greathqy
 
 http://www.cocos2d-x.org
 
 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:
 
 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.
 
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
 ****************************************************************************/

#include "HttpClient.h"
// #include "platform/CCThread.h"

#include <queue>
#include <pthread.h>
#include <semaphore.h>
#include <errno.h>

#include "curl/curl.h"

NS_CC_EXT_BEGIN

static pthread_t        s_networkThread;
static pthread_mutex_t  s_requestQueueMutex;
static pthread_mutex_t  s_responseQueueMutex;
static sem_t *          s_pSem = NULL;
static unsigned long    s_asyncRequestCount = 0;

// __DECOMP__ custom code!
static const char* caCertPemDirectory = NULL;
static int downloadProgress = 0;
static time_t downloadTimerAfterInst = 0;
static long g_lastBufferSize = 0;
static double g_dlNowCheck = 0.0;

#if CC_TARGET_PLATFORM == CC_PLATFORM_IOS
#define CC_ASYNC_HTTPREQUEST_USE_NAMED_SEMAPHORE 1
#else
#define CC_ASYNC_HTTPREQUEST_USE_NAMED_SEMAPHORE 0
#endif

#if CC_ASYNC_HTTPREQUEST_USE_NAMED_SEMAPHORE
#define CC_ASYNC_HTTPREQUEST_SEMAPHORE "ccHttpAsync"
#else
static sem_t s_sem;
#endif

#if (CC_TARGET_PLATFORM == CC_PLATFORM_WIN32)
typedef int int32_t;
#endif

static bool need_quit = false;

static CCArray* s_requestQueue = NULL;
static CCArray* s_responseQueue = NULL;

static CCHttpClient *s_pHttpClient = NULL; // pointer to singleton

static char s_errorBuffer[CURL_ERROR_SIZE];

typedef size_t (*write_callback)(void *ptr, size_t size, size_t nmemb, void *stream);


// Callback function used by libcurl for collect response data
size_t writeData(void *ptr, size_t size, size_t nmemb, void *stream)
{
    std::vector<char> *recvBuffer = (std::vector<char>*)stream;
    size_t sizes = size * nmemb;
    
    // add data to the end of recvBuffer
    // write data maybe called more than once in a single request
    recvBuffer->insert(recvBuffer->end(), (char*)ptr, (char*)ptr+sizes);
    
    return sizes;
}

size_t writeFileData(void* ptr, size_t size, size_t nmemb, void* stream)
{
    return fwrite(ptr, size, nmemb, (FILE*)stream);
}

int progress_callback(void* clientp,
    double dltotal,
    double dlnow,
    double ultotal,
    double ulnow)
{
    if (dltotal != 0.0)
    {
        if (g_dlNowCheck != dlnow)
        {
            g_dlNowCheck = dlnow;
            downloadProgress = (int)(dlnow + g_lastBufferSize) / (dltotal + g_lastBufferSize);
            downloadTimerAfterInst = clock();
        }

        auto currentTime = clock();
        if (g_dlNowCheck == 0.0)
        {
            downloadTimerAfterInst = currentTime;
        }

        auto timerProg = (currentTime - downloadTimerAfterInst) / 1000000.0;

        if (timerProg <= 25.0)
            return 0;

        return 1;
    }

    return 0;
}

// Prototypes
bool configureCURL(CURL *handle);
int processGetTask(CCHttpRequest *request, write_callback callback, void *stream, int32_t *errorCode);
int processPostTask(CCHttpRequest *request, write_callback callback, void *stream, int32_t *errorCode);
//int processDownloadTask(HttpRequest *task, write_callback callback, void *stream, int32_t *errorCode);
int processFileTask(CCHttpRequest* request, write_callback callback, void* stream, int32_t* errorCode);

// Worker thread
static void* networkThread(void *data)
{    
    CCHttpRequest *request = NULL;
    
    while (true) 
    {
// __DECOMP__: bf doesn't have the semaphore code, but I don't really mind...
// 
        // Wait for http request tasks from main thread
        int semWaitRet = sem_wait(s_pSem);
        if (semWaitRet < 0) {
            CCLog("HttpRequest async thread semaphore error: %s\n", strerror(errno));
            break;
        }

        if (need_quit)
        {
            break;
        }
        
        // step 1: send http request if the requestQueue isn't empty
        request = NULL;
        
        pthread_mutex_lock(&s_requestQueueMutex); //Get request task from queue
        if (0 != s_requestQueue->count())
        {
            request = dynamic_cast<CCHttpRequest*>(s_requestQueue->objectAtIndex(0));
            s_requestQueue->removeObjectAtIndex(0);  
            // request's refcount = 1 here
        }
        pthread_mutex_unlock(&s_requestQueueMutex);
        
        if (NULL == request)
        {
            continue;
        }
        
        // step 2: libcurl sync access
        
        // Create a HttpResponse object, the default setting is http access failed
        CCHttpResponse *response = new CCHttpResponse(request);
        
        // request's refcount = 2 here, it's retained by HttpRespose constructor
        request->release();
        // ok, refcount = 1 now, only HttpResponse hold it.
        
        int responseCode = -1;
        int retValue = 0;

        // Process the request -> get response packet
        switch (request->getRequestType())
        {
            case CCHttpRequest::kHttpGet: // HTTP GET
                retValue = processGetTask(request, 
                                          writeData, 
                                          response->getResponseData(), 
                                          &responseCode);
                break;
            
            case CCHttpRequest::kHttpPost: // HTTP POST
                retValue = processPostTask(request, 
                                           writeData, 
                                           response->getResponseData(), 
                                           &responseCode);
                break;
            
            // __DECOMP__
            case CCHttpRequest::kHttpUnkown:
                retValue = processFileTask(request, writeFileData, response->getResponseData(), &responseCode);
                break;

            default:
                CCAssert(true, "CCHttpClient: unkown request type, only GET and POSt are supported");
                break;
        }
                
        // write data to HttpResponse
        response->setResponseCode(responseCode);
        
        if (retValue != 0) 
        {
            response->setSucceed(false);
            response->setErrorBuffer(s_errorBuffer);
        }
        else
        {
            response->setSucceed(true);
        }

        
        // add response packet into queue
        pthread_mutex_lock(&s_responseQueueMutex);
        s_responseQueue->addObject(response);
        pthread_mutex_unlock(&s_responseQueueMutex);
        
        // resume dispatcher selector
        CCDirector::sharedDirector()->getScheduler()->resumeTarget(CCHttpClient::getInstance());
    }
    
    // cleanup: if worker thread received quit signal, clean up un-completed request queue
    pthread_mutex_lock(&s_requestQueueMutex);
    s_requestQueue->removeAllObjects();
    pthread_mutex_unlock(&s_requestQueueMutex);
    s_asyncRequestCount -= s_requestQueue->count();
    
    if (s_pSem != NULL) {
#if CC_ASYNC_HTTPREQUEST_USE_NAMED_SEMAPHORE
        sem_unlink(CC_ASYNC_HTTPREQUEST_SEMAPHORE);
        sem_close(s_pSem);
#else
        sem_destroy(s_pSem);
#endif
        
        s_pSem = NULL;
        
        pthread_mutex_destroy(&s_requestQueueMutex);
        pthread_mutex_destroy(&s_responseQueueMutex);
        
        s_requestQueue->release();
        s_responseQueue->release();
    }

    pthread_exit(NULL);
    
    return 0;
}

//Configure curl's timeout property
bool configureCURL(CURL *handle)
{
    if (!handle) {
        return false;
    }
    
    int32_t code;
    code = curl_easy_setopt(handle, CURLOPT_ERRORBUFFER, s_errorBuffer);
    if (code != CURLE_OK) {
        return false;
    }
    code = curl_easy_setopt(handle, CURLOPT_TIMEOUT, CCHttpClient::getInstance()->getTimeoutForRead());
    if (code != CURLE_OK) {
        return false;
    }
    code = curl_easy_setopt(handle, CURLOPT_CONNECTTIMEOUT, CCHttpClient::getInstance()->getTimeoutForConnect());
    if (code != CURLE_OK) {
        return false;
    }

    /* BRAVE FRONTIER CUSTOM CODE (__DECOMP__) */

    code = curl_easy_setopt(handle, CURLOPT_NOSIGNAL, 1);
    if (code != CURLE_OK) {
        return false;
    }

    code = curl_easy_setopt(handle, CURLOPT_ACCEPT_ENCODING, "gzip");
    if (code != CURLE_OK) {
        return false;
    }

#ifdef _DEBUG
    code = curl_easy_setopt(handle, CURLOPT_SSL_VERIFYHOST, 0);
#else
    code = curl_easy_setopt(handle, CURLOPT_SSL_VERIFYHOST, 2);
#endif
    if (code != CURLE_OK) {
        return false;
    }

    code = curl_easy_setopt(handle, CURLOPT_SSL_VERIFYPEER, 0);
    if (code != CURLE_OK) {
        return false;
    }

    code = curl_easy_setopt(handle, CURLOPT_CAINFO, nullptr);
    if (code != CURLE_OK) {
        return false;
    }

    code = curl_easy_setopt(handle, CURLOPT_CAPATH, nullptr);
    if (code != CURLE_OK) {
        return false;
    }


#ifdef _DEBUG
    code = curl_easy_setopt(handle, CURLOPT_SSL_VERIFYPEER, 0);
#else
    code = curl_easy_setopt(handle, CURLOPT_SSL_VERIFYPEER, 1);
#endif
    if (code != CURLE_OK) {
        return false;
    }

    code = curl_easy_setopt(handle, CURLOPT_SSLCERTTYPE, "PEM");
    if (code != CURLE_OK) {
        return false;
    }

    const auto provider = CCHttpClient::getInstance()->getCertificateProvider();
    if (provider)
    {
        code = curl_easy_setopt(handle, CURLOPT_SSL_CTX_FUNCTION, provider->getSSLCTXFunction());
        if (code != CURLE_OK) {
            return false;
        }
    }
    else
        return false;

    /* END OF BRAVE FRONTIER CUSTOM CODE */
    
    return true;
}

//Process Get Request
int processGetTask(CCHttpRequest *request, write_callback callback, void *stream, int *responseCode)
{
    CURLcode code = CURL_LAST;
    CURL *curl = curl_easy_init();
    curl_slist* slist = nullptr;

    do {
        if (!configureCURL(curl)) 
        {
            break;
        }

        // __DECOMP__
        

        for (auto& req : request->getHeaders())
        {
            slist = curl_slist_append(slist, req.c_str());
        }

        code = curl_easy_setopt(curl, CURLOPT_HTTPHEADER, slist);
        if (code != CURLE_OK)
        {
            curl_slist_free_all(slist);
            break;
        }
        
        code = curl_easy_setopt(curl, CURLOPT_URL, request->getUrl());
        if (code != CURLE_OK) 
        {
            break;
        }
        
        code = curl_easy_setopt(curl, CURLOPT_FOLLOWLOCATION, request->getUrl());
        if (code != CURLE_OK)
        {
            break;
        }

        code = curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, callback);
        if (code != CURLE_OK) 
        {
            break;
        }
        
        code = curl_easy_setopt(curl, CURLOPT_WRITEDATA, stream);
        if (code != CURLE_OK) 
        {
            break;
        }
        code = curl_easy_perform(curl);
        if (code != CURLE_OK) 
        {
            break;
        }
        
        code = curl_easy_getinfo(curl, CURLINFO_RESPONSE_CODE, responseCode); 
        if (code != CURLE_OK || *responseCode != 200) 
        {
            code = CURLE_HTTP_RETURNED_ERROR;
        }
    } while (0);
    if (slist)
        curl_slist_free_all(slist);
    if (curl) {
        curl_easy_cleanup(curl);
    }
    
    return (code == CURLE_OK ? 0 : 1);
}

//Process POST Request
int processPostTask(CCHttpRequest *request, write_callback callback, void *stream, int32_t *responseCode)
{
    CURLcode code = CURL_LAST;
    CURL *curl = curl_easy_init();
    curl_slist* slist = nullptr;

    do {
        if (!configureCURL(curl)) {
            break;
        }

        // __DECOMP__
        for (auto& req : request->getHeaders())
        {
            slist = curl_slist_append(slist, req.c_str());
        }

        code = curl_easy_setopt(curl, CURLOPT_HTTPHEADER, slist);
        if (code != CURLE_OK)
        {
            curl_slist_free_all(slist);
            break;
        }
        
        code = curl_easy_setopt(curl, CURLOPT_URL, request->getUrl());
        if (code != CURLE_OK) {
            break;
        }
        code = curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, callback);
        if (code != CURLE_OK) {
            break;
        }
        code = curl_easy_setopt(curl, CURLOPT_WRITEDATA, stream);
        if (code != CURLE_OK) {
            break;
        }
        code = curl_easy_setopt(curl, CURLOPT_POST, 1);
        if (code != CURLE_OK) {
            break;
        }
        code = curl_easy_setopt(curl, CURLOPT_POSTFIELDS, request->getRequestData());
        if (code != CURLE_OK) {
            break;
        }
        code = curl_easy_setopt(curl, CURLOPT_POSTFIELDSIZE, request->getRequestDataSize());
        if (code != CURLE_OK) {
            break;
        }
        code = curl_easy_perform(curl);
        if (code != CURLE_OK) {
            break;
        }
        
        code = curl_easy_getinfo(curl, CURLINFO_RESPONSE_CODE, responseCode); 
        if (code != CURLE_OK || *responseCode != 200) {
            code = CURLE_HTTP_RETURNED_ERROR;
        }
    } while (0);
    if (slist)
        curl_slist_free_all(slist);
    if (curl) {
        curl_easy_cleanup(curl);        
    }
    
    return (code == CURLE_OK ? 0 : 1);    
}

int processFileTask(CCHttpRequest* request, write_callback callback, void* stream, int32_t* responseCode)
{
    CURLcode code = CURL_LAST;
    CURL* curl = curl_easy_init();
    FILE* fpx = nullptr;

    do {
        int32_t code;
        code = curl_easy_setopt(curl, CURLOPT_ERRORBUFFER, s_errorBuffer);
        if (code != CURLE_OK) {
            return false;
        }
        code = curl_easy_setopt(curl, CURLOPT_TIMEOUT, 0);
        if (code != CURLE_OK) {
            return false;
        }
        code = curl_easy_setopt(curl, CURLOPT_LOW_SPEED_LIMIT, 10);
        if (code != CURLE_OK) {
            return false;
        }
        code = curl_easy_setopt(curl, CURLOPT_LOW_SPEED_TIME, 10);
        if (code != CURLE_OK) {
            return false;
        }
        code = curl_easy_setopt(curl, CURLOPT_NOPROGRESS, 0);
        if (code != CURLE_OK) {
            return false;
        }
        code = curl_easy_setopt(curl, CURLOPT_PROGRESSFUNCTION, progressfunc);
        if (code != CURLE_OK) {
            return false;
        }

        code = curl_easy_setopt(curl, CURLOPT_URL, request->getUrl());
        if (code != CURLE_OK) {
            break;
        }
        code = curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, callback);
        if (code != CURLE_OK) {
            break;
        }
        code = curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, callback);
        if (code != CURLE_OK) {
            break;
        }

        struct stat ss;
        stat(request->getRequestData(), &ss);
        g_lastBufferSize = ss.st_size;

        code = curl_easy_setopt(curl, CURLOPT_RESUME_FROM, g_lastBufferSize);
        if (code != CURLE_OK) {
            break;
        }
        fpx = fopen(request->getRequestData(), "ab");
        code = curl_easy_setopt(curl, CURLOPT_FILE, fpx);
        if (code != CURLE_OK) {
            break;
        }
        code = curl_easy_perform(curl);
        if (code != CURLE_OK) {
            if (code == CURLE_BAD_DOWNLOAD_RESUME || code == CURLE_RANGE_ERROR)
            {
                // NOT FROM DECOMP BUT WELL....
                fclose(fpx);
                fpx = nullptr;

                remove(request->getRequestData());
                break;
            }

            code = CURLE_HTTP_RETURNED_ERROR;
        }
    } while (0);
    if (fpx)
        fclose(fpx);
    if (curl) {
        curl_easy_cleanup(curl);
        downloadTimerAfterInst = 0;
    }

    return (code == CURLE_OK ? 0 : 1);
}

// HttpClient implementation
CCHttpClient* CCHttpClient::getInstance()
{
    if (s_pHttpClient == NULL) {
        s_pHttpClient = new CCHttpClient();
        curl_global_init(CURL_GLOBAL_ALL); // __DECOMP__ BF custom data
    }
    
    return s_pHttpClient;
}

void CCHttpClient::destroyInstance()
{
    CCDirector::sharedDirector()->getScheduler()->unscheduleSelector(schedule_selector(CCHttpClient::dispatchResponseCallbacks), 
                                                                     CCHttpClient::getInstance());
    CC_SAFE_RELEASE_NULL(s_pHttpClient);
}

CCHttpClient::CCHttpClient()
:_timeoutForRead(60)
,_timeoutForConnect(30)
{
    CCDirector::sharedDirector()->getScheduler()->scheduleSelector(
                    schedule_selector(CCHttpClient::dispatchResponseCallbacks), this, 0, false);
    CCDirector::sharedDirector()->getScheduler()->pauseTarget(this);

    // __DECOMP__ -> BRAVE FRONTIER CUSTOM CODE
    caCertPemDirectory = CCFileUtils::sharedFileUtils()->fullPathFromRelativePath("cacert.pem");
}

// __DECOMP__
int CCHttpClient::getProgressOfDownload()
{
    return downloadProgress;
}

void CCHttpClient::setDownloadTimerAfterInterruption()
{
    downloadTimerAfterInst = clock();
}

CCHttpClient::~CCHttpClient()
{
    need_quit = true;
    
    if (s_pSem != NULL) {
        sem_post(s_pSem);
    }
    
    CCDirector::sharedDirector()->getScheduler()->unscheduleSelector(schedule_selector(CCHttpClient::dispatchResponseCallbacks), this);
}

//Lazy create semaphore & mutex & thread
bool CCHttpClient::lazyInitThreadSemphore()
{
    if (s_pSem != NULL) {
        return true;
    } else {
#if CC_ASYNC_HTTPREQUEST_USE_NAMED_SEMAPHORE
        s_pSem = sem_open(CC_ASYNC_HTTPREQUEST_SEMAPHORE, O_CREAT, 0644, 0);
        if (s_pSem == SEM_FAILED) {
            CCLog("Open HttpRequest Semaphore failed");
            s_pSem = NULL;
            return false;
        }
#else
        int semRet = sem_init(&s_sem, 0, 0);
        if (semRet < 0) {
            CCLog("Init HttpRequest Semaphore failed");
            return false;
        }
        
        s_pSem = &s_sem;
#endif

        s_requestQueue = new CCArray();
        s_requestQueue->init();
        
        s_responseQueue = new CCArray();
        s_responseQueue->init();
        
        pthread_mutex_init(&s_requestQueueMutex, NULL);
        pthread_mutex_init(&s_responseQueueMutex, NULL);
        
        pthread_create(&s_networkThread, NULL, networkThread, NULL);
        pthread_detach(s_networkThread);
        
        need_quit = false;
    }
    
    return true;
}

//Add a get task to queue
void CCHttpClient::send(CCHttpRequest* request)
{    
    if (false == lazyInitThreadSemphore()) 
    {
        return;
    }
    
    if (!request)
    {
        return;
    }
        
    ++s_asyncRequestCount;
    
    request->retain();
        
    pthread_mutex_lock(&s_requestQueueMutex);
    s_requestQueue->addObject(request);
    pthread_mutex_unlock(&s_requestQueueMutex);
    
    // Notify thread start to work
    sem_post(s_pSem);
}

// Poll and notify main thread if responses exists in queue
void CCHttpClient::dispatchResponseCallbacks(float delta)
{
    // CCLog("CCHttpClient::dispatchResponseCallbacks is running");
    
    CCHttpResponse* response = NULL;
    
    pthread_mutex_lock(&s_responseQueueMutex);
    if (s_responseQueue->count())
    {
        response = dynamic_cast<CCHttpResponse*>(s_responseQueue->objectAtIndex(0));
        s_responseQueue->removeObjectAtIndex(0);
    }
    pthread_mutex_unlock(&s_responseQueueMutex);
    
    if (response)
    {
        --s_asyncRequestCount;
        
        CCHttpRequest *request = response->getHttpRequest();
        CCObject *pTarget = request->getTarget();
        SEL_CallFuncND pSelector = request->getSelector();

        if (pTarget && pSelector) 
        {
            (pTarget->*pSelector)((CCNode *)this, response);
        }
        
        response->release();
    }
    
    if (0 == s_asyncRequestCount) 
    {
        CCDirector::sharedDirector()->getScheduler()->pauseTarget(this);
    }
    
}

NS_CC_EXT_END


