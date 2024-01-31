#pragma once

#include "CommonUtils.h"

class AITerm : public cocos2d::CCObject
{
public:
	int getIntParam() const { CommonUtils::StrToInt(m_param); }
	const auto& getParam() const { return m_param; }
	auto getTargetID() const { return m_targetID; }
	const auto& getTargetParam() const { return m_targetParam; }
	const auto& getTermID() const { return m_termID; }
	void setParam(const std::string& param) { m_param = param; }
	void setTargetID(int id) { m_targetID = id; }
	void setTargetParam(const std::string& param) { m_targetParam = param; }
	void setTermID(const std::string& id) { m_termID = id; }

protected:
	std::string m_termID, m_param, m_targetParam;
	int m_targetID;
};
