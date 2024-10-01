#pragma once

#include "MstList.h"

class DailyTaskBonusMst : public cocos2d::CCObject
{
public:
	CC_SYNTHESIZE(int, m_bonusBravePoints, BonusBravePoints);
};

class DailyTaskBonusMstList : public MstList<DailyTaskBonusMst>
{
	SHARED_SINGLETON(DailyTaskBonusMstList);
};
