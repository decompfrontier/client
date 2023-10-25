#include "pch.h"
#include "GumiLiveManager.h"
#include "Utils.h"
#include "PWConstants.h"
#include "GumiLiveConstants.h"
#include "NetworkManager.h"
#include "ServerConfig.h"
#include "SaveData.h"

SET_SHARED_SINGLETON(GumiLiveManager);

using namespace cocos2d;

/* not 100% the same as original but who cares... */
SHARED_SINGLETON_BODY(GumiLiveManager)
{
	if (!SINGLETON_INSTANCE(GumiLiveManager))
	{
		SINGLETON_NEW(GumiLiveManager);
		SINGLETON_INSTANCE(GumiLiveManager)->init();
	}

	return SINGLETON_INSTANCE(GumiLiveManager);
}

void GumiLiveManager::init()
{
	mngr = new GumiLiveNetworkManagement(this);

	auto netMngr = NetworkManager::shared();
	auto ptServerUrl = CCUserDefault::sharedUserDefault()->getStringForKey(PT_SERVER_URL);

	if (ptServerUrl.empty())
	{
		std::string url = "https://";
		url += PHP_URL();
		url += ":80";
		netMngr->setGameUrl(url);
	}

	ptServerUrl = CCUserDefault::sharedUserDefault()->getStringForKey(PT_SERVER_URL);
	if (ptServerUrl != netMngr->getGameUrl())
		netMngr->setGameUrl(ptServerUrl);

	netMngr->setServiceUrl(SERVICE_PHP_URL());
	netMngr->setServiceUrl2(SERVICE_PHP_URL2());
	netMngr->setAppKey(Utils::getAppKey());

	CCNotificationCenter::sharedNotificationCenter()->addObserver(this, (cocos2d::SEL_CallFuncO)loginToGumiLiveFromNotification, GUMI_LIVE_MANAGER_LOGIN, nullptr);
	CCNotificationCenter::sharedNotificationCenter()->addObserver(this, (cocos2d::SEL_CallFuncO)forgotPasswordFromNotification, GUMI_LIVE_FORGOT_PASSWORD, nullptr);
	CCNotificationCenter::sharedNotificationCenter()->addObserver(this, (cocos2d::SEL_CallFuncO)bindCheck, GUMI_LIVE_BIND_CHECK, nullptr);
}

void GumiLiveManager::appendLoginCredentials(std::string& ret)
{
	ret += Utils::getDeviceModel();
	ret += ",";
	ret += Utils::getDevicePlatform();
	ret += ",";
	ret += Utils::getDeviceVID();
	ret += ",";
	ret += Utils::getDeviceOS();
	ret += ",";
	ret += unk2[8];//unk!
	ret += ",";
	ret += cocos2d::CCUserDefault::sharedUserDefault()->getStringForKey(PWConstants::DEVICE_ID);
	ret += ",";
	ret += Utils::getDeviceAltVid();
	ret += ",";
	ret += str_unk2; // unk2!
	ret += ",";
	ret += token;
}

void GumiLiveManager::NotifyGumiLiveLoginSuccessful()
{
	CCNotificationCenter::sharedNotificationCenter()->postNotification(GUMI_LIVE_LOGIN_SUCCESSFUL);
}

void GumiLiveManager::OnLoginConfirmationOkPressed(void*)
{
	if (!SINGLETON_INSTANCE(GumiLiveManager))
	{
		SINGLETON_NEW(GumiLiveManager);
		SINGLETON_INSTANCE(GumiLiveManager)->init();
	}

	CCNotificationCenter::sharedNotificationCenter()->postNotification(GUMI_LIVE_LOGIN_SUCCESSFUL);
}

