#include "Pch.hpp"
#include "AsyncFileLoad.hpp"

bool AsyncFileLoad::isImage()
{
	size_t pos = m_url.find_last_of('.');
	if (pos == -1)
	{
		return false;
	}
	
	std::string ext = m_url.substr(pos + 1);
	if (ext == "png") {
		return true;
	}
	
	if (ext == "jpg") {
		return true;
	}
	
	return false;
}

void AsyncFileLoad::loadFile(std::string url, void* userObj)
{
	m_url = url;
	m_isInError = false;
	m_isFinished = false;
	m_isConnected = false;
	
	cocos2d::CCObject* ccObj = (cocos2d::CCObject*)userObj;
	m_userObj = ccObj;
	ccObj->retain();
	
	cocos2d::JniMethodInfo method;
	if (cocos2d::JniHelper::getStaticMethodInfo(method, "sg/gumi/util/AsyncFileLoad", "startDownload", "(JLjava/lang/String;)V")) {
		jstring jurl = method.env->NewStringUTF(url.c_str());
		method.env->CallStaticVoidMethod(method.classID, method.methodID, this, jurl);
		method.env->DeleteLocalRef(jurl);
		method.env->DeleteLocalRef(method.classID);
	}
	
	m_isConnected = true;
}
