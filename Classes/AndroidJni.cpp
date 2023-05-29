/*
    libgame.so definitions
    Android Jni
*/
#include "pch.h"

#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#include "AppDelegate.h"
#include "SuperAnim.h"
#include "PTRateThisAppPopup.h"

/* jni */
#include <jni.h>
#include <android/log.h>


#define  LOG_TAG    "main"
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)

using namespace cocos2d;

extern "C"
{

jint JNI_OnLoad(JavaVM *vm, void *reserved)
{
    JniHelper::setJavaVM(vm);
#if USE_TAPJOY
    tapjoy::Tapjoy::setJavaVM(vm);
#endif
    return JNI_VERSION_1_4;
}

void Java_jp_co_alim_brave_BraveFrontierJNI_setMultiInvateSchemeData()
{
    /* stub, perhaps this was left from JP */
}

/* The REAL android entry point */
void Java_org_cocos2dx_lib_Cocos2dxRenderer_nativeInit(JNIEnv* env, jobject thiz, jint w, jint h)
{
    if (!CCDirector::sharedDirector()->getOpenGLView())
    {
        CCEGLView *view = CCEGLView::sharedOpenGLView();
        view->setFrameSize(w, h);

        AppDelegate *pAppDelegate = new AppDelegate();
        CCApplication::sharedApplication()->run();
    }
    else
    {
        ccDrawInit();
        ccGLInvalidateStateCache();
        CCShaderCache::sharedShaderCache()->reloadDefaultShaders();
        SuperAnim::ReloadSuperAnimShared(); /* bf */
        CCTextureCache::reloadAllTextures();
        CCNotificationCenter::sharedNotificationCenter()->postNotification(EVENT_COME_TO_FOREGROUND, NULL);
        CCDirector::sharedDirector()->setGLDefaultValues(); 
    }
}

void Java_sg_gumi_bravefrontier_BraveFrontierJNI_nativeRateThisAppPopupCallback(JNIEnv* env, jobject thisz, jint status)
{
    // status 0: app was rated, status 1: app was not rated
    PTRateThisAppPopup::alertCloseCallback(status);
}


void Java_sg_gumi_bravefrontier_BraveFrontierJNI_backButtonCallback();
void Java_sg_gumi_bravefrontier_BraveFrontierJNI_nativeDownloadCallback(JNIEnv* env, jobject thisz, jlong obj, jbyteArray downloadedData, jstring error);

} /* extern "C" */

#endif /* PLATFORM_ANDROID */
