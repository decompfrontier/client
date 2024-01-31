#pragma once

#include <cocoa/CCObject.h>
#include <string>

#include "MstList.h"
#include "AITerm.h"

class AIMst : public cocos2d::CCObject
{
public:
	AIMst() = default;
	virtual ~AIMst() = default;

	CC_SYNTHESIZE(std::string, m_id, AiID);
	CC_SYNTHESIZE(float, m_actTarget, ActTarget);
	CC_SYNTHESIZE(std::string, m_actionId, ActionID);
	CC_SYNTHESIZE(float, m_percent, Percent);
	CC_SYNTHESIZE(int, m_priority, Priority);
	CC_SYNTHESIZE(std::string, m_searchTerm, SearchTerm);
	CC_SYNTHESIZE(std::string, m_actTermParams, ActTermParams);

	cocos2d::CCMutableArray<AITerm*> getPartyActTermList() const;
	cocos2d::CCMutableArray<AITerm*> getActTermList() const;
	void parseAITerm(const std::string& data);

protected:
	std::string m_partyActTermList;
};

class AIMstList : public MstList<AIMst*>
{
	SHARED_SINGLETON(AIMstList);

public:
	AIMst* getObject(std::string aiId, int priority)
	{
		for (int i = 0; i < m_array.count(); i++)
		{
			auto aim = m_array.getObjectAtIndex(i);

			if (aim->getAiID() == aiId && aim->getPriority() == priority)
				return aim;
		}

		return nullptr;

	}

	cocos2d::CCMutableArray<AIMst*> getObjectList(std::string id)
	{
		cocos2d::CCMutableArray<AIMst*> rt;

		auto it = m_array.begin();
		for (; it != m_array.end(); it++)
		{
			if ((*it)->getAiID() == id)
				rt.addObject(*it);
		}
	}
};
