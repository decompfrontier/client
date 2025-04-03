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
        static void setJavaVM(JavaVM *vm);
#endif
    };
}
