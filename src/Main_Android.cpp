#include "Pch.hpp"

#ifdef __ANDROID__
#include "Tapjoy.hpp"
#include "PTRateThisAppPopup.hpp"
#include "NativeCallbackHandler.hpp"
#include "AsyncFileLoad.hpp"
#include "WrapAsyncFileLoad.hpp"
#include "WebViewScene.hpp"

extern "C"
{
    JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved)
    {
        cocos2d::JniHelper::setJavaVM(vm);
        tapjoy::Tapjoy::setJavaVM(vm);
        return JNI_VERSION_1_4;
    }

    JNIEXPORT void JNICALL Java_jp_co_alim_brave_BraveFrontierJNI_setMultiInvateSchemeData(JNIEnv *env, jobject thiz) {}

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_BraveFrontierJNI_nativeRateThisAppPopupCallback(JNIEnv* env, jobject thiz, jint rated)
    {
        PTRateThisAppPopup::alertCloseCallback(rated, thiz);
    }

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_BraveFrontierJNI_nativeDownloadCallback(JNIEnv* env, jobject thiz, jlong obj, jbyteArray data, jstring error)
    {
        AsyncFileLoad* fl = (AsyncFileLoad*)obj; // what the fuck, seriously what the fuck!!!!
        jbyte* elements;
        bool isInError;
        const char* errorStr = NULL;
        bool gotElements = false;
        jboolean copy;
        WrapAsyncFileLoad* wfl;

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
                elements = el;

                if (errorStr) {
                    isInError = *errorStr != '\0';
                } else {
                    isInError = false;
                }

                WrapAsyncFileLoad* wfl = (WrapAsyncFileLoad*)fl->getUserObj();
                fl->setError(isInError);
                jsize elementsLen = env->GetArrayLength(data);

                if (!fl->isError())
                {
                    wfl->connectionDidFinishLoading(elementsLen, el);
                    gotElements = true;
                }
                else
                {
                    gotElements = true;
                    fl->finish();
                }
            } 
        }
        else
        {
            fl->finish();
            wfl = (WrapAsyncFileLoad*)fl->getUserObj();
            elements = NULL;
            gotElements = false;
        }

        if (fl->isError())
        {
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

        fl->releaseObj();

        if (data && gotElements) {
            env->ReleaseByteArrayElements(data, elements, JNI_ABORT);
        }

        if (error && errorStr) {
            env->ReleaseStringUTFChars(error, errorStr);
        }
    }

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_BraveFrontierJNI_videoFinishedCallback(JNIEnv* env, jobject thiz)
    {
        NativeCallbackHandler::sharedHandler()->onVideoFinished();
    }

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_BraveFrontierJNI_videoSkippedCallback(JNIEnv* env, jobject thiz)
    {
        NativeCallbackHandler::sharedHandler()->onVideoSkipped();
    }

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_BraveFrontierJNI_videoPreparedCallback(JNIEnv* env, jobject thiz)
    {
        NativeCallbackHandler::sharedHandler()->onVideoPrepared();
    }

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_BraveFrontierJNI_backButtonCallback(JNIEnv* env, jobject thiz)
    {
        NativeCallbackHandler::sharedHandler()->onBackButtonCalled();
    }

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_BraveFrontierJNI_onDeviceShake(JNIEnv* env, jobject thiz)
    {
        NativeCallbackHandler::sharedHandler()->onDeviceShake();
    }

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_BraveFrontierJNI_purchaseStateChangedCallback(
        JNIEnv *env,
        jobject thiz,
        jstring iapData,
        jstring iapSignature,
        jstring purchase)
    {
        const char *jiapData = env->GetStringUTFChars(iapData, NULL);
        const char *jiapSignature = env->GetStringUTFChars(iapSignature, NULL);
        const char *jpurchase = env->GetStringUTFChars(purchase, NULL);

        NativeCallbackHandler::sharedHandler()->onPurchaseStateChanged(jiapData, jiapSignature, jpurchase);

        env->ReleaseStringUTFChars(iapData, jiapData);
        env->ReleaseStringUTFChars(iapSignature, jiapSignature);
        env->ReleaseStringUTFChars(purchase, jpurchase);
    }

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_BraveFrontierJNI_playPhonePurchaseSuccessCallBack(
        JNIEnv *env,
        jobject thiz,
        jstring arg1,
        jstring arg2,
        jstring arg3)
    {
        const char *jarg1 = env->GetStringUTFChars(arg1, NULL);
        const char *jarg2 = env->GetStringUTFChars(arg2, NULL);
        const char *jarg3 = env->GetStringUTFChars(arg3, NULL);

        NativeCallbackHandler::sharedHandler()->playPhonePurchaseSuccessCallBack(jarg1, jarg2, jarg3);

        env->ReleaseStringUTFChars(arg1, jarg1);
        env->ReleaseStringUTFChars(arg2, jarg2);
        env->ReleaseStringUTFChars(arg3, jarg3);
    }

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_BraveFrontierJNI_playPhonePurchaseFailCallBack(JNIEnv* env, jobject thiz)
    {
        NativeCallbackHandler::sharedHandler()->playPhonePurchaseFailCallBack();
    }

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_BraveFrontierJNI_purchaseStateChangedAdmobCallback(
        JNIEnv *env,
        jobject thiz,
        jstring arg1,
        jstring arg2,
        jstring arg3)
    {
        const char *jarg1 = env->GetStringUTFChars(arg1, NULL);
        const char *jarg2 = env->GetStringUTFChars(arg2, NULL);
        const char *jarg3 = env->GetStringUTFChars(arg3, NULL);

        NativeCallbackHandler::sharedHandler()->onPurchaseStateChangedAdmobCallback(jarg1, jarg2, jarg3);

        env->ReleaseStringUTFChars(arg1, jarg1);
        env->ReleaseStringUTFChars(arg2, jarg2);
        env->ReleaseStringUTFChars(arg3, jarg3);
    }

    JNIEXPORT void JNICALL Java_sg_gumi_bravefrontier_webview_BFWebViewClient_callBraveMethode(
        JNIEnv *env,
        jobject thiz,
        jstring url)
    {
        const char *jurl = env->GetStringUTFChars(url, NULL);
        WebViewScene::shared()->callBraveMethodFromHtml(jurl);
        // yes... the code does have a memory leak
    }
}

#endif
