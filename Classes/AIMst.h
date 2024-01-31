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

	auto getAiID() const { return m_id; }
	auto getActTarget() const noexcept { return m_actTarget; }
	auto getActionID() const { return m_actionId; }
	auto getPercent() const noexcept { return m_percent; }
	auto getPriority() const noexcept { return m_priority; }
	auto getSearchTerm() const { return m_searchTerm; }
	cocos2d::CCMutableArray<AITerm*> getActTermList() const;
	cocos2d::CCMutableArray<AITerm*> getPartyActTermList() const;
	void parseAITerm(const std::string& data);
	void setActTarget(int at) noexcept { m_actTarget = at; }
	void setActTermParams(std::string p) { m_actTermParams = p; }
	void setActionID(std::string p) { m_actionId = p; }
	void setAiID(std::string p) { m_id = p; }
	void setPercent(float p) noexcept  { m_percent = p; }
	void setPriority(int p) noexcept { m_priority = p; }
	void setSearchTerm(std::string p) { m_searchTerm = p; }
private:
	std::string m_id, m_searchTerm, m_actionId, m_actTermParams, m_partyActTermList;
	float m_percent, m_actTarget;
	int m_priority;
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
