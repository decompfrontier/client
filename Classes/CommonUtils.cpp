#include "pch.h"
#include "CommonUtils.h"

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
