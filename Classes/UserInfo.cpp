#include "pch.h"
#include "UserInfo.h"
#include "SaveData.h"
#include "UserDebugInfo.h"
#include "Globals.h"
#include "ColosseumCommonInfo.h"
#include "UserArenaInfo.h"
#include "GumiLiveManager.h"
#include "FirstDescMst.h"

SET_SHARED_SINGLETON(UserInfo);

UserInfo::UserInfo()
	: m_admobPurchaseResult(0)
	, m_arenaTutoChapterId(1)
	, m_devTransfer(false)
	, m_friendInvFlag(0)
	, m_lastTutoChapterID(0)
	, m_playTutoChapterID(0)
	, m_modelChangeCount(0)
	, m_purchaseFixFlag(0)
	, m_purchaseModelStatus(0)
	, m_purchaseResetFlag(0)
	, m_triggerTutorialStarted(0)
	, m_tutoChapterID(1)
	, m_tutoEndFlag(0)
	, m_tutoEndMsgFlag(0)
	, m_skipMainTutorialFlag(false)
	, m_triggerTutorialEnabled(false)
{
	m_skipMainTutorialFlag = cocos2d::CCUserDefault::sharedUserDefault()->getBoolForKey(PKEY_TUTORIAL_SKIP);
}

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

const std::string& UserInfo::getGumiLiveToken() const
{
	return GumiLiveManager::shared()->getGumiLiveToken();
}

const std::string& UserInfo::getGumiLiveUserId() const
{
	return GumiLiveManager::shared()->getGumiLiveID();
}

const std::string& UserInfo::getLobiHandleName() const
{
	std::string ret = m_handleName;
	ret += "(";
	// DECOMP NOTE: No reason to go this route...
	//if (!SINGLETON_INSTANCE(UserInfo))
	//	SINGLETON_NEW(UserInfo);
	//auto liveMngr = SINGLETON_INSTANCE(UserInfo);
	ret += m_friendID;
	ret += ")";
	return ret;
}

const std::string& UserInfo::getSignInWithAppleId() const
{
	return GumiLiveManager::shared()->getAppleID();
}

void UserInfo::setFirstDesc(const std::string& v)
{
	m_vFirstDesc = CommonUtils::parseList(v, ",");
}

void UserInfo::setScriptDesc(const std::string& v)
{
	m_vScriptDesc = CommonUtils::parseList(v, ",");
}

const std::string& UserInfo::getFacebookId() const
{
	return GumiLiveManager::shared()->getFacebookID();
}

void UserInfo::clearFirstDescs()
{
	m_vFirstDesc.clear();
	FirstDescMstList::shared()->save();
}

bool UserInfo::isScriptDesc(const std::string& v) const
{
	return std::find(m_vScriptDesc.begin(), m_vScriptDesc.end(), v) != m_vScriptDesc.end();
}

void UserInfo::clearFirstDescID(const std::string& v)
{
	// NOTE: I hope I got it right...

	auto it = std::find(m_vScriptDesc.begin(), m_vScriptDesc.end(), v);

	if (it != m_vScriptDesc.end())
		m_vScriptDesc.erase(it);
}

void UserInfo::clearScriptDesc(const std::string& v)
{
	// NOTE: I hope I got it right...

	auto it = std::find(m_vFirstDesc.begin(), m_vFirstDesc.end(), v);

	if (it != m_vFirstDesc.end())
		m_vFirstDesc.erase(it);

	FirstDescMstList::shared()->save();
}

bool UserInfo::isFirstDesc(const std::string& v)
{
	// DECOMP NOTE: I hope I got it right...
	if (m_vFirstDesc.size() > 0)
	{
		const auto it = std::find(m_vFirstDesc.begin(), m_vFirstDesc.end(), v);

		if (it == m_vFirstDesc.end())
		{
			m_vFirstDesc.push_back(v);

			if (FUNC_FIRST_DESC)
				FirstDescMstList::shared()->save();
		}

		return true;
	}

	const auto it = std::find(m_vScriptDesc.begin(), m_vScriptDesc.end(), v);
	if (it != m_vScriptDesc.end())
	{
		clearScriptDesc(v);
		return true;
	}

	return false;
}
