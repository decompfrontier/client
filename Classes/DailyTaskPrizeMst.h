#pragma once

#include "MstList.h"

class DailyTaskPrizeMst : public cocos2d::CCObject
{
public:
	DailyTaskPrizeMst() = default;
	virtual ~DailyTaskPrizeMst() = default;

	CC_SYNTHESIZE(int, m_bravePointCost, BravePointCost);
	CC_SYNTHESIZE(int, m_currentClaimCount, CurrentClaimCount);
	CC_PROPERTY(std::string, m_prizeTitle, TaskPrizeTitle);
	CC_SYNTHESIZE(int, m_presentType, PresentType);
	CC_SYNTHESIZE(int, m_targetCount, TargetCount);
	CC_SYNTHESIZE(int, m_targetID, TargetID);
	CC_SYNTHESIZE(int, m_prizeID, TaskPrizeId);
	CC_SYNTHESIZE(int, m_maxClaimCount, MaxClaimCount);
	CC_SYNTHESIZE(bool, m_isMileStonePrize, IsMileStonePrize);
	CC_PROPERTY(long, m_timeLimit, TimeLimit);
	CC_PROPERTY(std::string, m_prizeDesc, TaskPrizeDesc);
	CC_SYNTHESIZE(std::string, m_targetParam, TargetParam);

	long long getRemainingTime() const;
	const std::string& getTaskPrizeDesc() const;
	bool isMoreThan999Days() const { return m_timeLimit / 86400 > 999; } // 86400 = 1 day in seconds

protected:
	time_t m_startTimeLimit;
};

class DailyTaskPrizeMstList : public MstList<DailyTaskPrizeMst*>
{
	SHARED_SINGLETON(DailyTaskPrizeMstList);

public:
	DailyTaskPrizeMstList() = default;

	cocos2d::CCMutableArray<DailyTaskPrizeMst*> getLeastUnclaimedMileStonePrize();
	const std::string& getLocalNotificationText(int) const;
	long long getLocalNotificationTimer() const;
	void getMileStonePrizeList() const;
	void getRegularPrizeList() const;
	void getUnclaimedMileStonePrizeCount() const;
	bool isNextMileStoneNotificationAvailable(int) const;
};
