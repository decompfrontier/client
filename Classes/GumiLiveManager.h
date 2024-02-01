#pragma once

#include "PWFacebookUser.h"
#include "GumiLiveNetworkManagement.h"

enum class enumGumiLiveLoginType
{
	FacebookSignIn = 0,
	FacebookBindCheck = 1,
	FacebookBinding = 2,
	ExsitingLogin = 3,
	NewUser = 4,
	NewUserBinding = 5,
	Guest = 6,
	Invalid = 7,
	AppleSignIn = 8,
	AppleBindCheck = 9,
	AppleBinding = 10,
};


// DECOMP: names are not like decomp but who cares, it's more clean in this way imo
Begin_Enum_String(enumGumiLiveLoginType)
{
	Enum_String(enumGumiLiveLoginType::FacebookSignIn);
	Enum_String(enumGumiLiveLoginType::FacebookBindCheck);
	Enum_String(enumGumiLiveLoginType::FacebookBinding);
	Enum_String(enumGumiLiveLoginType::ExsitingLogin);
	Enum_String(enumGumiLiveLoginType::NewUser);
	Enum_String(enumGumiLiveLoginType::NewUserBinding);
	Enum_String(enumGumiLiveLoginType::Guest);
	Enum_String(enumGumiLiveLoginType::Invalid)
	Enum_String(enumGumiLiveLoginType::AppleSignIn);
	Enum_String(enumGumiLiveLoginType::AppleBindCheck);
	Enum_String(enumGumiLiveLoginType::AppleBinding);
}
End_Enum_String;


class GumiLiveManager : public cocos2d::CCObject
{
	SHARED_SINGLETON(GumiLiveManager);

public:
	GumiLiveManager() : m_mngr(nullptr) {}
	~GumiLiveManager();

	void appendLoginCredentials(std::string& ret);
	void SavePreviousGumiLiveSession();
	void bindCheck(cocos2d::CCObject* obj);
	void getFriendData(cocos2d::CCMutableArray<PWFacebookUser*>* friends);
	std::string getGumiLiveApplicationID();
	enumGumiLiveLoginType getLastLoginTypeFromUserDefault();
	std::string getRequestInProgressString();
	std::string getValueAsString(Json::Value& value);
	bool hasAttemptedAppleSignIn();
	bool hasAttemptedFacebookLogIn();
	void loginToGumiLive(enumGumiLiveLoginType type, cocos2d::CCDictionary* data);
	void marketAmazon(std::string, std::string);
	void marketGoogle(std::string, std::string);
	void marketMobogenie(std::string, std::string, std::string, std::string);
	void marketSamsung(std::string receipt, std::string clientId);
	void sendUserCountry(std::string, std::string);
	void setLastLoginTypeToUserDefault(enumGumiLiveLoginType type);
	void checkGuestAccountStatusForCurrentDevice();

	static void OnLoginConfirmationOkPressed(void* user);
	static void NotifyGumiLiveLoginSuccessful();

	CC_SYNTHESIZE_READONLY(std::string, m_gumiLiveId, GumiLiveID);
	CC_SYNTHESIZE_READONLY(enumGumiLiveLoginType, m_loginType, LoginType);
	CC_SYNTHESIZE_READONLY(std::string, m_gumiLiveToken, GumiLiveToken);
	CC_SYNTHESIZE_READONLY(std::string, m_appleId, AppleID);
	CC_SYNTHESIZE_READONLY(std::string, m_facebookId, FacebookID);

private:
	void init();
	void loginToGumiLiveFromNotification(cocos2d::CCObject* data);
	void forgotPasswordFromNotification(cocos2d::CCObject* username);
	void bindCheck(cocos2d::CCObject* dataDict);
	void paymentVerified(cocos2d::CCNode* _unused, void* userData);
	void onFriendDataSuccessful(Json::Value&);
	void onFriendDataUnSuccessful();
	void onLoginToGumiLiveBindCheck(Json::Value&);
	void onLoginToGumiLiveExistData(Json::Value&);
	void onLoginToGumiLiveSuccessful(Json::Value&);
	void onLoginToGumiLiveUnSuccessful(Json::Value&);
	void onPasswordResetSuccessful(Json::Value&);
	void onPasswordResetUnSuccessful(Json::Value&);

	GumiLiveNetworkManagement* m_mngr;
};
