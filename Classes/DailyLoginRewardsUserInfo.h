#pragma once

class DailyLoginRewardsUserInfo
{
	SHARED_SINGLETON(DailyLoginRewardsUserInfo);

	CC_SYNTHESIZE(int, m_currentDay, CurrentDay);
	CC_SYNTHESIZE(int, m_day, Day);
	CC_SYNTHESIZE(std::string, m_id, Id);
	CC_SYNTHESIZE(std::string, m_message, Message);
	CC_SYNTHESIZE(int, m_nextRewardId, NextRewardId);
	CC_SYNTHESIZE(int, m_userCurrCount, UserCurrentCount);
	CC_SYNTHESIZE(int, m_userLimitCount, UserLimitCount);
	CC_SYNTHESIZE(bool, m_dailyLogiAvail, IsDailyLoginAvailable); // __DECOMP__, not the same but it's ok
};
