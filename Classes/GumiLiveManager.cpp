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

GumiLiveManager::GumiLiveManager() : m_mngr(nullptr)
{
	init();
}

GumiLiveManager::~GumiLiveManager()
{

}

void GumiLiveManager::init()
{
	m_mngr = new GumiLiveNetworkManagement(this);

	auto netMngr = NetworkManager::shared();
	auto ptServerUrl = CCUserDefault::sharedUserDefault()->getStringForKey(PT_SERVER_URL);

	if (ptServerUrl.empty())
	{
		std::string url = API_PROTOCOL;
		url += PHP_URL();
		url += API_PROTOCOL_PORT;
		netMngr->setGameUrl(url);
	}

	ptServerUrl = CCUserDefault::sharedUserDefault()->getStringForKey(PT_SERVER_URL);
	if (ptServerUrl != netMngr->getGameUrl())
		netMngr->setGameUrl(ptServerUrl);

	netMngr->setOriginalGameUrl(PHP_URL());
	netMngr->setServiceUrl1(SERVICE_PHP_URL());
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
	ret += m_mngr->getAppKey();
	ret += ",";
	ret += cocos2d::CCUserDefault::sharedUserDefault()->getStringForKey(PWConstants::DEVICE_ID);
	ret += ",";
	ret += Utils::getDeviceAltVid();
	ret += ",";
	ret += m_gumiLiveId;
	ret += ",";
	ret += m_gumiLiveToken;
}

void GumiLiveManager::NotifyGumiLiveLoginSuccessful()
{
	CCNotificationCenter::sharedNotificationCenter()->postNotification(GUMI_LIVE_LOGIN_SUCCESSFUL);
}

void GumiLiveManager::OnLoginConfirmationOkPressed(void*)
{
	if (!SINGLETON_INSTANCE(GumiLiveManager))
		SINGLETON_NEW(GumiLiveManager);

	CCNotificationCenter::sharedNotificationCenter()->postNotification(GUMI_LIVE_LOGIN_SUCCESSFUL);
}

