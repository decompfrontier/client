#pragma once

class DailyTaskMst : public cocos2d::CCObject
{
public:
	CC_SYNTHESIZE(int, m_bppTotal, BravePointsTotal);
	CC_SYNTHESIZE(std::string, m_imgName, ImageName);
};

