#include "pch.h"
#include "GumiLiveNetworkManagement.h"
#include "GumiLiveConstants.h"
#include "NetworkManager.h"
#include "Utils.h"
#include "PWConstants.h"

using namespace cocos2d;

void GumiLiveNetworkManagement::networkRequestCheckGuestAccount()
{
	auto dict = CCDictionary::create();
	dict->setObject(CCString::create(Utils::getDeviceModel()), GUMI_DEVICEMODEL);
	dict->setObject(CCString::create(Utils::getDevicePlatform()), GUMI_DEVICEPLATFORM);
	dict->setObject(CCString::create(Utils::getDeviceVID()), GUMI_DEVICEVID);
	dict->setObject(CCString::create(Utils::getDeviceOS()), GUMI_DEVICEOS);
	dict->setObject(CCString::create(m_appKey), GUMI_APPKEY);
	dict->setObject(CCString::create(CCUserDefault::sharedUserDefault()->getStringForKey(PWConstants::DEVICE_ID)), GUMI_DEVICEID);
	dict->setObject(CCString::create(Utils::getDeviceAltVid()), GUMI_DEVICEALTVID);
	dict->setObject(CCString::create(Utils::getUniqueIdentifier()), GUMI_UNIQUEIDENTIFIERS);

	NetworkManager::shared()->NetworkRequestGet(
		NetworkManager::HOST::SlApi,
		NetworkManager::API_VERSION::Default,
		GUMI_LIVE_PATH_GUEST_GET,
		CHECK_FOR_GUMI_LIVE_GUEST_ACCOUNT,
		this,
		(NetworkManager::RequestComplete)&GumiLiveNetworkManagement::onNetworkRequestComplete,
		nullptr,
		false,
		true,
		dict
	);
}

void GumiLiveNetworkManagement::networkRequestAppleGumiLiveExistData(CCString* gumi_session_to, CCString* gumi_user_id)
{
	auto dict = CCDictionary::create();
	dict->setObject(gumi_user_id, GUMI_USER_ID_KEY);
	dict->setObject(gumi_session_to, GUMI_SESSION_TO);

	NetworkManager::shared()->NetworkRequestGet(
		NetworkManager::HOST::External,
		NetworkManager::API_VERSION::Default,
		GUMI_LIVE_PATH_APPLE_ACTION,
		GUMI_LIVE_TOKEN_REQUEST_APPLE_EXIST_DATA,
		this,
		(NetworkManager::RequestComplete)&GumiLiveNetworkManagement::onNetworkRequestComplete,
		nullptr,
		false,
		true,
		dict
	);
}

void GumiLiveNetworkManagement::networkRequestGumiLiveExistData(cocos2d::CCString* tokenKey, cocos2d::CCString* userIdKey)
{
	auto dict = CCDictionary::create();
	dict->setObject(userIdKey, GUMI_USER_ID_KEY);
	dict->setObject(tokenKey, GUMI_SESSION_TO);

	NetworkManager::shared()->NetworkRequestGet(
		NetworkManager::HOST::External,
		NetworkManager::API_VERSION::Default,
		GUMI_LIVE_PATH_FACEBOOK_ACTION,
		GUMI_LIVE_TOKEN_REQUEST_EXIST_DATA,
		this,
		(NetworkManager::RequestComplete)&GumiLiveNetworkManagement::onNetworkRequestComplete,
		nullptr,
		false,
		true,
		dict
	);
}

void GumiLiveNetworkManagement::networkRequestAppleSignInGumiLiveLogin(cocos2d::CCDictionary* data)
{
	std::string userId = ((CCString*)data->objectForKey(APPLE_USER_ID_KEY))->getCString();
	std::string jwtSecret = ((CCString*)data->objectForKey(APPLE_JWT_SECRET))->getCString();

	auto dct = CCDictionary::create();
	dct->setObject(CCString::create(Utils::getDeviceModel()), GUMI_DEVICEMODEL);
	dct->setObject(CCString::create(Utils::getDevicePlatform()), GUMI_DEVICEPLATFORM);
	dct->setObject(CCString::create(Utils::getDeviceVID()), GUMI_DEVICEVID);
	dct->setObject(CCString::create(Utils::getDeviceOS()), GUMI_DEVICEOS);
	dct->setObject(CCString::create(m_appKey), GUMI_APPKEY);
	dct->setObject(CCString::createWithFormat("%s|%s", userId.c_str(), jwtSecret.c_str()), GUMI_APPLE_ACCESS_IN);
	dct->setObject(CCString::create(cocos2d::CCUserDefault::sharedUserDefault()->getStringForKey(PWConstants::DEVICE_ID)), "device_id");
	dct->setObject(CCString::create(Utils::getDeviceAltVid()), GUMI_DEVICEALTVID);
	dct->setObject(CCString::create(Utils::getUniqueIdentifier()), GUMI_UNIQUEIDENTIFIERS);

	NetworkManager::shared()->NetworkRequestGet(
		NetworkManager::HOST::SlApi,
		NetworkManager::API_VERSION::Default,
		GUMI_LIVE_PATH_LOGIN,
		GUMI_LIVE_TOKEN_REQUEST_APPLE,
		this,
		(NetworkManager::RequestComplete)&GumiLiveNetworkManagement::onNetworkRequestComplete,
		nullptr,
		false,
		true,
		dct
	);
}

