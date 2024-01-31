#pragma once

#include "MstList.h"

class FirstDescMst : public cocos2d::CCObject
{
public:
	FirstDescMst();

	CC_SYNTHESIZE(std::string, m_descID, DescID);
	CC_SYNTHESIZE(std::string, m_descTitle, DescTitle);
	CC_SYNTHESIZE(std::string, m_descType, DescType);
	CC_SYNTHESIZE(std::string, m_img, Img);
	CC_SYNTHESIZE(std::string, m_param, Param);
	CC_SYNTHESIZE(std::string, m_scriptName, ScriptName);

	void printLog() {}
};

class FirstDescMstList
{
	SHARED_SINGLETON(FirstDescMstList);

public:
	FirstDescMstList();
	FirstDescMst* getObjectWithDescType(std::string);
	FirstDescMst* getObjectWithDungeonID(std::string, std::string);
	void getObjectWithScripEvent();
	static const std::string& getStringForRequest();
	void save();
};
