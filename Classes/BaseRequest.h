#pragma once

#include "JsonGroup.h"

class BaseRequest : public cocos2d::CCObject
{
public:
	BaseRequest() = default;
	virtual ~BaseRequest() = default;

	JsonGroup* addGroup(const char* group)
	{
		auto p = new JsonGroup(group);
		m_groups.addObject(p);
		p->release();
		return p;
	}

	void addGumiliveParams(JsonNode* root);
	void createCampaignReceiptInfoTag();
	void createDungeonEventUserInfoTag();
	void createNewsGetInfoTag();
	void createSignalKeyTag();
	void createTutoUserInfoTag();
	void createUserInfoTag();
	void createVersionTag();
	virtual std::string getSendData();
	JsonGroup* replaceGroup(const char* group);

	virtual void create() {}
	virtual void debugLog(const char* log) {}

	virtual bool isAsync() const { return false; }
	virtual const char* getUrl() const { return ACTIONURL_BASE; } // /gme/action/

	virtual const char* getRequestID() const = 0;
	virtual const char* getEncodeKey() const = 0;
	virtual void createBody() = 0;

protected:
	cocos2d::CCMutableArray<JsonGroup*> m_groups;
};
