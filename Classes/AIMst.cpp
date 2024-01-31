#include "pch.h"
#include "AIMst.h"
#include "AITerm.h"

cocos2d::CCMutableArray<AITerm*> AIMst::getPartyActTermList() const
{
	const auto v = CommonUtils::parseList(m_partyActTermList, "@");
	cocos2d::CCMutableArray<AITerm*> res;

	for (const auto& it : v)
	{
		auto term = new AITerm();
		const auto termData = CommonUtils::parseList(it, ":");

		term->setTargetID(CommonUtils::StrToInt(termData[0]));
		term->setTargetParam(termData[1]);
		term->setTermID(termData[2]);
		term->setParam(termData[3]);

		res.addObject(term);
		term->release();
	}

	return res;
}

cocos2d::CCMutableArray<AITerm*> AIMst::getActTermList() const
{
	const auto v = CommonUtils::parseList(m_actTermParams, "@");
	cocos2d::CCMutableArray<AITerm*> res;

	for (const auto& it : v)
	{
		auto term = new AITerm();
		const auto& termData = CommonUtils::parseList(it, ":");

		term->setTermID(termData[0]);
		term->setParam(termData[1]);

		res.addObject(term);
		term->release();
	}
}

void AIMst::parseAITerm(const std::string& data)
{
	const auto& v = CommonUtils::parseList(data, "#");

	if (v.size() == 1)
	{
		m_actTermParams = v[0];
	}
	else
	{
		m_partyActTermList = v[0];
		m_actTermParams = v[1];
	}
}
