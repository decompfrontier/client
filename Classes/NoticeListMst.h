#pragma once

#include "MstList.h"

class NoticeListMst : public cocos2d::CCObject
{
public:
	NoticeListMst() = default;
	virtual ~NoticeListMst() = default;

	CC_SYNTHESIZE(int, m_contantsID, ContentsID);
	CC_SYNTHESIZE(int, m_dispOrder, DispOrder);
	CC_SYNTHESIZE(std::string, m_dispDate, DisplayDate);
	CC_SYNTHESIZE(bool, m_newFlag, NewFlag);
	CC_SYNTHESIZE(int, m_noticeId, NoticeID);
	CC_SYNTHESIZE(int, m_noticeType, NoticeType);
	CC_SYNTHESIZE(int, m_specialFlag, SpecialFlag);
	CC_SYNTHESIZE(std::string, m_templateName, TemplateName);
	CC_SYNTHESIZE(std::string, m_title, Title);
	CC_SYNTHESIZE(std::string, m_summary, Summary);
};

class NoticeListMstList
{
	SHARED_SINGLETON(NoticeListMstList);

public:
	std::string getStringForRequest();
	int getOffset();
	bool haveNew();
	bool haveOldest();
	bool needRequest();
};
