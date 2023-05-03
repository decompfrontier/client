#pragma once

// cocos2d-x
#include "cocos2d.h"
#include "SimpleAudioEngine.h"
#include "CCMutableArray.h" // COMPATIBILITY WITH COCOS2D-X v1 API

// zlib
#include <zlib.h>

#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#include "jni/JniHelper.h"
constexpr const char* BF_JNI_CLASS = ",/sg/gumi/bravefrontier/BraveFrontierJNI";
#endif

// C++
#include <vector>
#include <string>

// typedefs
using ushort = unsigned short;
using uint = unsigned int;

// game
#include "Singleton.h"
#include "CrashlyticsUtil.h"
#include "CommonUtils.h"
