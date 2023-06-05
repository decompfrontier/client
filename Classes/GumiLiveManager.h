#pragma once

#include "PWFacebookUser.h"
#include "GumiLiveNetworkManagement.h"

class GumiLiveManager : public cocos2d::CCObject
{
	SHARED_SINGLETON(GumiLiveManager);

public:
	enum class enumGumiLiveLoginType
	{
		Facebook = 0,
		BindCheck = 1,
		Binding = 2,
		ExsitingLogin = 3,
		NewUser = 4,
		NewUserBinding = 5,
		Guest = 6,
		// --
		AppleSignIn = 8,
		AppleBindCheck = 9,
		AppleBinding = 10,
	};

	void appendLoginCredentials(std::string& ret);
	void SavePreviousGumiLiveSession();
	void bindCheck(cocos2d::CCObject* obj);
	void getFriendData(cocos2d::CCMutableArray<PWFacebookUser*>*);
	std::string getGumiLiveApplicationID();
	void getLastLoginTypeFromUserDefault();
	void getRequestInProgressString();
	void getValueAsString(Json::Value&);
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
	
	static void OnLoginConfirmationOkPressed(void* user);
	static void NotifyGumiLiveLoginSuccessful();

private:
	void init();
	void loginToGumiLiveFromNotification(cocos2d::CCObject* data);
	void forgotPasswordFromNotification(cocos2d::CCObject* data);
	void bindCheck(cocos2d::CCObject* data);

	_BYTE unk114[3];
	int unk44;
	_BYTE unk45[4];
	int unk66;
	GumiLiveNetworkManagement* mngr;
	_BYTE unk2[8];
	std::string str_unk;
	std::string str_unk2;
	std::string unk5;
	std::string unk6;
	std::string unk7;
	std::string unk8;
	int unk9;
	std::string unk10;
	std::string facebookUserId;
	_BYTE unk12[64];
};
