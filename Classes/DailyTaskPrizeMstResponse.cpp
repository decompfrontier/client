#include "pch.h"
#include "DailyTaskPrizeMstResponse.h"
#include "DailyTaskPrizeMst.h"

bool DailyTaskPrizeMstResponse::readParam(int, int, const char* key, const char* value, bool isFirst)
{
	if (!a3)
	{
		if (!a2)
		{
			DailyTaskPrizeMstList::shared()->removeAllObjects();
		}

		m_mst = new DailyTaskPrizeMst();
	}

	if (!strcmp(key, DAILYTASKMST_TASKID))
	{
		m_mst->setTaskPrizeId(CommonUtils::StrToInt(value));
	}
	else if (!strcmp(key, DAILYTASKMST_TASKTITLE))
	{
		m_mst->setTaskPrizeTitle(value);
	}
	else if (!strcmp(key, DAILYTASKMST_TASKDESC))
	{
		m_mst->setTaskPrizeDesc(value);
	}
	else if (!strcmp(key, DAILYTASKMST_PRESENTTYPE))
	{
		m_mst->setPresentType(CommonUtils::StrToInt(value));
	}
	else if (!strcmp(key, DAILYTASKMST_TARGETID))
	{
		m_mst->setTargetID(CommonUtils::StrToInt(value));
	}
	else if (!strcmp(key, DAILYTASKMST_TARGETCOUNT))
	{
		m_mst->setTargetCount(CommonUtils::StrToInt(value));
	}
	else if (!strcmp(key, DAILYTASKMST_TARGETPARAM))
	{
		m_mst->setTargetParam(value);
	}
	else if (!strcmp(key, DAILYTASKMST_BRAVEPOINTCOST))
	{
		m_mst->setBravePointCost(CommonUtils::StrToInt(value));
	}
	else if (!strcmp(key, DAILYTASKMST_TIMELIMIT))
	{
		std::string q = value;
		if (!q.empty())
			m_mst->setTimeLimit(CommonUtils::StrToLong(q));
	}
	else if (!strcmp(key, DAILYTASKMST_MAXCLAIMCOUNT))
	{
		m_mst->setMaxClaimCount(CommonUtils::StrToInt(value));
	}
	else if (!strcmp(key, DAILYTASKMST_CURRENTCLAIMCOUNT))
	{
		m_mst->setCurrentClaimCount(CommonUtils::StrToInt(value));
	}
	else if (!strcmp(key, DAILYTASKMST_ISMILESTONEPRIZE))
	{
		m_mst->setIsMileStonePrize(CommonUtils::StrToInt(value) > 0);
	}

	if (isFirst)
	{
		DailyTaskPrizeMstList::shared()->addObject(m_mst);
	}
}
