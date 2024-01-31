#include "pch.h"
#include "BaseRequest.h"
#include "Utils.h"
#include "UserInfo.h"
#include "Globals.h"
#include "FirstDescMst.h"
#include "NoticeListMst.h"

void BaseRequest::addGumiliveParams(JsonNode* root)
{
	if (Utils::magic_num1 != 0x5B88 && !Utils::mInfo.empty())
	{
		root->addParam(USERINFO_MINFO, Utils::mInfo);
	}

	const auto& userInfo = UserInfo::shared();

	root->addParam(USERINFO_GUMILIVEUSERID, userInfo->getGumiLiveUserId());
	root->addParam(USERINFO_GUMILIVETOKEN, userInfo->getGumiLiveToken());
	root->addParam(USERINFO_FACEBOOKID, userInfo->getFacebookId());

	if (Utils::hasReportedID())
	{
		root->addParam(USERINFO_REPORTEDUSERID, userInfo->getUserID());
		root->addParam(USERINFO_MINFO, Utils::getMInfo());
	}
}

void BaseRequest::createUserInfoTag()
{
	auto grp = addGroup(GROUP_USERINFO);
	auto grpNode = grp->addNode();

	if (Utils::point_num_name != 15)
	{
		if (!Utils::mInfo.empty())
			grpNode->addParam(USERINFO_MINFO, Utils::mInfo);
	}

	auto userInfo = UserInfo::shared();
	grpNode->addParam(USERINFO_USERID, userInfo->getUserID());
	grpNode->addParam(USERINFO_CONTACTID, userInfo->getContactID());
	addGumiliveParams(grpNode);

	if (!Utils::pointerName.empty())
		grpNode->addParam(USERINFO_POINTERNAME, Utils::pointerName);

	grpNode->addParam(USERINFO_MODELCHANGECOUNT, userInfo->getModelChangeCnt());
	grpNode->addParam(USERINFO_DEVICENAME, CommonUtils::getDeviceName());
	grpNode->addParam(USERINFO_TARGETOS, CommonUtils::IntToString(CommonUtils::getTargetOs()));

	if (FUNC_FIRST_DESC)
	{
		auto str = FirstDescMstList::shared()->getStringForRequest();
		if (!str.empty())
			grpNode->addParam(USERINFO_FIRSTDESCMST_REQUEST, str);
	}

	if (FUNC_NOTICE_LIST)
	{
		grpNode->addParam(USERINFO_NOTICELISTMST_REQUEST, NoticeListMstList::shared()->getStringForRequest());
	}

	grpNode->addParam(USERINFO_BUILDPLATFORMID, CommonUtils::IntToString(CommonUtils::getBuildPlatformID()));
}

JsonGroup* BaseRequest::replaceGroup(const char* group)
{
	for (unsigned int i = 0; i < m_groups.count(); i++)
	{
		auto obj = m_groups.getObjectAtIndex(i);

		if (obj->getGroupName() == group)
		{
			m_groups.removeObject(obj);
			break;
		}
	}

	auto p = new JsonGroup(group);
	m_groups.addObject(p);
	p->release();
	return p;
}
