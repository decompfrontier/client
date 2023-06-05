#include "pch.h"
#include "GumiLiveNetworkManagement.h"
#include "GumiLiveConstants.h"
#include "NetworkManager.h"

using namespace cocos2d;

void GumiLiveNetworkManagement::networkRequestAppleGumiLiveExistData(CCString* gumi_session_to, CCString* gumi_user_id)
{
	auto dict = CCDictionary::create();
	dict->setObject(gumi_user_id, "gumi_user_id");
	dict->setObject(gumi_session_to, "gumi_session_to");

	NetworkManager::shared()->NetworkRequestGet(
		2, 0, (__int64)&v9, GUMI_LIVE_TOKEN_REQUEST_APPLE_EXIST_DATA, this,
		onRequestComplete, 0, 0, 1, dict, 0);
	);
}