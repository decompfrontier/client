#include "pch.h"
#include "CommonUtils.h"
#include "GameLayer.h"

constexpr auto Z_BUFFER_SIZE = 32768;

USING_NS_CC;

#define TOSTR(x) \
	std::stringstream buf; \
	buf << x; \
	return buf.str();

#define FROMSTR(x, type) \
	std::stringstream buf; \
	buf << x; \
	type c; \
	buf >> c; \
	return c;

std::string CommonUtils::FloatToString(float conv, int _unused)
{
	TOSTR(conv);
}

const char* CommonUtils::IntToCString(int conv)
{
	/* eww */
	std::string g_globalCString = "";
	g_globalCString = IntToString(conv);
	return g_globalCString.c_str();
}

std::string CommonUtils::IntToString(int conv)
{
	TOSTR(conv);
}

std::string CommonUtils::IntToStringFull(int conv);

std::string CommonUtils::LLToString(long long conv)
{
	TOSTR(conv);
}

std::string CommonUtils::LongLongToString(long long conv)
{
	TOSTR(conv);
}

std::string CommonUtils::LongToString(long conv)
{
	TOSTR(conv);
}

double CommonUtils::StrExpToDouble(std::string exp);

float CommonUtils::StrToFloat(const char* str)
{
	FROMSTR(str, float);
}

int CommonUtils::StrToInt(const char* str)
{
	FROMSTR(str, int);
}

int CommonUtils::StrToInt(const std::string& str)
{
	FROMSTR(str, int);
}

long CommonUtils::StrToLong(const std::string& str)
{
	FROMSTR(str, long);
}

long long CommonUtils::StrToLongLong(const char* str)
{
	FROMSTR(str, long long);
}

long long CommonUtils::StrToLongLong(const std::string& str)
{
	FROMSTR(str, long long);
}

unsigned int CommonUtils::StrToUInt(const std::string& str)
{
	FROMSTR(str, unsigned int);
}

unsigned long long CommonUtils::StrToULongLong(const char* str)
{
	FROMSTR(str, unsigned long long);
}

unsigned long long CommonUtils::StrToULongLong(const std::string& str)
{
	FROMSTR(str, unsigned long long);
}

std::string CommonUtils::ULongLongToString(unsigned long long conv)
{
	TOSTR(conv);
}

void CommonUtils::appExit()
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	JniMethodInfo method;
	std::string className = BF_JNI_CLASS;
	if (!JniHelper::getStaticMethodInfo(&method, className.c_str(), "appExit", "()V"))
		return;

	method.env->CallStaticVoidMethod(method.classID, method.methodID);
	method.env->DeleteLocalRef(method.classID);
#else	/* __DECOMP__ */
	exit(0);
#endif
}

std::string CommonUtils::appendStr(const char* a, const char* b)
{
	std::string dst = a;
	dst += b;
	return dst;
}

void CommonUtils::appsflyerStartTracking()
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	JniMethodInfo method;
	std::string className = BF_JNI_CLASS;
	if (!JniHelper::getStaticMethodInfo(&method, className.c_str(), "appsflyerStartTracking", "()V"))
		return;

	method.env->CallStaticVoidMethod(method.classID, method.methodID);
	method.env->DeleteLocalRef(method.classID);
#endif
}

void CommonUtils::blink(CCNode* node, float duration, uint blinks)
{
	auto blink = CCBlink::create(duration, blinks);
	auto repeat = CCRepeatForever::create(blink);
	node->runAction(repeat);
}

double CommonUtils::calcPercent(double percent, double total)
{
	if (total != 100.0)
		return (percent / 100.0) * total;

	return percent;
}

bool CommonUtils::canAccessToPhotos()
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	JniMethodInfo method;
	std::string className = BF_JNI_CLASS;
	if (!JniHelper::getStaticMethodInfo(&method, className.c_str(), "canAccessToPhotos", "()Z"))
		return;

	bool ret = method.env->CallStaticBooleanMethod(method.classID, method.methodID);
	method.env->DeleteLocalRef(method.classID);
	return ret;
#else /* __DECOMP__ */
	return true;
#endif
}

bool CommonUtils::canOpenUrl(std::string url)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	JniMethodInfo method;
	std::string className = BF_JNI_CLASS;
	if (!JniHelper::getStaticMethodInfo(&method, className.c_str(), "canLaunchUrl", "(Ljava/lang/String;)Z"))
		return;

	jstring jstr = method.env->functions->NewStringUTF(url.c_str());
	bool ret = method.env->CallStaticBooleanMethod(method.classID, method.methodID, jstr);
	method.env->DeleteLocalRef(method.classID);
	method.env->DeleteLocalRef(jstr);
	return ret;
#else /* __DECOMP__ */
	return true;
#endif
}

bool CommonUtils::containsPoint(const cocos2d::CCRect& rect, const cocos2d::CCPoint& point)
{
	auto dir = CCDirector::sharedDirector();
	auto pointGL = dir->convertToGL(point);
	auto originGL = dir->convertToGL(rect.origin);
	CCRect newRect(originGL.x, originGL.y - rect.size.height, rect.size.width, rect.size.height);
	return newRect.containsPoint(pointGL);
}

cocos2d::CCPoint CommonUtils::convertPosition(cocos2d::CCLayer* layer, float width, float height)
{
	auto rheight = layer-> ? ();

	if (rheight == 0.0)
	{
		rheight = CCDirector::sharedDirector()->getWinSize().height;
	}

	return cocos2d::CCPoint(width, rheight - height);
}

