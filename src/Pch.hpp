#pragma once

// cocos2d-x
#include <cocos2d.h>

// C++
#include <vector>
#include <string>
#include <algorithm>

#ifdef __ANDROID__
// Android runtime
#include <jni.h>
#include <JniHelper.h>
#endif

#if defined(__clang__) || defined(__GNUC__)
#define MAYBE_INLINE inline
#define ALWAYS_INLINE __attribute__((always_inline)) inline
#else
#error "Unsupported compiler!"
#endif
