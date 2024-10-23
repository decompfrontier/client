#include "pch.h"
#include "DailyLoginRewardsUserInfoResponse.h"
#include "DailyLoginRewardsUserInfo.h"

bool DailyLoginRewardsUserInfoResponse::readParam(int, int, const char* key, const char* value, bool isFirst)
{
	if (!strcmp(key, DAILYLOGINREWARDUSERINFO_ID))
	{
		DailyLoginRewardsUserInfo::shared()->setId(value);
	}
	else if (!strcmp(key, DAILYLOGINREWARDUSERINFO_USERCURRENTCOUNT))
	{
		DailyLoginRewardsUserInfo::shared()->setUserCurrentCount(CommonUtils::StrToInt(value));
	}
	else if (!strcmp(key, DAILYLOGINREWARDUSERINFO_USERLIMITCOUNT))
	{
		DailyLoginRewardsUserInfo::shared()->setUserLimitCount(CommonUtils::StrToInt(value));
	}
	else if (!strcmp(key, DAILYLOGINREWARDUSERINFO_CURRENTDAY))
	{
		DailyLoginRewardsUserInfo::shared()->setCurrentDay(CommonUtils::StrToInt(value));
	}
	else if (!strcmp(key, DAILYLOGINREWARDUSERINFO_DAY))
	{
		DailyLoginRewardsUserInfo::shared()->setDay(CommonUtils::StrToInt(value));
	}
	else if (!strcmp(key, DAILYLOGINREWARDUSERINFO_MESSAGE))
	{
		DailyLoginRewardsUserInfo::shared()->setMessage(value);
	}
	else if (!strcmp(key, DAILYLOGINREWARDUSERINFO_NEXTREWARDID))
	{
		DailyLoginRewardsUserInfo::shared()->setNextRewardId(CommonUtils::StrToInt(value));
	}
}
