#pragma once

class CommonUtils
{
public:
	static std::string FloatToString(float conv, int _unused);
	static const char* IntToCString(int conv);
	static std::string IntToString(int conv);
	static std::string IntToStringFull(int conv);
	static std::string LLToString(long long conv);
	static std::string LongToString(long conv);
	static std::string LongLongToString(long long conv);
	static double StrExpToDouble(std::string exp);
	static float StrToFloat(const char* str);
	static int StrToInt(const char* str);
	static int StrToInt(const std::string& str);
	static long StrToLong(const std::string& str);
	static long long StrToLongLong(const char* str);
	static long long StrToLongLong(const std::string& str);
	static unsigned int StrToUInt(const std::string& str);
	static unsigned long long StrToULongLong(const char* str);
	static unsigned long long StrToULongLong(const std::string& str);
	static std::string ULongLongToString(unsigned long long conv);
	static void appExit();
	static std::string appendStr(const char* str1, const char* str2);
	static void appsflyerStartTracking();
	static void blink(cocos2d::CCNode* node, float duration, uint blinks);
	static double calcPercent(double percent, double total);
	static bool canAccessToPhotos();
	static bool canOpenUrl(std::string url);
	static bool containsPoint(const cocos2d::CCRect& rect, const cocos2d::CCPoint& point);
	static cocos2d::CCPoint convertPosition(cocos2d::CCLayer* layer, float width, float height);

	/* todo: to finish ... */
};
