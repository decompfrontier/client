#include "pch.h"
#include "GumiLiveManager.h"
#include "Utils.h"
#include "PWConstants.h"
#include "GumiLiveConstants.h"
#include "NetworkManager.h"
#include "ServerConfig.h"

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
	mngr = new GumiLiveNetworkManagement();

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
	ret += ?;//unk!
	ret += ",";
	ret += cocos2d::CCUserDefault::sharedUserDefault()->getStringForKey(PWConstants::DEVICE_ID);
	ret += ",";
	ret += Utils::getDeviceAltVid();
	ret += ",";
	ret += str_unk2; // unk2!
	ret += ",";
	ret += str_unk; // unk3!,
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
	// TODO
}

void GumiLiveManager::loginToGumiLive(enumGumiLiveLoginType type, CCDictionary* data)
{
	switch (type)
	{
	case enumGumiLiveLoginType::Facebook:
		break;

	case enumGumiLiveLoginType::BindCheck:
		break;

	case enumGumiLiveLoginType::Binding:
		break;

	case enumGumiLiveLoginType::ExsitingLogin:
		break;

	case enumGumiLiveLoginType::NewUser:
		break;

	case enumGumiLiveLoginType::NewUserBinding:
		break;

	case enumGumiLiveLoginType::Guest:
		break;

	case enumGumiLiveLoginType::AppleSignIn:
		break;

	case enumGumiLiveLoginType::AppleBindCheck:
		break;

	case enumGumiLiveLoginType::AppleBinding:
		break;

	default:
		break;
	}
}
