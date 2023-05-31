#include "pch.h"
#include "Utils.h"
#include "GameLayer.h"

void Utils::showLoadingScreen(cocos2d::extension::CCHttpRequest* req, bool, cocos2d::CCLayer*, bool)
{
	if (req)
	{
		cocos2d::extension::CCHttpClient::getInstance()->send(req);
	}

	auto gl = GameLayer::shared();

	/* TODO... */
}

std::string Utils::getDevicePlatform()
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	return "Android";
#elif (CC_TARGET_PLATFORM == CC_PLATFORM_WIN32) // __DECOMP__
	return "Win32";
#else
#error "Unknown platform"
#endif
}

std::string Utils::getDeviceVID()
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	GET_JNI("getDeviceUUID", "()Ljava/lang/String");
	jstring jret = (jstring)method.env->CallStaticObjectMethod(method.classID, method.methodID);
	auto p = method.env->GetStringUTFChars(jret, 0);
	std::string devid = p;

	method.env->ReleaseStringUTFChars(jret, p);
	method.env->DeleteLocalRef(jret);
	method.env->DeleteLocalRer(method.classID);

	return devid;
#else
	return ""; // TODO: __DECOMP__
#endif
}

std::string Utils::getDeviceOS()
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	GET_JNI("getOSVersion", "()Ljava/lang/String");
	jstring jret = (jstring)method.env->CallStaticObjectMethod(method.classID, method.methodID);
	auto p = method.env->GetStringUTFChars(jret, 0);
	std::string devid = p;

	method.env->ReleaseStringUTFChars(jret, p);
	method.env->DeleteLocalRef(jret);
	method.env->DeleteLocalRer(method.classID);

	return devid;
#else
	return ""; // TODO: __DECOMP__
#endif
}
