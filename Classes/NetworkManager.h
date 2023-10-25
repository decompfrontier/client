#pragma once

class NetworkManager : public cocos2d::CCObject
{
	// not the same as the original but who cares it's the same thing...
	SHARED_SINGLETON(NetworkManager);
public:
	enum class API_VERSION
	{
		Default = 0,
		V1 = 1,
		V2 = 2,
	};

	enum class HOST
	{
		SlApi, // last version of GUMI Live host
		LiveApi, // the one that was like live.gumi.sg/api/1.1 (apiv2) or 1.0 (apiv1)
		Max,
	};

	enum class CONTENT_TYPE
	{
		Json,
		UrlEncoded,
	};

	struct HostUrl
	{
		std::string url;
		uint64_t unk;
	};

	typedef void (cocos2d::CCObject::* RequestComplete)(cocos2d::CCNode*, void*);
	typedef bool (cocos2d::CCObject::* RequestFail)(cocos2d::CCNode*, void*);

	NetworkManager();
	~NetworkManager();

	void NetworkRequest(HOST host, API_VERSION version, std::string filePath, cocos2d::CCDictionary* phpArguments, cocos2d::extension::CCHttpRequest::HttpRequestType reqType, cocos2d::extension::CCHttpRequest::HttpRequestHandlingType handleType, CONTENT_TYPE contentType, std::string, const char* tag, cocos2d::CCObject* cbHolder, RequestComplete onComplete, RequestFail onFail, bool, bool, bool);
	void NetworkRequestGet(HOST host, API_VERSION apiVersion, std::string filePath, char const* tag, cocos2d::CCObject* cbHolder, RequestComplete onComplete, RequestFail onFail, bool, bool, cocos2d::CCDictionary* phpArguments, cocos2d::extension::CCHttpRequest::HttpRequestHandlingType = cocos2d::extension::CCHttpRequest::HttpRequestHandlingType::kDefault);
	void NetworkRequestPost(HOST host, API_VERSION apiVersion, std::string, char const* tag, cocos2d::CCObject* cbHolder, RequestComplete onComplete, RequestFail onFail, bool, bool, cocos2d::CCDictionary* phpArguments, cocos2d::extension::CCHttpRequest::HttpRequestHandlingType = cocos2d::extension::CCHttpRequest::HttpRequestHandlingType::kDefault);
	void networkRequestFile(const char*, std::string, const char*, const char*, cocos2d::CCObject* cbHolder, RequestComplete onComplete);

	void OnErrorCancelPressed(void*);
	void OnErrorRetryPressed(void*);
	void OnTokenExpisedOkPressed(void*);

	void WebViewBattleFAQURL();
	void WebViewCreditsURL();
	void WebViewGeneralFAQURL();
	void WebViewOtherFAQURL();
	void WebviewDailyBonusURL();
	void WebviewDailyBonusURL();
	void WebviewLiveChatURL();
	void WebviewLoginURL();
	void WebviewNewsURL();
	void WebviewSocialURL();
	void WebviewTwoLineChatURL();
	
	void onNetworkRequestResponse(cocos2d::CCNode* node, void* user);
	void onNetworkRequestComplete(void* user, bool, bool);

	void forwardResponse(cocos2d::extension::CCHttpResponse* resp);

	std::string getStringForAPIVersion(API_VERSION version);

	void setServiceUrl(const std::string& str) { servicePhpUrl = str; }
	const std::string& getServiceUrl() const { return servicePhpUrl; }

	void setServiceUrl2(const std::string& str) { servicePhpUrl2 = str; }
	const std::string& getServiceUrl2() const { return servicePhpUrl2; }

	void setAppKey(const std::string& str) { apiUrl = str; }
	const std::string& getAppKey() const { return apiUrl; }

private:
	//_BYTE unk2[27];
	std::string apiUrl;
	HostUrl hostsUrl[(int)HOST::Max];
	//_BYTE unk5[8];
	std::string servicePhpUrl;
	//_BYTE unk6[8];
	std::string servicePhpUrl2;
	//_BYTE unk[13];
};

