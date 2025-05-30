#pragma once

// cocos2d-x
#include <cocos2d.h>
#include "compat/CCBool.h"
#include "compat/CCMutableArray.h"
#include "compat/CCMutableDictionary.h"

// C++
#include <vector>
#include <string>
#include <algorithm>
#include <map>

// JSON parsing
#include <picojson.h>

#ifdef __ANDROID__
// Android runtime
#include <jni.h>
#include <JniHelper.h>
#endif

// Decompilation helper macros
#if defined(__clang__) || defined(__GNUC__)
#define MAYBE_INLINE inline
#define ALWAYS_INLINE __attribute__((always_inline)) inline
#elif defined(_MSC_VER)
#define MAYBE_INLINE inline
#define ALWAYS_INLINE __forceinline inline
#else
#error "Unsupported compiler!"
#endif

// Game includes
#include "CocosMacros.hpp"
#include "RegionSwitch.hpp"
