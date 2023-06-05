#include "pch.h"
#include "NetworkManager.h"
#include "BFCertificateProvider.h"

using namespace cocos2d::extension;

SET_SHARED_SINGLETON(NetworkManager);

NetworkManager::NetworkManager()
{
	apiUrl = API_URL;
	CCHttpClient::getInstance()->setCertificateProvider(BFCertificateProvider::shared());
}

std::string NetworkManager::getStringForAPIVersion(API_VERSION version)
{
	if (version == API_VERSION::V2)
		return "/api/1.1/";

	return "/api/1.0/";
}

void NetworkManager::forwardResponse(cocos2d::extension::CCHttpResponse* resp)
{
	// TODO
}