#pragma once

class NetworkManager : public cocos2d::CCObject
{
	// not the same as the original but who cares it's the same thing...
	SHARED_SINGLETON(NetworkManager);
public:
	enum class API_VERSION
	{
		V1,
		V2,
	};

	NetworkManager();
	~NetworkManager();

	std::string getStringForAPIVersion(API_VERSION version);
	void onNetworkRequestResponse(cocos2d::CCNode* node, void* user);
	void onNetworkRequestComplete(void* user, bool, bool);
	void forwardResponse(cocos2d::extension::CCHttpResponse* resp);

private:
	char unk2[27];
	std::string apiUrl;
	char unk[109];
};

