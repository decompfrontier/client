#include "pch.h"
#include "UnitMstResponse.h"

bool UnitMstResponse::readParam(int, int, const char* key, const char* value, bool)
{
    if (!a3)
    {
        if (!a2)
        {
            v9 = (UnitMstList*)UnitMstList::shared((UnitMstList*)this);
            UnitMstList::removeAllObjects(v9);
        }

        m_unit = new UnitMst();
    }

    // TODO: all the rest...

    if (!strcmp(key, UNITMST_OVERDRIVEPARAM))
    {
        const auto& p = CommonUtils::parseList(value, ",");

        m_unit->setOverDriveUpAtk(CommonUtils::StrToInt(p[0]));
        m_unit->setOverDriveUpDef(CommonUtils::StrToInt(p[1]));
        m_unit->setOverDriveUpHeal(CommonUtils::StrToInt(p[2]));

        if (p.size() > 3)
            m_unit->setOverDriveIncCost(CommonUtils::StrToInt(p[3]));
    }
    else if (!strcmp(key, UNITMSG_VOICE) && FUNC_CHARACTER_VOICE)
    {
        const auto& p = CommonUtils::parseList(value, ",");

        m_unit->setAtkStartVoiceFile1(p[0]);
        m_unit->setAtkStartVoiceFile2(p[1]);
        m_unit->setAtkVoiceFile1(p[2]);
        m_unit->setAtkVoiceFile2(p[3]);
        m_unit->setDamageVoiceFile(p[4]);
        m_unit->setBbVoiceFile(p[5]);
        m_unit->setBbCutinVoiceFile(p[6]);
    }

	return true;
}
