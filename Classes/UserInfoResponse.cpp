#include "pch.h"
#include "JsonNetworkNames.h"
#include "UserInfoResponse.h"
#include "UserInfo.h"
#include "UserScenarioInfoList.h"
#include "UserSpecialScenarioInfoList.h"
#include "EarlyBird.h"
#include "UserDebugInfo.h"
#include "UserUnitInfoList.h"
#include "SaveData.h"
#include "Globals.h"
#include "FeatureGatingHandler.h"

bool UserInfoResponse::readParam(int, int, const char* key, const char* value, bool isFirst)
{
	const auto& info = UserInfo::shared();

	if (isFirst)
	{
		UserUnitInfoList::shared()->updateSummonerMst();
		UserUnitInfoList::sharedOriginal()->updateSummonerMst();

		if (SaveData::shared()->getHandleName() != info->getHandleName())
		{
			SaveData::shared()->setHandleName(info->getHandleName());
			SaveData::shared()->saveKeyChain();
		}
	}

	if (strcmp(key, USERINFO_USERID) == 0)
	{
		info->setUserID(value);
	}
	else if (strcmp(key, USERINFO_HANDLENAME) == 0)
	{
		info->setHandleName(value);
	}
	else if (strcmp(key, USERINFO_ACCOUNTID) == 0)
	{
		info->setAccountID(value);
	}
	else if (strcmp(key, USERINFO_PASSWORD) == 0)
	{
		info->setPassword(value);
	}
	else if (strcmp(key, USERINFO_FRIENDID) == 0)
	{
		info->setFriendID(value);
	}
	else if (strcmp(key, USERINFO_CONTACTID) == 0)
	{
		info->setContactID(value);
	}
	else if (strcmp(key, USERINFO_TUTOCHAPTERID) == 0)
	{
		auto tutoId = CommonUtils::StrToInt(value);
		info->setTutoChapterID(tutoId);

		if ((tutoId - 50) < 4 || tutoId == 12) // TODO: what's this
			info->setTriggerableTutorial(true);
	}
	else if (strcmp(key, USERINFO_TUTOENDFLAG) == 0 && info->getTriggerableTutorialStarted())
	{
		info->setTutoEndFlg(CommonUtils::StrToInt(value));
	}
	else if (strcmp(key, USERINFO_SCENARIOS) == 0)
	{
		UserScenarioInfoList::shared()->parse(value);
	}
	else if (strcmp(key, USERINFO_SPECIALSCENARIOS) == 0)
	{
		UserSpecialScenarioInfoList::shared()->parse(value);
	}
	else if (strcmp(key, USERINFO_MODELCHANGECOUNT) == 0)
	{
		info->setModelChangeCnt(CommonUtils::StrToInt(value));
	}
	else if (strcmp(key, USERINFO_CODEEXPIREDATE) == 0)
	{
		info->setCodeExpireDate(value);
	}
	else if (strcmp(key, USERINFO_FRIENDINVITATIONFLAG) == 0)
	{
		info->setFriendInvitationFlg(CommonUtils::StrToInt(value));
	}
	else if (strcmp(key, USERINFO_EARLYBIRD_END) == 0)
	{
		cocos2d::CCLog("P_EARLYBIRD_END=%s", value);
		auto t = CommonUtils::StrToLongLong(value);
		timeval tv;
		gettimeofday(&tv, nullptr);
		EarlyBird::m_sStartingFrom = tv.tv_sec;
		EarlyBird::m_sTimeLeft = t;
	}
	else if (strcmp(key, USERINFO_DEBUGMODE) == 0)
	{
		UserDebugInfo::shared()->setDebugMode(CommonUtils::StrToInt(value) != 0);
	}
	else if (strcmp(key, USERINFO_ENCRYPTIV) == 0)
	{
		info->setEncryptIv(value);
	}
	else if (strcmp(key, USERINFO_ENCRYPTEDFRIENDID) == 0)
	{
		info->setEncryptedFriendID(value);
	}
	else if (strcmp(key, USERINFO_FIRSTDESC) == 0)
	{
		info->setFirstDesc(value);
	}
	else if (strcmp(key, USERINFO_DLCURL) == 0)
	{
		SET_DLC_URL(value);
	}
	else if (strcmp(key, USERINFO_FEATUREGATE) == 0)
	{
		auto i = CommonUtils::StrToInt(value);

		if (i != 1)
			FeatureGatingHandler::shared()->setFeatureDate(i);
	}
	else if (strcmp(key, "32k0ahkD") == 0)
	{
		// TODO: Discover what's this
		info->set ? ? ? ? (value);
	}
	else if (strcmp(key, USERINFO_SERVICEREQUESTENDPOINTPARAM) == 0)
	{
		info->setServiceRequestEndpointParam(value);
	}

	return 1;
}
