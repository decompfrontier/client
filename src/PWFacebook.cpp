#include "Pch.hpp"
#include "PWFacebook.hpp"
#include "Utils.hpp"

void PWFacebook::onUserLogin()
{
	cocos2d::CCNotificationCenter::sharedNotificationCenter()->postNotification("FacebookLogin");
}

void PWFacebook::onError(const char *error)
{
	if (error)
	{
		std::string localeMsg = Utils::localText("TXT_LOGIN_FACEBOOK_UNREACHABLE");
		localeMsg += "<br>";
		localeMsg += error;
	}
	else
	{
		std::string localeMsg = Utils::localText("TXT_LOGIN_FACEBOOK_UNREACHABLE");
	}
	
	cocos2d::CCNotificationCenter::sharedNotificationCenter()->postNotification("FacebookLoginFailure");
}

void PWFacebook::setSchedulerForEvents(bool schedule)
{

}

void PWFacebook::logAccessToken()
{
	
}

void PWFacebook::trackPurchase(float, const char *)
{
	
}

void PWFacebook::handleFaceBookEvents()
{
	
}

std::string PWFacebook::getSessionExpiredMessage()
{
	return "";
}

PWFacebook* PWFacebook::sharedFacebook()
{
	return NULL;
}
