#pragma once

class NetworkManager : public cocos2d::CCObject
{
	// not the same as the original but who cares it's the same thing...
	SHARED_SINGLETON(NetworkManager);
public:
	enum class API_VERSION
	{
		Default = 0, // used for SL api
		V1 = 1,
		V2 = 2,
	};

	enum class HOST
	{
		SlApi, // last version of GUMI Live host
		LiveApi, // the one that was like live.gumi.sg/api/1.1 (apiv2) or 1.0 (apiv1)
		External, // external api to forward to (eg. gme)
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
	~NetworkManager() = default;

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
	bool onNetworkRequestComplete(void* user, bool, bool);

	void forwardResponse(cocos2d::extension::CCHttpResponse* resp);

	std::string getStringForAPIVersion(API_VERSION version);

	CC_SYNTHESIZE(std::string, m_apiUrl, ApiUrl);
	CC_SYNTHESIZE(std::string, m_servicePhpUrl1, ServiceUrl1);
	CC_SYNTHESIZE(std::string, m_servicePhpUrl2, ServiceUrl2);
	CC_SYNTHESIZE(std::string, m_appKey, AppKey);
	CC_SYNTHESIZE(std::string, m_origGameUrl, OriginalGameUrl);
	CC_SYNTHESIZE(std::string, m_gameUrl, GameUrl);

private:
	HostUrl hostsUrl[(int)HOST::Max];
};

