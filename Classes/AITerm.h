#pragma once

#include "CommonUtils.h"

class AITerm : public cocos2d::CCObject
{
public:
	CC_SYNTHESIZE(std::string, m_param, Param);
	CC_SYNTHESIZE(std::string, m_termID, TermID);
	CC_SYNTHESIZE(std::string, m_targetParam, TargetParam);
	CC_SYNTHESIZE(int, m_targetID, TargetID);

	int getIntParam() { CommonUtils::StrToInt(m_param); }
};