void GumiLiveNetworkManagement::networkRequestGumiLiveNewUserLogin(cocos2d::CCDictionary* data)
{
	auto username = data->objectForKey(GUMI_LIVE_USERNAME_KEY);
	auto password = data->objectForKey(GUMI_LIVE_PASSWORD_KEY);

	auto notif = CCNotificationCenter::sharedNotificationCenter();
	auto obj = CCBool::create(false);
	notif->postNotification(GUMI_LIVE_UI_INPUT_TEXT_VISIBLE, obj);

	auto dct = CCDictionary::create();
	dct->setObject(CCString::create(Utils::getDeviceModel()), GUMI_DEVICEMODEL);
	dct->setObject(CCString::create(Utils::getDevicePlatform()), GUMI_DEVICEPLATFORM);
	dct->setObject(CCString::create(Utils::getDeviceVID()), GUMI_DEVICEVID);
	dct->setObject(CCString::create(Utils::getDeviceOS()), GUMI_DEVICEOS);
	dct->setObject(CCString::create(m_appKey), GUMI_APPKEY);

	dct->setObject(username, GUMI_USERNAME);
	dct->setObject(password, GUMI_PASSWORD);
	dct->setObject(CCString::create(cocos2d::CCUserDefault::sharedUserDefault()->getStringForKey(PWConstants::DEVICE_ID)), "device_id");
	dct->setObject(CCString::create(Utils::getDeviceAltVid()), GUMI_DEVICEALTVID);
	dct->setObject(CCString::create(Utils::getUniqueIdentifier()), GUMI_UNIQUEIDENTIFIERS);

	NetworkManager::shared()->NetworkRequestGet(
		NetworkManager::HOST::SlApi,
		NetworkManager::API_VERSION::Default,
		GUMI_LIVE_PATH_ACCOUNT_SIGNUP,
		GUMI_LIVE_TOKEN_REQUEST_NEW_USER,
		this,
		(NetworkManager::RequestComplete)&GumiLiveNetworkManagement::onNetworkRequestComplete,
		nullptr,
		false,
		true,
		dct
	);
}

void GumiLiveNetworkManagement::networkRequestGuestGumiLiveLogin()
{
	auto dct = CCDictionary::create();
	dct->setObject(CCString::create(Utils::getDeviceModel()), GUMI_DEVICEMODEL);
	dct->setObject(CCString::create(Utils::getDevicePlatform()), GUMI_DEVICEPLATFORM);
	dct->setObject(CCString::create(CommonUtils::getDeviceID()), GUMI_DEVICEVID); // DECOMP NOTE: it was made Utils::getDeviceAdvertisingID, probably a mistake from gumi itself. Corrected here!
	dct->setObject(CCString::create(Utils::getDeviceOS()), GUMI_DEVICEOS);
	dct->setObject(CCString::create(m_appKey), GUMI_APPKEY);
	dct->setObject(CCString::create(cocos2d::CCUserDefault::sharedUserDefault()->getStringForKey(PWConstants::DEVICE_ID)), "device_id");
	dct->setObject(CCString::create(Utils::getDeviceAltVid()), GUMI_DEVICEALTVID);
	dct->setObject(CCString::create(Utils::getUniqueIdentifier()), GUMI_UNIQUEIDENTIFIERS);
	dct->setObject(CCString::create(CommonUtils::getDeviceAdvertisingID()), GUMI_DEVICEADID);

	NetworkManager::shared()->NetworkRequestGet(
		NetworkManager::HOST::SlApi,
		NetworkManager::API_VERSION::Default,
		GUMI_LIVE_PATH_GUEST_LOGIN,
		GUMI_LIVE_TOKEN_REQUEST_GUEST_USER,
		this,
		(NetworkManager::RequestComplete)&GumiLiveNetworkManagement::onNetworkRequestComplete,
		nullptr,
		false,
		true,
		dct
	);
}

