/*
* Sounds like this is Android only and part of something called "gumi-library" or similar.
*/
#include "Pch.hpp"
#include "AsyncFileLoad.hpp"

#ifdef __ANDROID__
bool AsyncFileLoad::isImage()
{
	if (m_url.empty())
	{
		return false;
	}

	size_t comma = m_url.rfind(".");
	if (comma == -1)
	{
		return false;
	}

	size_t strsz = m_url.size();

	if (strsz)
	{
		for (; strsz > 0; strsz--)
		{
			if (m_url[strsz] == '.')
			{
				break;
			}
		}

		if (strsz == 0)
		{
			comma = 0;
		}
		else
		{
			comma = strsz + 1;
		}
	}
	else
	{
		comma = 0;
	}

	std::string ext_pos = m_url.substr(comma + 1);

	if (ext_pos.size() == 3 && ext_pos == "png") {
		return true;
	}

	if (strsz)
	{
		for (; strsz > 0; strsz--)
		{
			if (m_url[strsz] == '.')
			{
				break;
			}
		}

		if (strsz == 0)
		{
			comma = 0;
		}
		else
		{
			comma = strsz + 1;
		}
	}
	else
	{
		comma = 0;
	}

	ext_pos = m_url.substr(comma + 1);

	if (ext_pos.size() == 3 && ext_pos == "jpg") {
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
#endif
