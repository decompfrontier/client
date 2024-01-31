#pragma once

#include "JsonGroup.h"

class BaseRequest : public cocos2d::CCObject
{
public:
	BaseRequest() = default;
	virtual ~BaseRequest() = default;

	void addGroup(const char* group)
	{
		auto p = new JsonGroup(group);
		m_groups.addObject(p);
	}

	void addGumiliveParams(JsonNode* node);
	virtual void create() {}
	void createCampaignReceiptInfoTag();
	void createDungeonEventUserInfoTag();
	void createNewsGetInfoTag();
	void createSignalKeyTag();
	void createTutoUserInfoTag();
	void createUserInfoTag();
	void createVersionTag();
	virtual void debugLog(const char* log) {}
	virtual std::string getSendData() = 0;
	virtual std::string getUrl() const { return "/actionSymbol/action.php"; } // /gme/action/
	virtual bool isAsync() const { return false; }
	void replaceGroup(const char* group);

protected:
	cocos2d::CCMutableArray<JsonGroup*> m_groups;
};
