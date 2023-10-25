#pragma once

#include <cocoa/CCObject.h>
#include <string>

class AIMst : public cocos2d::CCObject
{
public:
	AIMst() = default;
	virtual ~AIMst() = default;

	auto getAiID() const { return id; }
	auto getActTarget() const noexcept { return actTarget; }
	auto getActionID() const { return actionId; }
	auto getPercent() const noexcept { return percent; }
	auto getPriority() const noexcept { return priority; }
	auto getSearchTerm() const { return searchTerm; }
	auto getPartyActTermList() const;
	void parseAITerm(std::string);
	void setActTarget(int at) noexcept { actTarget = at; }
	void setActTermParams(std::string p) { actTermParams = p; }
	void setActionID(std::string p) { actionId = p; }
	void setAiID(std::string p) { id = p; }
	void setPercent(float p) noexcept  { percent = p; }
	void setPriority(int p) noexcept { priority = p; }
	void setSearchTerm(std::string p) { searchTerm = p; }
private:
	std::string id, searchTerm, actionId, actTermParams;
	float percent, actTarget;
	int priority;
};
