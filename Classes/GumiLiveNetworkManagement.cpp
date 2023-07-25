#include "pch.h"
#include "GumiLiveNetworkManagement.h"
#include "GumiLiveConstants.h"
#include "NetworkManager.h"
#include "Utils.h"
#include "PWConstants.h"

using namespace cocos2d;

void GumiLiveNetworkManagement::networkRequestAppleGumiLiveExistData(CCString* gumi_session_to, CCString* gumi_user_id)
{
	auto dict = CCDictionary::create();
	dict->setObject(gumi_user_id, GUMI_APPLE_USER_ID_KEY);
	dict->setObject(gumi_session_to, GUMI_APPLE_SESSION_KEY);

	NetworkManager::shared()->NetworkRequestGet(
		2, 0, (__int64)&v9, GUMI_LIVE_TOKEN_REQUEST_APPLE_EXIST_DATA, this,
		onRequestComplete, 0, 0, 1, dict, 0);
	);
}

void GumiLiveNetworkManagement::networkRequestGumiLiveNewUserLogin(cocos2d::CCDictionary* data)
{
	auto username = data->objectForKey(GUMI_LIVE_USERNAME_KEY);
	auto password = data->objectForKey(GUMI_LIVE_PASSWORD_KEY);

	auto notif = CCNotificationCenter::sharedNotificationCenter();
	notif->postNotification(GUMI_LIVE_UI_INPUT_TEXT_VISIBLE, obj);

	auto dct = CCDictionary::create();
	dct->setObject(CCString::create(Utils::getDeviceModel()), "dn");
	dct->setObject(CCString::create(Utils::getDevicePlatform()), "dp");
	dct->setObject(CCString::create(Utils::getDeviceVID()), "vid");
	dct->setObject(CCString::create(Utils::getDeviceOS()), "v"); 

	if (!appKey.empty())
		dct->setObject(CCString::create(appKey), "ak");
	else
	{
		dct->setObject(CCString::create(unk2), "ak");
	}

	dct->setObject(username, "username");
	dct->setObject(password, "password");

	auto deviceId = cocos2d::CCUserDefault::sharedUserDefault()->getStringForKey(PWConstants::DEVICE_ID);

	dct->setObject(CCString::create(deviceId), "device_id");
	dct->setObject(CCString::create(Utils::getDeviceAltVid()), "altvid");
	dct->setObject(CCString::create(Utils::getUniqueIdentifier()), "identifiers");

	auto net = NetworkManager::shared();

	net->NetworkRequestGet(NetworkManager::HOST::SlApi, NetworkManager::API_VERSION::Default, "accounts/signup", GUMI_LIVE_TOKEN_REQUEST_NEW_USER, this, onNetworkRequestComplete, nullptr, false, false, dct);
}
