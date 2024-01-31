#include "pch.h"
#include "UserInfo.h"
#include "SaveData.h"
#include "UserDebugInfo.h"
#include "Globals.h"
#include "ColosseumCommonInfo.h"
#include "UserArenaInfo.h"

SET_SHARED_SINGLETON(UserInfo);

std::string UserInfo::getServiceRequestEndpointParam() const
{
	return SaveData::shared()->getServiceRequestEndpoint();
}

void UserInfo::setServiceRequestEndpointParam(const std::string& v)
{
	SaveData::shared()->setServiceRequestEndpoint(v);
}

bool UserInfo::getSkipMainTutorialFlag() { return m_skipMainTutorialFlag; }

void UserInfo::setSkipMainTutorialFlag(bool v)
{
	m_skipMainTutorialFlag = v;
	const auto& ud = cocos2d::CCUserDefault::sharedUserDefault();
	ud->setBoolForKey(PKEY_TUTORIAL_SKIP, v);
	ud->flush();
}

bool UserInfo::isTutoPlaying() const
{
	if (UserDebugInfo::shared()->getTutoDebugFlg())
		return true;

	return m_tutoEndFlag;
}

bool UserInfo::isInAmazonControlGroup() const
{
	if (!FUNC_AMAZON_COINS_REWARD_CONTROL)
		return false;

	return !m_userId.empty();
}

bool UserInfo::isArenaTutoPlaying() const
{
	if (ColosseumCommonInfo::shared()->isColosseumBattle())
		return false;

	if (UserDebugInfo::shared()->isDebugMode())
	{
		if (UserDebugInfo::shared()->getArenaTutoDebugFlg())
			return true;
	}

	std::string x = UserArenaInfo::shared()->getArenaRankID();
	return !x.empty();
}

bool UserInfo::existUser()
{
	if (!UserDebugInfo::shared()->isDebugMode(0))
	{
		return m_userId.size();
	}

	return false;
}
