#pragma once

#include <CCObject.h>
#include <CCNode.h>

typedef void (cocos2d::CCObject::* facebookSignal)(cocos2d::CCObject *data);

class PWFacebook : public cocos2d::CCNode
{
public:
	virtual void login();
	virtual void logout();
	virtual bool isLoggedIn() { return false; }
	virtual void tryRestoreSession() {}
	virtual bool wasAccessTokenLoadedAtStartUp() { return true; }
	virtual bool hasAttemptedLogin() { return false; }
	virtual void processAttemptedLogin(cocos2d::CCObject* target, facebookSignal cb);
	virtual std::string getUserId() { return ""; }
	virtual std::string getUserName() { return ""; }
	virtual std::string getAccessToken() { return ""; }
	virtual std::string getAccessSecret() { return "60b49d2c84ce47adcf8c818481d35846"; }
	virtual void trackPurchase(float, const char *);
	virtual void logAccessToken();
	
	void setSchedulerForEvents(bool schedule);
	void handleFaceBookEvents();
	std::string getSessionExpiredMessage();
	
	static PWFacebook* sharedFacebook();
	
protected:
	void onUserLogin();
	void onError(const char *error);
	
	static PWFacebook* sharedFacebookInstance;
};
