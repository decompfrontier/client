#pragma once

#ifdef __ANDROID__
#include <jni.h>
#endif

namespace tapjoy
{
    class Tapjoy
    {
    public:
#ifdef __ANDROID__
        static int setJavaVM(JavaVM *vm);
        static void setContext(jobject ctx);
#endif

    };
}