void GumiLiveManager::SavePreviousGumiLiveSession()
{
	if (!unk6.length() || (unk7.length() && !unk8.length()))
		return;

	SaveData::shared()->deleteKeyChain();

	if (!unk7.length())
		str_unk2 = unk8;
	else
		str_unk2 = unk7;

	if (facebookUserId.length() > 0)
	{
		//unk9 = unk12;
		facebookUserId = "";
		setLastLoginTypeToUserDefault(enumGumiLiveLoginType::FacebookBinding);
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
	m_loginType = type;

	switch (type)
	{
	case enumGumiLiveLoginType::FacebookSignIn:
		m_mngr->networkRequestFacebookGumiLiveLogin(data);
		break;

	case enumGumiLiveLoginType::FacebookBindCheck:
		m_mngr->networkRequestFacebookGumiLiveLoginBindCheck(data);
		break;

	case enumGumiLiveLoginType::FacebookBinding:
		m_mngr->networkRequestFacebookGumiLiveLoginBinding(data);
		break;

	case enumGumiLiveLoginType::ExsitingLogin:
		m_mngr->networkRequestGumiLiveExistingLogin(data);
		break;

	case enumGumiLiveLoginType::NewUser:
		m_mngr->networkRequestGumiLiveNewUserLogin(data);
		break;

	case enumGumiLiveLoginType::NewUserBinding:
		m_mngr->networkRequestGumiLiveNewUserLoginBinding(data);
		break;

	case enumGumiLiveLoginType::Guest:
		m_mngr->networkRequestFacebookGumiLiveLogin(data);
		break;

	case enumGumiLiveLoginType::AppleSignIn:
		m_mngr->networkRequestAppleSignInGumiLiveLogin(data);
		break;

	case enumGumiLiveLoginType::AppleBindCheck:
		m_mngr->networkRequestAppleSignInGumiLiveLoginBindCheck(data);
		break;

	case enumGumiLiveLoginType::AppleBinding:
		m_mngr->networkRequestAppleSignInGumiLiveLoginBinding(data);
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

	m_mngr->networkRequestGumiLiveExistData((cocos2d::CCString*)tokenKey, (cocos2d::CCString*)gameKey);
}

void GumiLiveManager::checkGuestAccountStatusForCurrentDevice()
{
	m_mngr->networkRequestCheckGuestAccount();
}

void GumiLiveManager::forgotPasswordFromNotification(cocos2d::CCObject* username)
{
	m_mngr->networkRequestPasswordChange(dynamic_cast<cocos2d::CCString*>(username));
}

void GumiLiveManager::getFriendData(cocos2d::CCMutableArray<PWFacebookUser*>* friends)
{
	m_mngr->networkRequestFacebookFriendData(friends);
}

std::string GumiLiveManager::getGumiLiveApplicationID()
{
	return m_mngr->getAppKey();
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
	return EnumString<enumGumiLiveLoginType>::From(m_loginType);
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

void GumiLiveManager::bindCheck(cocos2d::CCObject* obj)
{
	auto dict = dynamic_cast<cocos2d::CCDictionary*>(obj);

	auto tokenKey = dict->objectForKey(TOKEN_KEY);
	auto userIdKey = dict->objectForKey(GAME_USER_ID_KEY);
	m_mngr->networkRequestGumiLiveExistData(dynamic_cast<cocos2d::CCString*>(tokenKey), dynamic_cast<cocos2d::CCString*>(userIdKey));
}

void GumiLiveManager::marketSamsung(std::string receipt, std::string clientId)
{
	// DECOMP NOTE: No reason to go this route...
	//if (!SINGLETON_INSTANCE(GumiLiveManager))
	//	SINGLETON_NEW(GumiLiveManager);
	//auto liveMngr = SINGLETON_INSTANCE(GumiLiveManager);

	auto dct = CCDictionary::create();

	dct->setObject(CCString::create(getGumiLiveID()), GUMI_LIVEID);
	dct->setObject(CCString::create(Utils::getAppKey()), GUMI_APPKEY);

	CCString* ccUserId;
	if (receipt.empty())
		ccUserId = CCString::create("samsung_1");
	else
		ccUserId = CCString::createWithFormat("samsung_%s", receipt.c_str());

	dct->setObject(CCString::create(receipt), GUMI_RECEIPT);
	dct->setObject(ccUserId, GUMI_CLIENTID);

	NetworkManager::shared()->NetworkRequest(
		NetworkManager::HOST::SlApi,
		NetworkManager::API_VERSION::Default,
		GUMI_LIVE_PATH_WALLET_SAMSUNG,
		dct,
		extension::CCHttpRequest::kHttpGet,
		extension::CCHttpRequest::HttpRequestHandlingType::kDefault,
		NetworkManager::CONTENT_TYPE::UrlEncoded,
		"",
		PWConstants::PURCHASE_VERIFY_TAG,
		this,
		(NetworkManager::RequestComplete)&GumiLiveManager::paymentVerified,
		nullptr,
		false,
		true,
		true
	);
}

std::string GumiLiveManager::getValueAsString(Json::Value& v)
{
	if (v.isInt())
	{
		// this is technically what the code does but inlined
		return CommonUtils::IntToString(v.asInt());
	}
	else if (v.isString())
		return v.asString();

	return "";
}

void GumiLiveManager::onFriendDataUnSuccessful()
{
	CCNotificationCenter::sharedNotificationCenter()->postNotification(GUMI_LIVE_FRIEND_DATA);
}

void GumiLiveManager::setLastLoginTypeToUserDefault(enumGumiLiveLoginType type)
{
	SaveData::shared()-> ? ? ? ;
}

void GumiLiveManager::paymentVerified(cocos2d::CCNode* node, void* userData)
{
	if (NetworkManager::shared()->onNetworkRequestComplete(userData, false, true))
	{
		Json::Value v;
		Utils::ReadIntoJson(userData, v, false);

		CCInteger* obj;

		auto amount = v[GUMI_PAYMENT_TOPUP_AMOUNT].asInt();
		auto status = v[GUMI_PAYMENT_STATUS].asString(); // I think this is done just to raise the exception if it's not found

		if (!amount)
			obj = CCInteger::create(-v[GUMI_PAYMENT_STATUS_NO].asInt());
		else
			obj = CCInteger::create(amount);

		CCNotificationCenter::sharedNotificationCenter()->postNotification(PWConstants::GUMI_LIVE_PURCHASE_VERIFIED, obj);
	}
}