void GumiLiveNetworkManagement::networkRequestPasswordChange(cocos2d::CCString* username)
{
	auto dct = CCDictionary::create();
	dct->setObject(username, GUMI_USERNAME);
	dct->setObject(CCString::create(Utils::getUniqueIdentifier()), GUMI_UNIQUEIDENTIFIERS);
	NetworkManager::shared()->NetworkRequestGet(
		NetworkManager::HOST::SlApi,
		NetworkManager::API_VERSION::Default,
		GUMI_LIVE_PATH_GENERATE_RESET_PASSWORD,
		GUMI_LIVE_REQUEST_PASSWORD_RESET,
		this,
		(NetworkManager::RequestComplete)&GumiLiveNetworkManagement::onNetworkRequestComplete,
		nullptr,
		false,
		true,
		dct
	);
}

void GumiLiveNetworkManagement::networkRequestFacebookFriendData(cocos2d::CCMutableArray<PWFacebookUser*>* friends)
{
	std::string friendUserIDs = "";

	for (unsigned int i = 0; i < friends->count(); i++)
	{
		auto fbFriend = friends->getObjectAtIndex(i);

		if (i > 0)
			friendUserIDs += ",";

		friendUserIDs += fbFriend->getUserID();
	}

	auto dct = CCDictionary::create();
	dct->setObject(CCString::create(friendUserIDs), GUMI_IDS);

	NetworkManager::shared()->NetworkRequestGet(
		NetworkManager::HOST::External,
		NetworkManager::API_VERSION::Default,
		GUMI_LIVE_PATH_FACEBOOK_FRIENDLIST,
		GUMI_LIVE_REQUEST_FACEBOOK_FRIEND_DATA,
		this,
		(NetworkManager::RequestComplete)&GumiLiveNetworkManagement::onFriendDataRequestComplete,
		(NetworkManager::RequestFail)&GumiLiveNetworkManagement::GumiLiveNetworkManagement::onFriendDataRequestError,
		false,
		true,
		dct
	);
}

void GumiLiveNetworkManagement::onNetworkRequestComplete(cocos2d::CCObject* res, void* userData)
{
	if (NetworkManager::shared()->onNetworkRequestComplete(userData, false, true))
	{
		Json::Value v;
		const char* key = ? ;
		std::string value = ? ;
		Utils::ReadIntoJson(value, v, true);
		if (!strcmp(key, GUMI_LIVE_TOKEN_REQUEST_NEW_USER))
		{
			onGumiLiveTokenRequestNewUserJsonObtained(v);
		}
		else if (!strcmp(key, GUMI_LIVE_TOKEN_REQUEST_NEW_USER_BINDING))
		{
			onGumiLiveTokenRequestNewUserBindingJsonObtained(v);
		}
		else if (!strcmp(key, GUMI_LIVE_TOKEN_REQUEST_EXISTING_USER))
		{
			onGumiLiveTokenRequestExistingUserJsonObtained(v);
		}
		else if (!strcmp(key, GUMI_LIVE_TOKEN_REQUEST_GUEST_USER))
		{
			onGumiLiveTokenRequestGuestUserJsonObtained(v);
		}
		else if (!strcmp(key, GUMI_LIVE_TOKEN_REQUEST_FACEBOOK))
		{
			onGumiLiveTokenRequestFacebookUserJsonObtained(v);
		}
		else if (!strcmp(key, GUMI_LIVE_TOKEN_REQUEST_FACEBOOK_BIND_CHECK))
		{
			onGumiLiveTokenRequestFacebookBindCheckJsonObtained(v);
		}
		else if (!strcmp(key, GUMI_LIVE_TOKEN_REQUEST_EXIST_DATA))
		{
			onGumiLiveTokenRequestExistDataJsonObtained(v);
		}
		else if (!strcmp(key, GUMI_LIVE_TOKEN_REQUEST_FACEBOOK_BINDING))
		{
			onGumiLiveTokenRequestFacebookUserBindingJsonObtained(v);
		}
		else if (!strcmp(key, CHECK_FOR_GUMI_LIVE_GUEST_ACCOUNT))
		{
			onCheckGuestAccountJsonObtained(v);
		}
		else if (!strcmp(key, GUMI_LIVE_REQUEST_PASSWORD_RESET))
		{
			onGumiLiveRequestPasswordResetJsonObtained(v);
		}
		else if (!strcmp(key, GUMI_LIVE_TOKEN_REQUEST_APPLE))
		{
			onGumiLiveTokenRequestAppleSignInUserJsonObtained(v);
		}
		else if (!strcmp(key, GUMI_LIVE_TOKEN_REQUEST_APPLE_BIND_CHECK))
		{
			onGumiLiveTokenRequestAppleSignInBindCheckJsonObtained(v);
		}
		else if (!strcmp(key, GUMI_LIVE_TOKEN_REQUEST_APPLE_EXIST_DATA))
		{
			onGumiLiveTokenRequestAppleSignInExistDataJsonObtained(v);
		}
		else if (!strcmp(key, GUMI_LIVE_TOKEN_REQUEST_APPLE_BINDING))
		{
			onGumiLiveTokenRequestAppleSignInUserBindingJsonObtained(v);
		}
	}
}
