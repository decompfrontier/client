#pragma once

//#include "StringLabelList.hpp"

#define unk void

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
	static void deleteLocalFile(const std::string& fileToDelete);
	static void deleteLocalFile(const std::string& prefix, const std::string& suffix);
	static void deleteLocalFiles(std::string prefix, std::string suffix);
	static void deleteLocalFilesWithExtension(std::string ext);
	static std::string dictionaryWordBreak(std::string& wordBreak, std::string& result);
	static uint disableBit(uint& res, uint bit);
	static void disableDim();
	static void downloadBundlePriority(std::string, std::string, bool);
	static void enableBit(uint& res, uint bit);
	static void enableDim();
	static std::string encodeCStringForBase64(const char*, const char*);
	static bool existsBundleFile(const std::string& file);
	static bool existsLocalFile(const std::string& file);
	static float getBatteryLevel();
	static int getBatteryState();
	static int getBlackTexture();
	static int getBuildPlatformID();
	static int getBuildPlatformName();
	static int getChallengeArenaUrl();
//	static float getConstrainLabelScale(StringLabelList*, float);
	static unk getCsvList(const std::string&);
	static unk getCsvListForLocal(const std::string&);
	static unk getCsvListForResource(const std::string&);
	static std::string getDeviceAdvertisingID();
	static std::string getDeviceID();
	static std::string getDeviceManufacturer();
	static std::string getDeviceName();
	static std::string getDeviceVersion();
	static int getDistance(int, int, int, int);
	static std::string getFileExtension(const char*);
	static std::string getFileName(const char*);
	static std::string getFileName(const std::string& p) { return getFileName(p.c_str()); }
	static std::string getFilePathFromLocalThenBundle(std::string) { return ""; }
	static void getLocalPath(std::string& file);
	static std::string getLocalPath();
	static int getNetworkState();
	static time_t getNowUnitxTime();
	//static ulonglong getNowUnitxTimeMill();
	static int getNowYMD();
	static int getPercent(float, float);
	static void getPhpReidUrl(const std::string&);
	static int getPlistInfo(const char*);
	static int getPlistInfo(const std::string&);
	static int getPurhcasePhpUrl();
	static int getQueryMap(const std::string&);
	static int getRandom(int, int);
	static int getRandomForFloat(float, float);
	static int getResourcePath(std::string& pathFromBundle);
	static int getResourceUrl(bool);
	static int getScreenHeight();
	static int getScreenWidth();
	static int getSecondsFromDate(std::string);
	static int getStringWidth(const std::string& integer);
	static int getTargetOs();
	static unk getTexture(const std::string&);
	static unk getTexture(const std::string&, bool, bool);
	static unk getTexture(const std::string&, const std::string&, bool);
	static unk getTextureForAll(std::string);
	static unk getTextureForLocal(const std::string&);
	static unk getTextureForLocal(const std::string&, bool);
	static unk getTextureForResource(const std::string&);
	static unk getTextureForResourceLan(const std::string&);
	static unk getTimerLeftStringFromSeconds(const std::string&);
	static std::string getUniqID();
	static void initGrouthBeat();
	static bool isBatteryCharging();
	static bool isCanSimultaneousDownload();
	static bool isEnableBit(uint&, uint);
	static bool isTouchObject(cocos2d::CCNode*, cocos2d::CCTouch*);
	static bool isTouchObject(cocos2d::CCNode*, cocos2d::CCTouch*, int, int);
	static unk judgePercent(float);
	static void leaveBreadcumb(const std::string&);
	static unk numUTF8Chars(std::string, int);
	static unk openUrl(std::string url);
	static unk outputLogcat(const std::string&);
	static std::vector<std::string> parseList(const std::string& list, const std::string& delim);
	static unk parseList2(std::string&, std::string&);
	static unk parseListWithQuotedStrings(std::string&, std::string&);
//	static unk removeDuplicate(cocos2d::CCMutableArray<cocos2d::CCInteger*>*);
	static unk eraseAllBits(uint&, uint);
	static unk saveFile(const std::string&, const std::string&);
	static unk saveScreenForCameraRoll(const std::string&);
	static unk saveScreenShot();
	static unk sendAppDriverActionResult(const std::string&);
	static unk sendGrowthBeatEventLaunch();
	static unk sendInvitationFaceBook(const std::string&, const std::string&);
	static unk sendInvitationLine(const std::string&, const std::string&);
	static unk sendInvitationMail(const std::string&, const std::string&);
	static unk sendInvitationTwitter(const std::string&, const std::string&);
	static unk sendMailWithImage(const std::string&, const std::string&, const std::string&);
	static unk setAudioResignBehavour();
	static unk setBadgeNumber(int);
//	static unk setConstrainLabelSize(StringLabel*, float);
//	static unk setConstrainLabelSize(StringLabelList*, float);
	static unk setSleepLabel(bool);
	static unk sharedApplication(const std::string&);
	static unk showPlayPhoneButton(bool);
	static unk showToastMessage(const std::string&, int);
	static unk showWebView();
//	static unk setArray(cocos2d::CCMutableArray<cocos2d::CCInteger*>*);
	static unk split(const std::string&, const char*);
	static unk split(const std::string&, const char*, std::vector<std::string>&);
	static unk splitInt(const std::string&, const char*);
	static unk splitInt(const std::string&, const char*, std::vector<int>&);
	static unk strReplace(std::string, const std::string&, const std::string&);
	static unk stringFormat(int, int);
	static unk stringFormatLong(long long, int);
	static unk stringifyIntList(const std::vector<int>&, const std::string&);
	static unk stringifyList(const std::vector<std::string>&, const std::string&);
	static unk textureFilledWhite(const std::string&);
	static unk urlDecode(const char*);
	static unk urlEncode(const char*);
};
