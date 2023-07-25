#pragma once

#include "PWFacebookUser.h"
#include "GumiLiveNetworkManagement.h"

constexpr auto GAME_USER_ID_KEY = "game_user_id";
constexpr auto TOKEN_KEY = "token";

enum class enumGumiLiveLoginType
{
	Facebook = 0,
	BindCheck = 1,
	Binding = 2,
	ExsitingLogin = 3,
	NewUser = 4,
	NewUserBinding = 5,
	Guest = 6,
	Unknown = 7, // Default?
	AppleSignIn = 8,
	AppleBindCheck = 9,
	AppleBinding = 10,
};


Begin_Enum_String(enumGumiLiveLoginType)
{
	Enum_String(Facebook);
	Enum_String(BindCheck);
	Enum_String(Binding);
	Enum_String(ExsistingLogin);
	Enum_String(NewUser);
	Enum_String(NewUserBinding);
	Enum_String(Guest);
	// --
	Enum_String(AppleSignIn);
	Enum_String(AppleBindCheck);
	Enum_String(AppleBinding);
}
End_Enum_String;


class GumiLiveManager : public cocos2d::CCObject
{
	SHARED_SINGLETON(GumiLiveManager);

public:


	void appendLoginCredentials(std::string& ret);
	void SavePreviousGumiLiveSession();
	void bindCheck(cocos2d::CCObject* obj);
	void getFriendData(cocos2d::CCMutableArray<PWFacebookUser*>* out);
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
	void marketSamsung(std::string, std::string);
	void onFriendDataSuccessful(Json::Value&);
	void onFriendDataUnSuccessful();
	void onLoginToGumiLiveBindCheck(Json::Value&);
	void onLoginToGumiLiveExistData(Json::Value&);
	void onLoginToGumiLiveSuccessful(Json::Value&);
	void onLoginToGumiLiveUnSuccessful(Json::Value&);
	void onPasswordResetSuccessful(Json::Value&);
	void onPasswordResetUnSuccessful(Json::Value&);
	void paymentVerified(cocos2d::CCNode*, void*);
	void sendUserCountry(std::string, std::string);
	void setLastLoginTypeToUserDefault(enumGumiLiveLoginType type);
	void checkGuestAccountStatusForCurrentDevice();

	std::string getToken() const { return token; }

	static void OnLoginConfirmationOkPressed(void* user);
	static void NotifyGumiLiveLoginSuccessful();

private:
	void init();
	void loginToGumiLiveFromNotification(cocos2d::CCObject* data);
	void forgotPasswordFromNotification(cocos2d::CCObject* data);
	void bindCheck(cocos2d::CCObject* data);

	// byte[3];
	enumGumiLiveLoginType loginType;
	// byte[4];
	int unk66;
	GumiLiveNetworkManagement* mngr;
	// byte[8];
	std::string token;
	std::string str_unk2;
	std::string unk5;
	std::string unk6;
	std::string unk7;
	std::string unk8;
	int unk9;
	std::string unk10;
	std::string facebookUserId;
	// byte[64];
};