cocos2d::CCPoint CommonUtils::convertPosition(const cocos2d::CCPoint& point)
{
	return convertPosition(1, point.x, point.y);
}

cocos2d::CCPoint CommonUtils::convertPosition(float width, float height)
{
	return convertPosition(1, width, height);
}

cocos2d::CCPoint CommonUtils::convertPosition(int layerId, float width, float height)
{
	auto layer = GameLayer::shared()->getLayer(layerId);
	return convertPosition(layer, width, height);
}

void* CommonUtils::convertUIImage(void* imgptr)
{
	return nullptr;
}

void CommonUtils::copyClipboard(const std::string& str)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	JniMethodInfo method;
	std::string className = BF_JNI_CLASS;
	if (!JniHelper::getStaticMethodInfo(&method, className.c_str(), "copyToClipboard", "(Ljava/lang/String;)V"))
		return;

	auto jstr = method.env->NewStringUTF(str.c_str());
	method.env->CallStaticVoidMethod(method.classID, method.methodID);
	method.env->DeleteLocalRef(jstr);
	method.env->DeleteLocalRef(method.classID);
#else
	/* __DECOMP__ TODO */
#endif
}

/*
	AES_CBC.decrypt(base64(data), key) 
	
	key max size -> 16 bytes, if it's smaller then simply add a bunch of 0x0
					if it's bigger than just copy the first 16 bytes

*/
std::string CommonUtils::decodeCStringForBase64(const char* data, const char* key)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	JniMethodInfo method;
	std::string className = BF_JNI_CLASS;
	if (!JniHelper::getStaticMethodInfo(&method, className.c_str(), "decodeCStringForBase64", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;"))
		return;

	auto jstr = method.env->NewStringUTF(a2);
	auto jstr2 = method.env->NewStringUTF(a3);
	auto jstr_ret = method.env->CallStaticVoidMethod(method.classID, method.methodID);
	std::string retstr = "";

	if (!method.env->ExceptionCheck())
	{
		retstr = method.env->GetStringUTFChars(jstr_ret, 0);
		method.env->DeleteLocalRef(jstr_ret);
	}

	method.env->DeleteLocalRef(jstr2);
	method.env->DeleteLocalRef(jstr);
	method.env->DeleteLocalRef(method.classID);
	return retstr;
#else
	/* __DECOMP__ TODO */
#endif
}

std::string CommonUtils::decompress_string(const char* data, int length)
{
	char buffer[Z_BUFFER_SIZE+8];
	z_stream zs;
	memset(&zs, 0, sizeof(zs));

	if (inflateInit(&zs) != Z_OK)
		return "-1";

	zs.next_in = (Bytef*)data;
	zs.avail_in = length;

	int zcode = 0;
	std::string ret = "";

	do
	{
		zs.next_out = (Bytef*)buffer;
		zs.avail_out = Z_BUFFER_SIZE;

		zcode = inflate(&zs, 0);

		auto p = zs.total_out - ret.capacity();
		if (zs.total_out > ret.capacity())
			ret.append(buffer, zs.total_out - ret.capacity());

	} while (zcode != Z_OK);
	inflateEnd(&zs);

	if (zcode == Z_STREAM_END)
		return ret;

	return "-1";
}

void CommonUtils::deleteCache()
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	JniMethodInfo method;
	std::string className = BF_JNI_CLASS;
	if (!JniHelper::getStaticMethodInfo(&method, className.c_str(), "clearApplicationData", "()V"))
		return;

	auto jstr_ret = method.env->CallStaticVoidMethod(method.classID, method.methodID);
	method.env->DeleteLocalRef(method.classID);
	return retstr;
#endif
}

std::string CommonUtils::dictionaryWordBreak(std::string& a1, std::string& a2)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	JniMethodInfo method;
	std::string className = BF_JNI_CLASS;
	if (!JniHelper::getStaticMethodInfo(&method, className.c_str(), "dictionaryWordBreak", "(Ljava/lang/String;)Ljava/lang/String;"))
		return;

	auto jstr = method.env->NewStringUTF(a1.c_str());
	auto jstr_ret = method.env->CallStaticObjectMethod(method.classID, method.methodID, jstr);
	std::string retstr = "";

	method.env->DeleteLocalRef(jstr);

	if (jstr_ret)
	{
		a2 = method.env->GetStringUTFChars(jstr_ret, 0);
		(*v26.env)->ReleaseStringUTFChars(v26.env, v10, v11);
		method.env->DeleteLocalRef(jstr_ret);
		method.env->DeleteLocalRef(method.classID);
		return a2;
	}

	if (a1 != a2)
		a2 = a1;

	method.env->DeleteLocalRef(method.classID);
	return a2;
#else
	/* __DECOMP__ TODO */
#endif
}

uint CommonUtils::disableBit(uint& ret, uint bit)
{
	ret &= ~bit;
	return ret;
}

void CommonUtils::disableDim()
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID)
	JniMethodInfo method;
	std::string className = BF_JNI_CLASS;
	if (!JniHelper::getStaticMethodInfo(&method, className.c_str(), "disableDim", "()V"))
		return;

	auto jstr_ret = method.env->CallStaticVoidMethod(method.classID, method.methodID);
	method.env->DeleteLocalRef(method.classID);
	return retstr;
#endif
}