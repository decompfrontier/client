#pragma once

class NetworkManager : public cocos2d::CCObject
{
	// not the same as the original but who cares it's the same thing...
	SHARED_SINGLETON(NetworkManager);
public:
	enum class API_VERSION
	{
		V1 = 1,
		V2 = 2,
	};

	enum class Host
	{
		_Unk0,
		_Unk1,
		_Unk2,
	};

	NetworkManager();
	~NetworkManager();

	std::string getStringForAPIVersion(API_VERSION version);
	void onNetworkRequestResponse(cocos2d::CCNode* node, void* user);
	void onNetworkRequestComplete(void* user, bool, bool);
	void forwardResponse(cocos2d::extension::CCHttpResponse* resp);
	void NetworkRequestGet(Host host, API_VERSION ver, std::string unk, const char* unk2);

	void setGameUrl(const std::string& str) { gameUrl = str; }
	const std::string& getGameUrl() const { return gameUrl; }

	void setServiceUrl(const std::string& str) { servicePhpUrl = str; }
	const std::string& getServiceUrl() const { return servicePhpUrl; }

	void setServiceUrl2(const std::string& str) { servicePhpUrl2 = str; }
	const std::string& getServiceUrl2() const { return servicePhpUrl2; }

	void setAppKey(const std::string& str) { apiUrl = str; }
	const std::string& getAppKey() const { return apiUrl; }

private:
	_BYTE unk2[27];
	std::string apiUrl;
	_BYTE unk3[8];
	std::string gameUrl;
	_BYTE unk4[8];
	std::string unkUrl;
	_BYTE unk5[8];
	std::string servicePhpUrl;
	_BYTE unk6[8];
	std::string servicePhpUrl2;
	_BYTE unk[13];
};

