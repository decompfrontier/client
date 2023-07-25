#include "pch.h"
#include "SaveUtils.h"

void SaveUtils::saveUserDefaults(std::string key, std::string value)
{
	key = "key" + key;
	value = "magic_key_saved_as_v2" + value;

#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	GET_JNI("setSharedPerfString", "(Ljava/lang/String;Ljava/lang/String;)V");

	auto j1 = method.env->NewStringUTF(key.c_str());
	auto j2 = method.env->NewStringUTF(value.c_str());
	method.env->CallStaticVoidMethod(method.classID, method.methodID);
	method.env->DeleteLocalRef(j1);
	method.env->DeleteLocalRef(j2);
	method.env->DeleteLocalRef(method.classID);
#endif
}

void SaveUtils::saveKeyChain(const char*, std::string name, std::string pass)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	GET_JNI("saveToAccount", "(Ljava/lang/String;Ljava/lang/String;)V");
	auto j1 = method.env->NewStringUTF(key.c_str());
	auto j2 = method.env->NewStringUTF(value.c_str());
	method.env->CallStaticVoidMethod(method.classID, method.methodID);
	method.env->DeleteLocalRef(j1);
	method.env->DeleteLocalRef(j2);
	method.env->DeleteLocalRef(method.classID);
#endif
}

void SaveUtils::deleteKeyChain(std::string keychainName, const char*)
{
	saveKeyChain(keychainName.c_str(), "", "");
}

std::string SaveUtils::getAccountNameFromKeyChain(const char* keychainName)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	GET_JNI("getNameFromAccount", "()Ljava/lang/String;");
	auto x = method.env->CallStaticVoidMethod(method.classID, method.methodID);
	auto str = method.env->GetStringUTFChars(x, 0);
	std::string out = str;
	method.env->ReleaseStringUTFChars(x, str);
	method.env->DeleteLocalRef(x);
	method.env->DeleteLocalRef(method.classID);
	return out;
#else
	return "";
#endif
}

std::string SaveUtils::getPasswordFromKeyChain(const char* keychainName)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	GET_JNI("getPasswordFromAccount", "()Ljava/lang/String;");
	auto x = method.env->CallStaticVoidMethod(method.classID, method.methodID);
	auto str = method.env->GetStringUTFChars(x, 0);
	std::string out = str;
	method.env->ReleaseStringUTFChars(x, str);
	method.env->DeleteLocalRef(x);
	method.env->DeleteLocalRef(method.classID);
	return out;
#else
	return "";
#endif
}

std::string SaveUtils::loadUserDefaults(std::string key)
{
	key = "key" + key;

#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	GET_JNI_("getSharedPrefString", "(Ljava/lang/String;)Ljava/lang/String;")
	{
		auto j = method.env->CallStaticObjectMethod(method.classID, method.methodID);
		auto str = method.env->GetStringUTFChars(j, 0);
		std::string out = str;
		method.env->ReleaseStringUTFChars(x, str);
		method.env->DeleteLocalRef(j);
		method.env->DeleteLocalRef(method.classID);
		return str;
	} 		// if method is not found...
#endif

	return cocos2d::CCUserDefault::sharedUserDefault()->getStringForKey(key.c_str());
}

