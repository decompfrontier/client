#pragma once

class ServiceRequestManager : public cocos2d::CCObject
{
	SHARED_SINGLETON(ServiceRequestManager);

public:
	cocos2d::CCDictionary* constructParameters();
	void decodeFromBase64(const char*, const unsigned char**, unsigned long*);
	void decryptIdentifiersFromServer(std::string);
	void encodeTobase64(char* in, int in_len, char* out);
	std::string encryptIdentifiersToServer(const std::string& data);
	void getForceUpdateFlag();
	void getForceUpdateMessage();
	void getOverlayWallpaperName();
	std::string getServiceEndpoint(std::string name, std::string unused);
	void getServiceMstFileVer();
	void getSErviceResourceEndpoint();
	void getShutdownFriendId();
	void getShutdownHandleName();
	std::string hmacHashing(const std::string& data);
	void isRequestCompleted();
	void loadCachedData();
	void parseResponse(void*);
	void reinitEndPoints();
	void retriveGamePlayer(std::string);
	void retriveGumiLiveWallet();
	void serviceRequest();
	void update();

private:
	void onNetworkRequestComplete(cocos2d::CCNode*, void*);
	void onNetworkRequestError(cocos2d::CCNode*, void*);
	
	std::map<std::string, std::string>;
	std::string landingPageKey;
	_BYTE unk[5];
};
