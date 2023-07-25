#pragma once

class Utils
{
public:
	static void showLoadingScreen(cocos2d::extension::CCHttpRequest* req, bool, cocos2d::CCLayer*, bool);
	static std::string getDevicePlatform();
	static std::string getDeviceVID();
	static std::string getDeviceOS();
	static std::string getDeviceModel();
	static std::string getAppKey();
	static std::string getDeviceAltVid();
	static std::string getUniqueIdentifier();
	static std::string URLEncodedString(std::string input);
	static std::string getBundleName();
	static time_t getCurrentTimeMs();

};
