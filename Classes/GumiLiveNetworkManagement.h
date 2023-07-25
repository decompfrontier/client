#pragma once

class GumiLiveManager;

#include "PWFacebookUser.h"

class GumiLiveNetworkManagement : public cocos2d::CCObject
{
public:
	GumiLiveNetworkManagement(GumiLiveManager* mngr);
	~GumiLiveNetworkManagement();

	void networkRequestAppleGumiLiveExistData(cocos2d::CCString*, cocos2d::CCString*);
	void networkRequestAppleSignInGumiLiveLogin(cocos2d::CCDictionary*);
	void networkRequestAppleSignInGumiLiveLoginBindCheck(cocos2d::CCDictionary*);
	void networkRequestAppleSignInGumiLiveLoginBinding(cocos2d::CCDictionary*);
	void networkRequestCheckGuestAccount(void);
	void networkRequestFacebookFriendData(cocos2d::CCMutableArray<PWFacebookUser*>*);
	void networkRequestFacebookGumiLiveLogin(cocos2d::CCDictionary*);
	void networkRequestFacebookGumiLiveLoginBindCheck(cocos2d::CCDictionary*);
	void networkRequestFacebookGumiLiveLoginBinding(cocos2d::CCDictionary*);
	void networkRequestGuestGumiLiveLogin(void);
	void networkRequestGumiLiveExistData(cocos2d::CCString*, cocos2d::CCString*);
	void networkRequestGumiLiveExistingLogin(cocos2d::CCDictionary*);
	void networkRequestGumiLiveNewUserLogin(cocos2d::CCDictionary* data);
	void networkRequestGumiLiveNewUserLoginBinding(cocos2d::CCDictionary*);
	void networkRequestPasswordChange(cocos2d::CCString*);
	void onCheckGuestAccountJsonObtained(Json::Value&);
	void onFriendDataRequestComplete(cocos2d::CCNode*, void*);
	void onFriendDataRequestError(cocos2d::CCNode*, void*);
	void onGumiLiveRequestPasswordResetJsonObtained(Json::Value&);
	void onGumiLiveTokenRequestAppleSignInBindCheckJsonObtained(Json::Value&);
	void onGumiLiveTokenRequestAppleSignInExistDataJsonObtained(Json::Value&);
	void onGumiLiveTokenRequestAppleSignInUserBindingJsonObtained(Json::Value&);
	void onGumiLiveTokenRequestAppleSignInUserJsonObtained(Json::Value&);
	void onGumiLiveTokenRequestExistDataJsonObtained(Json::Value&);
	void onGumiLiveTokenRequestExistingUserJsonObtained(Json::Value&);
	void onGumiLiveTokenRequestFacebookBindCheckJsonObtained(Json::Value&);
	void onGumiLiveTokenRequestFacebookUserBindingJsonObtained(Json::Value&);
	void onGumiLiveTokenRequestFacebookUserJsonObtained(Json::Value&);
	void onGumiLiveTokenRequestGuestUserJsonObtained(Json::Value&);
	void onGumiLiveTokenRequestNewUserBindingJsonObtained(Json::Value&);
	void onGumiLiveTokenRequestNewUserJsonObtained(Json::Value&);
	void onNetworkRequestComplete(cocos2d::CCNode*, void*);

	std::string GetAppKey() const { return appKey; }

private:
	std::string appKey;
	//_BYTE[0x10];
};
