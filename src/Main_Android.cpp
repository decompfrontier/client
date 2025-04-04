#include "Pch.hpp"

#ifdef __ANDROID__
#include "Tapjoy.hpp"
#include "PTRateThisAppPopup.hpp"
#include "NativeCallbackHandler.hpp"
#include "AsyncFileLoad.hpp"
#include "WrapAsyncFileLoad.hpp"

extern "C"
{
    JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved)
    {
        cocos2d::JniHelper::setJavaVM(vm);
        tapjoy::Tapjoy::setJavaVM(vm);
        return JNI_VERSION_1_4;
    }

    JNIEXPORT void JNICALL Java_jp_co_alim_brave_BraveFrontierJNI_setMultiInvateSchemeData(JNIEnv *env, jobject thiz) {}

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_BraveFrontierJNI_backButtonCallback(JNIEnv* env, jobject thiz)
    {
        NativeCallbackHandler::sharedHandler()->onBackButtonCalled();
    }

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_BraveFrontierJNI_nativeRateThisAppPopupCallback(JNIEnv* env, jobject thiz, jint rated)
    {
        PTRateThisAppPopup::alertCloseCallback(rated, thiz);
    }

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_BraveFrontierJNI_nativeDownloadCallback(JNIEnv* env, jobject thiz, jlong obj, jbyteArray data, jstring error)
    {
        AsyncFileLoad* fl = (AsyncFileLoad*)obj; // what the fuck, seriously what the fuck!!!!
        jbyte* elements;
        bool isInError;
        WrapAsyncFileLoad* wfl = /*(WrapAsyncFileLoad*)fl->getUserObj()*/;
        const char* errorStr = NULL;
        bool gotElements = false;
        jboolean copy;


        if (error) {
            errorStr = env->GetStringUTFChars(error, NULL);
        }

        copy = JNI_FALSE;

        if (data)
        {
            jbyte* el = env->GetByteArrayElements(data, &copy);
            fl->finish();      
            
            if (el)
            {
                
                if (errorStr) {
                    isInError = *errorStr != '\0';
                } else {
                    isInError = false;
                }
                fl->setError(isInError);

                elements = el;
                
                jsize elementsLen = env->GetArrayLength(data);

                gotElements = true;
                if (!fl->isError())
                {
                    wfl->connectionDidFinishLoading(elementsLen, el);
                }
            }
            
        }
        else
        {
            fl->finish();
        }

        if (fl->isError())
        {
            fl->setError(true);

            const char* wrapErrorMsg;
            if (!errorStr || !*errorStr)
            {
                wrapErrorMsg = "Download Error";
            }
            else
            {
                wrapErrorMsg = errorStr;
            }

            wfl->connectionDidFailWithError(wrapErrorMsg);    
        }

        if (data && gotElements) {
            env->ReleaseByteArrayElements(data, elements, JNI_ABORT);
        }
        if (error && errorStr) {
            env->ReleaseStringUTFChars(error, errorStr);
        }
    }
}

#endif
