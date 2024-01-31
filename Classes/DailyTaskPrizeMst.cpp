#include "pch.h"
#include "DailyTaskPrizeMst.h"
#include "TextManager.h"
#include "UserTeamInfo.h"

long long DailyTaskPrizeMst::getRemainingTime() const
{
	auto diff = (m_timeLimit + m_startTimeLimit) - CommonUtils::getNowUnitxTime();
	if (diff < 0)
		return 0;

	return diff;
}

const std::string& DailyTaskPrizeMst::getTaskPrizeDesc() const
{
	return TextManager::shared()->getText(m_prizeDesc.c_str());
}

const std::string& DailyTaskPrizeMst::getTaskPrizeTitle() const
{
	return TextManager::shared()->getText(m_prizeTitle.c_str());
}

cocos2d::CCMutableArray<DailyTaskPrizeMst*> DailyTaskPrizeMstList::getLeastUnclaimedMileStonePrize()
{
	cocos2d::CCMutableArray<DailyTaskPrizeMst*> ret;

	for (int i = 0; i < m_array.count(); i++)
	{
		auto mst = m_array.getObjectAtIndex(i);

		if (mst && mst->getIsMileStonePrize() &&
			mst->getCurrentClaimCount() < mst->getMaxClaimCount() &&
			UserTeamInfo::shared()->getBravePointsTotal() >= mst->getBravePointCost())
		{
			ret.addObject(mst);
		}
	}

	// TODO: is there any sorting?
	return ret;
}


long long DailyTaskPrizeMstList::getLocalNotificationTimer() const
{
	return LOCAL_NOTIFICATION_TIMER_FOR_DAILY_TASKS;
}
