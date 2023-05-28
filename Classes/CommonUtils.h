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
	static cocos2d::CCPoint convertPosition(const cocos2d::CCPoint& point);
	static cocos2d::CCPoint convertPosition(float width, float height);
	static cocos2d::CCPoint convertPosition(int layerId, float width, float height);
	static void* convertUIImage(void* imgptr);
	static void copyClipboard(const std::string& str);
	static std::string decodeCStringForBase64(const char* data, const char* key);
	static std::string decompress_string(const char* data, int);
	static void deleteCache();
	static void deleteLocalFile(const std::string& str);
	static void deleteLocalFile(const std::string&, const std::string);
	static void deleteLocalFilesWithExtension(std::string);
	static std::string dictionaryWordBreak(std::string&, std::string&);
	static uint disableBit(uint&, uint);
	static void disableDim();
	static void downloadBundlePriority(std::string, std::string, bool);
	static void enableBit(uint&, uint);
	static void enableDim();
	static std::string encodeCStringForBase64(const char*, const char*);
	static bool existsBundleFile(const std::string&);
	static bool existsLocalFile(const std::string&);
	static int getBatteryLevel();
	static int getBatteryState();
	static int getBlackTexture();
	static int getBuildPlatformID();
	static int getBuildPlatformName();
	static int getChallengeArenaUrl();
	static float getConstrainLabelScale(StringLabelList*, float);
	static unk getCsvList(const std::string&);
	static unk getCsvListForLocal(const std::string&);
	static unk getCsvListForResource(const std::string&);
	static int getDeviceAdvertisingID();
	static int getDeviceID();
	static int getDeviceManufacturer();
	static int getDeviceName();
	static int getDeviceVersion();
	static int getDistance(int, int, int, int);
	static std::string getFileExtension(const char*);
	static std::string getFileName(const char*);
	static std::string getFileName(const std::string& p) { return getFileName(p.c_str()); }
	static std::string getFilePathFromLocalThenBundle(std::string) { return ""; }
	static void getLocalPath(std::string&);
	static std::string getLocalPath();
	static int getNetworkState();
	static time_t getNowUnitxTime();
	static ulonglong getNowUnitxTimeMill();
	static int getNowYMD();
	static int getPercent(float, float);
	static void getPhpReidUrl(const std::string&);
	static int getPlistInfo(const char*);
	static int getPlistInfo(const std::string&);
	static int getPurhcasePhpUrl();
	static int getQueryMap(const std::string&);
	static int getRandom(int, int);
	static int getRandomForFloat(float, float);
	static int getResourcePath(std::string&);
	static int getResourceUrl(bool);

	/* todo: to finish ... */
};
