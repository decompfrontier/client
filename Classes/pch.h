#pragma once

// cocos2d-x
#include "cocos2d.h" // libcocos2d
#include "cocos-ext.h"  // Extensions
#include "SimpleAudioEngine.h" // CocosDenshion
#include "CCMutableArray.h" // COMPATIBILITY WITH COCOS2D-X v1 API

// zlib
#include <zlib.h>

#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
#include "jni/JniHelper.h"
constexpr const char* BF_JNI_CLASS = ",/sg/gumi/bravefrontier/BraveFrontierJNI";

#define GET_JNI(method, signature) \
	JniMethodInfo method; \
	std::string className = BF_JNI_CLASS; \
	if (!JniHelper::getStaticMethodInfo(&method, className.c_str(), method, signature)) \
		return;

#define RANDOM_FUNC() arc4random()
#else
#define RANDOM_FUNC() rand()
#endif

// C++
#include <vector>
#include <string>
#include <algorithm>

// typedefs
using byte = unsigned char;
using ushort = unsigned short;
using uint = unsigned int;
using ulonglong = unsigned long long;

enum class GemType : uint
{
	Free,
	Paid,
	Max,
};

enum class ReinforcementType : uint
{
	Ex1,
	Ex2,
	Max,
};

// game
#include "Singleton.h"
#include "CrashlyticsUtil.h"
#include "CommonUtils.h"
#include "ServerConfig.h"