void GumiLiveManager::SavePreviousGumiLiveSession()
{
	if (!unk6.length() || (unk7.length() && !unk8.length()))
		return;

	auto sd = SaveData::shared();
	sd->deleteKeyChain();

	if (!unk7.length())
		str_unk2 = unk8;
	else
		str_unk2 = unk7;

	if (facebookUserId.length() > 0)
	{
		//unk9 = unk12;
		facebookUserId = "";
		setLastLoginTypeToUserDefault(enumGumiLiveLoginType::Binding);
	}

	/*
	if (unk12[40])
	{
		if (!unk12[48])
		{
			loginType = enumGumiLiveLoginType::Unknown;
			sd->saveKeyChain();
			unk6 = "";
			unk7 = "";
			return;
		}
	}

	// ?
	unk12[16] = unk12[56];
	unk12[40] = "";*/

	setLastLoginTypeToUserDefault(enumGumiLiveLoginType::AppleBinding);
}

void GumiLiveManager::loginToGumiLive(enumGumiLiveLoginType type, CCDictionary* data)
{
	switch (type)
	{
	case enumGumiLiveLoginType::Facebook:
		mngr->networkRequestFacebookGumiLiveLogin(data);
		break;

	case enumGumiLiveLoginType::BindCheck:
		mngr->networkRequestFacebookGumiLiveLoginBindCheck(data);
		break;

	case enumGumiLiveLoginType::Binding:
		mngr->networkRequestFacebookGumiLiveLoginBinding(data);
		break;

	case enumGumiLiveLoginType::ExsitingLogin:
		mngr->networkRequestGumiLiveExistingLogin(data);
		break;

	case enumGumiLiveLoginType::NewUser:
		mngr->networkRequestGumiLiveNewUserLogin(data);
		break;

	case enumGumiLiveLoginType::NewUserBinding:
		mngr->networkRequestGumiLiveNewUserLoginBinding(data);
		break;

	case enumGumiLiveLoginType::Guest:
		mngr->networkRequestFacebookGumiLiveLogin(data);
		break;

	case enumGumiLiveLoginType::AppleSignIn:
		mngr->networkRequestAppleSignInGumiLiveLogin(data);
		break;

	case enumGumiLiveLoginType::AppleBindCheck:
		mngr->networkRequestAppleSignInGumiLiveLoginBindCheck(data);
		break;

	case enumGumiLiveLoginType::AppleBinding:
		mngr->networkRequestAppleSignInGumiLiveLoginBinding(data);
		break;

	default:
		break;
	}
}

void GumiLiveManager::bindCheck(cocos2d::CCObject* obj)
{
	auto dict = (cocos2d::CCDictionary*)obj;
	auto tokenKey = dict->objectForKey(TOKEN_KEY);
	auto gameKey = dict->objectForKey(GAME_USER_ID_KEY);

	mngr->networkRequestGumiLiveExistData((cocos2d::CCString*)tokenKey, (cocos2d::CCString*)gameKey);
}

void GumiLiveManager::checkGuestAccountStatusForCurrentDevice()
{
	mngr->networkRequestCheckGuestAccount();
}

void GumiLiveManager::forgotPasswordFromNotification(cocos2d::CCObject* ptr)
{
	mngr->networkRequestPasswordChange((cocos2d::CCString*)ptr);
}

void GumiLiveManager::getFriendData(cocos2d::CCMutableArray<PWFacebookUser*>* out)
{
	mngr->networkRequestFacebookFriendData(out);
}

std::string GumiLiveManager::getGumiLiveApplicationID()
{
	return mngr->GetAppKey();
}

enumGumiLiveLoginType GumiLiveManager::getLastLoginTypeFromUserDefault()
{
	auto s = EnumString<enumGumiLiveLoginType>::From(enumGumiLiveLoginType::Unknown);

	auto sd = SaveData::shared();

	// TODO

	enumGumiLiveLoginType e;
	EnumString<enumGumiLiveLoginType>::To(e, x);
	return e;
}

std::string GumiLiveManager::getRequestInProgressString()
{
	return EnumString<enumGumiLiveLoginType>::From(loginType);
}

std::string GumiLiveManager::getValueAsString(Json::Value& value)
{
	if (value.isInt())
	{
		return CommonUtils::IntToString(value.asInt());
	}
	else if (value.isString())
	{
		return value.asString();
	}
}

bool GumiLiveManager::hasAttemptedAppleSignIn()
{
	auto sd = SaveData::shared();
}
