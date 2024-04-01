#include "pch.h"
#include "DailyTaskBonusMstResponse.h"

void DailyTaskBonusMstResponse::readParam(int, int, const char* key, const char* value, bool)
{
    if (!a3)
    {
        if (!a2)
        {
            v10 = (DailyTaskBonusMstList*)DailyTaskBonusMstList::shared((DailyTaskBonusMstList*)this);
            DailyTaskBonusMstList::removeAllObjects(v10);
        }
        v11 = (DailyTaskBonusMst*)operator new(0x20uLL);
        DailyTaskBonusMst::DailyTaskBonusMst(v11);
        this->mst = v11;
    }

    if (!strcmp(key, DAILYTASKMSTBONUS_BONUSBRAVEPOINTS))
    {
        m_mst->setBonusBravePoints(CommonUtils::StrToInt(value));

        if (!a6)
            return;
    }

    if (a6)
    {
        DailyTaskBonusMstList::shared()->addObject(m_mst);
    }
}
