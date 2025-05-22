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

// Cocos2d support macros

/** CC_SYNTHESIZE_IS_READONLY is used to declare a protected variable.
 We can use getter to read the variable.
 @param varType : the type of variable.
 @param varName : variable name.
 @param funName : "is + funName" is the name of the getter.
 @warning : The getter is a public inline function.
 The variables and methods declared after CC_SYNTHESIZE_READONLY are all public.
 If you need protected or private, please declare.
 */
#define CC_SYNTHESIZE_IS_READONLY(varType, varName, funName)\
protected: varType varName;\
public: virtual varType is##funName(void) const { return varName; }

 /** CC_SYNTHESIZE_READONLY2 is used to declare a protected variable.
  We can use getter to read the variable.
  @param varType : the type of variable.
  @param varName : variable name.
  @param funName : "is + funName" is the name of the getter. "set + funName" is the name of the setter.
  @warning : The getter is a public inline function.
  The variables and methods declared after CC_SYNTHESIZE_READONLY are all public.
  If you need protected or private, please declare.
  */
#define CC_SYNTHESIZE_IS(varType, varName, funName)\
protected: varType varName;\
public: virtual varType is##funName(void) const { return varName; }

#define CC_DECLARE(varType, varName)\
protected: varType varName;

// Game includes
#include "RegionSwitch.hpp"
