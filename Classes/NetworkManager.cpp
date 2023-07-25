#include "pch.h"
#include "NetworkManager.h"
#include "BFCertificateProvider.h"
#include "GumiLiveConstants.h"
#include "GumiLiveManager.h"
#include "Utils.h"

using namespace cocos2d::extension;
using namespace cocos2d;

SET_SHARED_SINGLETON(NetworkManager);

NetworkManager::NetworkManager()
{
	apiUrl = API_URL;
	CCHttpClient::getInstance()->setCertificateProvider(BFCertificateProvider::shared());
}

std::string NetworkManager::getStringForAPIVersion(API_VERSION version)
{
	if (version == API_VERSION::V2)
		return GUMI_LIVE_URLPATH_V2;

	return GUMI_LIVE_URLPATH_V1;
}

void NetworkManager::forwardResponse(cocos2d::extension::CCHttpResponse* resp)
{
	// TODO
}

void NetworkManager::NetworkRequestGet(HOST host, API_VERSION apiVersion, std::string filePath, char const* cocosNotificationId, CCObject* cbHolder, RequestComplete onComplete, RequestFail onFail, bool a1, bool a2, CCDictionary* phpArguments, CCHttpRequest::HttpRequestHandlingType handleType)
{
	NetworkRequest(host, apiVersion, filePath, phpArguments, cocos2d::extension::CCHttpRequest::kHttpGet, handleType, CONTENT_TYPE::Json, "", cocosNotificationId, cbHolder, onComplete, onFail, a1, a2, false);
}

void NetworkManager::NetworkRequest(HOST host, API_VERSION version, std::string filePath, CCDictionary* phpArguments, CCHttpRequest::HttpRequestType reqType, CCHttpRequest::HttpRequestHandlingType handleType, CONTENT_TYPE contentType, std::string contentData, const char* notificationId, CCObject* cbHolder, RequestComplete onComplete, RequestFail onFail, bool a14, bool a15, bool sendToken)
{
	std::stringstream buf;

	if (apiUrl.empty())
	{
		buf << hostsUrl[(int)host].url;
	}
	else
		buf << apiUrl;

	if (host == HOST::LiveApi)
	{
		if (version == API_VERSION::V2)
			buf << GUMI_LIVE_URLPATH_V2;
		else if (version == API_VERSION::V1)
			buf << GUMI_LIVE_URLPATH_V1;
	}

	buf << filePath;
	buf << "?";

	// argument dispatch
	bool shouldPutAnd = false;
	
	if (phpArguments)
	{
		CCDictElement* elem = nullptr;
		CCDICT_FOREACH(phpArguments, elem)
		{
			if (shouldPutAnd)
				buf << "&";

			buf << Utils::URLEncodedString(elem->getStrKey());
			buf << "=";

			auto obj = elem->getObject();

			if (obj)
			{
				buf << Utils::URLEncodedString(((cocos2d::CCString*)obj)->getCString());
			}

			shouldPutAnd = true;
		}
	}

	if (sendToken)
	{
		if (shouldPutAnd)
			buf << "&";

		buf << "token=";
		buf << Utils::URLEncodedString(GumiLiveManager::shared()->getToken());
	}

	std::string ct = "";

	switch (contentType)
	{
	case CONTENT_TYPE::UrlEncoded:
		ct = "Content-Type: application/x-www-form-urlencoded";
		break;

	case CONTENT_TYPE::Json:
		ct = "Content-Type: application/json; charset=utf-8";
		break;

	default:
		break;
	}

	std::string accepts = "Accept:application/json; charset=utf-8";
	auto length = CCString::createWithFormat("Content-Length: %lu", contentData.empty() ? 0 : (uint32_t)contentData.length());
	auto extraHeader = CCString::createWithFormat("GCLIENTID: %s %s", Utils::getDevicePlatform(), Utils::getBundleName());

	CCHttpRequest* rq = new CCHttpRequest();
	rq->setRequestType(reqType);
	rq->setUrl(buf.str().c_str());
	rq->setResponseCallback(this, callfuncND_selector(onNetworkRequestResponse));
	rq->addHeader(accepts);
	rq->addHeader(ct);
	rq->addHeader(length->getCString());
	rq->addHeader(extraHeader->getCString());
	rq->setTag(notificationId);
	rq->setSuccessCb(onComplete);
	rq->setFailCb(onFail);
	rq->setSpecial(a14, a15);
	rq->setLoadingScreenShowFlag(false);
	rq->setRequestTime(Utils::getCurrentTimeMs());

	if (cbHolder)
	{
		rq->setCallbackHolder(cbHolder);
	}
	else
	{
		rq->setCallbackHolder(nullptr);
	}

	bool showLoadingScreen = false; // TODO: figure what's wrong with this boiii
	if (showLoadingScreen)
	{
		Utils::showLoadingScreen(rq, true, nullptr, false);
		rq->setLoadingScreenShowFlag(true);
	}
	else
		CCHttpClient::getInstance()->send(rq);

	rq->release();
}

void NetworkManager::onNetworkRequestResponse(cocos2d::CCNode* node, void* user)
{
	CCHttpResponse* resp = (CCHttpResponse*)user;
	Json::Value json;
	CCHttpRequest* req = resp->getHttpRequest();

	Utils::ReadIntoJson(resp->getResponseData(), json);
	Utils::printJson(json);

	if (req->isLoadingScreenShown())
		Utils::removeLoadingScreen(1, 0, nullptr);

	if (req->unk20 || req->getSuccessCb())
	{
		if (resp->isSucceed() && (resp->getResponseCode() - 200) < 100)
		{
			req->getSuccessCb()(v20, this, resp);
		}
	}
}
