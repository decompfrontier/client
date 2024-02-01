#include "pch.h"
#include "UserTeamInfoResponse.h"
#include "UserTeamInfo.h"
#include "DefineMst.h"

bool UserTeamInfoResponse::readParam(int, int, const char* key, const char* value, bool isFirst)
{
	auto info = info->shared();

    if (!strcmp(key, USERINFO_USERID))
        info->setUserID(value);
    else if (!strcmp(key, USERTEAMINFO_LV))
        info->setLv(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_EXP))
        info->setExp(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_MAXACTIONPOINT))
        info->setMaxActionPoint(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_ACTIONPOINT))
        info->setActionPoint(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_MAXFIGHTPOINT))
        info->setMaxFightPoint(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_FIGHTPOINT))
        info->setFightPoint(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_MAXUNITCOUNT))
        info->setMaxUnitCnt(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_ADDUNITCOUNT))
        info->setAddUnitCnt(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_DECKCOST))
        info->setDeckCost(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_MAXEQUIPSLOTCOUNT))
        info->setMaxEqpSlotCnt(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_MAXFRIENDCOUNT))
        info->setMaxFrdCnt(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_ADDFRIENDCOUNT))
        info->setAddFrdCnt(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_FRIENDPOINT))
        info->setFriendPoint(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_ZEL))
    {
        auto p = CommonUtils::StrToUInt(value);

        if (p > DefineMst::shared()->getMaxZel())
            p = DefineMst::shared()->getMaxZel();

        info->setZel(p);
    }
    else if (!strcmp(key, USERTEAMINFO_KARMA))
    {
        auto p = CommonUtils::StrToUInt(value);

        if (p > DefineMst::shared()->getMaxKarma())
            p = DefineMst::shared()->getMaxKarma();

        info->setKarma(p);
    }
    else if (!strcmp(key, USERTEAMINFO_BRAVECOIN))
        info->setBraveCoin(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_FRIENDMESSAGE))
    {
        info->setFriendMessage(value);
        info->setFriendMessageOrg(value);
    }
    else if (!strcmp(key, USERTEAMINFO_WAREHOUSECOUNT))
        info->setWarehouseCnt(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_ADDWHAREHOUSECOUNT))
        info->setAddWarehouseCnt(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_SETWANTGIFT))
        info->setWantGift(value);
    else if (!strcmp(key, USERTEAMINFO_PRESENTCOUNT))
        info->setPresentCnt(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_FRIENDAGREECOUNT))
        info->setFriendAgreeCnt(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_GIFTRECEIVECOUNT))
        info->setGiftRecieveCnt(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_ACTIONRESTTIMER))
        info->setActionRestTimer(CommonUtils::StrToInt(value));
    else if (!strcmp(key, USERTEAMINFO_FIGHTRESTTIMER))
    {
        info->setFightRestTimer();
    }
    else if (!strcmp(key, USERTEAMINFO_SUMMONTICKET))
    {
        auto v = CommonUtils::StrToInt(value);
        info->setSummonTicket(v);
        cocos2d::CCLog("SUMMON TICKET: %d", v);
    }
    else if (!strcmp(key, USERTEAMINFO_ACTIVEDECK))
    {
        info->setActiveDeckNum(CommonUtils::StrToInt(value));
        info->setServerActiveDeckNum(CommonUtils::StrToInt(value));
    }
    else if (!strcmp(key, USERTEAMINFO_COLOSSEUMTICKET))
    {
        info->setColosseumTicket(CommonUtils::StrToInt(value));
    }
    else if (!strcmp(key, USERTEAMINFO_ARENADECKNUM))
    {
        info->setArenaDeckNum(CommonUtils::StrToInt(value));
    }
    else if (!strcmp(key, USERTEAMINFO_REINFORCEMENTDECKS))
    {
        auto v = CommonUtils::parseList(value, ",");
        info->setReinforcementDeckNum(CommonUtils::StrToInt(v[0]));

        if (v.size() > 2)
        {
            info->setReinforcementDeckEx1Num(CommonUtils::StrToInt(v[1]));
            info->setReinforcementDeckEx2Num(CommonUtils::StrToInt(v[2]));
        }
    }
    else if (!strcmp(key, USERTEAMINFO_SLOTGAMEFLAG))
    {
        info->setSlotgameFlg(CommonUtils::StrToInt(value) != 0);
    }
    else if (!strcmp(key, USERTEAMINFO_BRAVEPOINTSTOTAL))
    {
        info->setBravePointsTotal(CommonUtils::StrToInt(value));
    }
    else if (!strcmp(key, "22rqpZTo"))
    {
        info->setBravePointsCurrent(CommonUtils::StrToInt(value));
    }
    else if (!strcmp(key, USERTEAMINFO_RAINBOWCOIN))
    {
        info->setRainbowCoin(CommonUtils::StrToInt(value));
    }
    else if (!strcmp(key, "3a8b9D8i"))
    {
        info->setCompletedTaskCount(CommonUtils::StrToInt(value));
    }
    else if (!strcmp(key, "7qncTHUJ"))
    {
        info->setInboxMessagesCnt(CommonUtils::StrToInt(value));
    }
    else if (!strcmp(key, "Qo9doUsp"))
    {
        info->setMysteryBoxCount(CommonUtils::StrToInt(value));
    }
    else if (!strcmp(key, "d37CaiX1"))
    {
        info->setPaidGems(CommonUtils::StrToInt(value));
    }
    else if (!strcmp(key, USERTEAMINFO_FREEGEMS))
    {
        info->setFreeGems(CommonUtils::StrToInt(value));
    }

    return true;
}
