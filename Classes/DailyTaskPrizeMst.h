#pragma once

#include "MstList.h"

class DailyTaskPrizeMst : public cocos2d::CCObject
{
public:
	int getBravePointCost() const { return m_bravePointCost; }
	int getCurrentClaimCount() const { return m_currentClaimCount; }
	bool getIsMileStonePrize() const { return m_isMileStonePrize; }
	int getMaxClaimCount() const { return m_maxClaimCount; }
	int getPresentType() const { return m_presentType; }
	long long getRemainingTime() const;
	int getTargetCount() const { return m_targetCount; }
	int getTargetID() const { return m_targetID; }
	const std::string& getTargetParam() const { return m_targetParam; }
	const std::string& getTaskPrizeDesc() const;
	int getTaskPrizeId() const { return m_prizeId; }
	const std::string& getTaskPrizeTitle() const;
	int getTimeLimit() const { return m_timeLimit; }
	bool isMoreThan999Days() const { return m_timeLimit / 86400 > 999; } // 86400 = 1 day in seconds
	void setBravePointCost(int v) { m_bravePointCost = v; }
	void setCurrentClaimCount(int v) { m_currentClaimCount = v; }
	void setIsMileStonePrize(bool v) { m_isMileStonePrize = v; }
	void setMaxClaimCount(int v) { m_maxClaimCount = v; }
	void setPresentType(int v) { m_presentType = v; }
	void setTargetCount(int v) { m_targetCount = v; }
	void setTargetID(int t) { m_targetID = t; }
	void setTargetParam(const std::string& t) { m_targetParam = t; }
	void setTaskPrizeDesc(const std::string& t) { m_prizeDesc = t; }
	void setTaskPrizeId(int t) { m_prizeId = t; }
	void setTaskPrizeTitle(const std::string& t) { m_prizeTitle = t; }
	void setTimeLimit(long t) { m_timeLimit = t; m_startTimeLimit = CommonUtils::getNowUnitxTime(); }

protected:
	int m_prizeId, m_targetID, m_targetCount, m_presentType;
	int m_maxClaimCount, m_currentClaimCount, m_bravePointCost;
	std::string m_prizeTitle, m_prizeDesc, m_targetParam;
	long m_timeLimit;
	time_t m_startTimeLimit;
	bool m_isMileStonePrize;
};

class DailyTaskPrizeMstList : public MstList<DailyTaskPrizeMst*>
{
	SHARED_SINGLETON(DailyTaskPrizeMstList);

public:
	cocos2d::CCMutableArray<DailyTaskPrizeMst*> getLeastUnclaimedMileStonePrize();
	const std::string& getLocalNotificationText(int) const;
	long long getLocalNotificationTimer() const;
	void getMileStonePrizeList() const;
	void getRegularPrizeList() const;
	void getUnclaimedMileStonePrizeCount() const;
	bool isNextMileStoneNotificationAvailable(int) const;
};
