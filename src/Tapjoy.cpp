#include "Pch.hpp"
#include "Tapjoy.hpp"

using namespace tapjoy;

#ifdef __ANDROID__
static JavaVM* g_tapjoyVM = NULL; // 29BDB50
static jobject g_tapjoyClassLoader = NULL;
static jmethodID g_findClassMethod = NULL;
static jclass g_tapjoy = NULL;
static jclass g_TJActionRequest = NULL;
static jclass g_TJPlacement = NULL;
static jclass g_TapjoyNative = NULL;
static jclass g_TJPrivacyPolicy = NULL;

int Tapjoy::setJavaVM(JavaVM *vm)
{
    int rc = JNI_VERSION_1_4;

    if (!g_tapjoyVM)
    {
        JNIEnv* env;
        g_tapjoyVM = vm;
        int status = vm->GetEnv((void**)&env, JNI_VERSION_1_4);
        rc = JNI_ERR;

        if (status == JNI_OK && env)
        {
            /*
                ClassLoader loader = Tapjoy.getClassLoader();
            */
            jclass tapjoyDef = env->FindClass("com/tapjoy/Tapjoy");
            jclass objectClasses = env->GetObjectClass(tapjoyDef);
            jclass classLoaderDef = env->FindClass("java/lang/ClassLoader");
            jmethodID loaderMethod = env->GetMethodID(objectClasses, "getClassLoader", "()Ljava/lang/ClassLoader;");
            jobject loader = env->CallObjectMethod(tapjoyDef, loaderMethod);

            g_tapjoyClassLoader = env->NewGlobalRef(loader);
            g_findClassMethod = env->GetMethodID(classLoaderDef, "findClass", "(Ljava/lang/String;)Ljava/lang/Class;");

            g_tapjoy = (jclass)env->NewGlobalRef(env->FindClass("com/tapjoy/Tapjoy"));

            g_TJActionRequest = (jclass)env->NewGlobalRef(env->FindClass("com/tapjoy/TJActionRequest"));
            g_TJPlacement = (jclass)env->NewGlobalRef(env->FindClass("com/tapjoy/TJPlacement"));
            g_TapjoyNative = (jclass)env->NewGlobalRef(env->FindClass("com/tapjoy/internal/TapjoyNative"));
            g_TJPrivacyPolicy = (jclass)env->NewGlobalRef(env->FindClass("com/tapjoy/internal/TJPrivacyPolicy"));

            /*
                TapjoyConnectCore.setPlugin("cppStatic");
            */
            jclass tapjoyConnectCore = env->FindClass("com/tapjoy/TapjoyConnectCore");
            jmethodID tapjoySetPlugin = env->GetStaticMethodID(tapjoyConnectCore, "setPlugin", "(Ljava/lang/String;)V");
            jstring pluginName = env->NewStringUTF("cppStatic");
            env->CallStaticVoidMethod(tapjoyConnectCore, tapjoySetPlugin, pluginName);

            return JNI_VERSION_1_4;
        }

        return rc;
    }

    return rc;
    setContext(0);
}
#endif
