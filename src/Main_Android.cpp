#include <jni.h>
#include <JniHelper.h>

extern "C"
{
    JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM* vm, void* reserved)
    {
        cocos2d::JniHelper::setJavaVM(vm);
        //tapjoy::Tapjoy::setJavaVM(vm);
        return JNI_VERSION_1_6;
    }

    JNIEXPORT void JNICALL Java_jp_co_alim_brave_BraveFrontierJNI_setMultiInvateSchemeData(JNIEnv*  env, jobject thiz) {}
}
