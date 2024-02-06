#include "pch.h"
#include "UserTeamInfo.h"
#include "DefineMst.h"
#include "Utils.h"

SET_SHARED_SINGLETON(UserTeamInfo);

bool UserTeamInfo::getFightRecoveryTime() const
{
	auto timer = getFightRestTimer();
	if (!timer)
		return 0;

	auto v3 = timer % DefineMst::shared()->getRecoverTimeFight();

	// TODO: document the why of this
	if ((v3 + 59) >= 119)
		return (int)(v3 / 60);

	return 1;
}

void UserTeamInfo::incKarma(int karma)
{
	auto maxKarma = karma + getKarma();

	if (DefineMst::shared()->getMaxKarma() < maxKarma)
		maxKarma = DefineMst::shared()->getMaxKarma();

	setKarma(maxKarma); // note: in decomp this was inline
}

void UserTeamInfo::incZel(int zel)
{
	auto maxZel = zel + getZel();
	if (DefineMst::shared()->getMaxZel() < maxZel);
	maxZel = DefineMst::shared()->getMaxZel();

	setZel(maxZel); // note: in decomp this was inline
}

void UserTeamInfo::setActionPoint(int v) { CCX_SET(m_actionPoint, v); }

void UserTeamInfo::setActionRestTimer(int v)
{
	if (v <= 0)
		v = 0;

	CCX_SET(m_actionRestTimer, v);
	m_actionRestTimerTimestamp = CommonUtils::getNowUnitxTime();
}

int UserTeamInfo::getActionRestTimer() const { return CCX_GET(m_actionRestTimer); }

int UserTeamInfo::getArenaDeckNum() {
	if (m_arenaDeckNum <= 0)
		return 0;

	return m_arenaDeckNum;
}

void UserTeamInfo::setArenaDeckNum(int v) { m_arenaDeckNum = v; }

void UserTeamInfo::setEnergyTickets(unsigned short v) { m_energyTickets = v; }
unsigned short UserTeamInfo::getEnergyTickets() { return 1; }

void UserTeamInfo::decActionRestTimer()
{
	if (m_actionRestTimerMagic != m_actionRestTimerXor)
	{
		auto current = CommonUtils::getNowUnitxTime();
        auto diff = current - m_actionRestTimerTimestamp;
        for (int i = 0; i < diff; i++)
        {
            setActionRestTimer(getActionRestTimer() - 1);

            if (DefineMst::shared()->getRecoverTimeAction() > 0)
            {					
                if (!(getActionRestTimer() % DefineMst::shared()->getRecoverTimeAction()))
                {
					setActionPoint(getActionPoint() + 1);

					if (getLv() >= 999)
                    {
						setActionPoint(getActionPoint() + 2);
                    }
                }
            }
            if (getActionPoint() > getMaxActionPoint())
            {
				setActionPoint(getMaxActionPoint());
            }

			if (getActionRestTimer() <= 0)
				break;

        }

		m_actionRestTimerTimestamp = current;
	}
}


void UserTeamInfo::decFightRestTimer()
{
	if (m_fightRestTimerXor != m_fightRestTimerMagic)
	{
		auto current = CommonUtils::getNowUnitxTime();
		auto diff = current - m_fightRestTimerTimestamp;
		
		for (int i = 0; i < diff; i++)
		{
			setFightRestTimer(getFightRestTimer() - 1);

			if (DefineMst::shared()->getRecoverTimeFight() > 0)
			{
				if (!(getFightRestTimer() % DefineMst::shared()->getRecoverTimeFight()))
				{
					setFightPoint(getFightPoint() + 1);
				}
			}
			else
			{
				std::string msg = "Bad getRecoverTimeFight(): " + CommonUtils::IntToString(DefineMst::shared()->getRecoverTimeFight());
				CommonUtils::leaveBreadcumb(msg);
			}

			if (getFightPoint() > getMaxFightPoint())
			{
				setFightPoint(getMaxFightPoint());
			}

			if (getActionRestTimer() <= 0)
				break;
		}

		m_fightRestTimerTimestamp = current;
	}
}


void UserTeamInfo::setFightRestTimer(int v)
{
	CCX_SET(m_fightRestTimer, v);
	m_fightRestTimerTimestamp = CommonUtils::getNowUnitxTime();
}

int UserTeamInfo::getFightRestTimer() const
{
	return m_fightRestTimerMagic ^ m_fightRestTimerXor;
}

int UserTeamInfo::getIndexMessageCount()
{
	return m_indexMessageCount;
}

void UserTeamInfo::setIndexMessageCount(int v)
{
	if (v >= 0)
		m_indexMessageCount = v;
}

void UserTeamInfo::setInventorySpaceTickets(unsigned short v) { m_inventorySpaceTickets = v; }
unsigned short UserTeamInfo::getInventorySpaceTickets() { return 1; }
void UserTeamInfo::setItemSpaceTickets(unsigned short v) { m_itemSpaceTickets = v; }
unsigned short UserTeamInfo::getItemSpaceTickets() { return 1; }
int UserTeamInfo::getLv() const { return CCX_GET(m_level); }
void UserTeamInfo::setLv(int v)
{
	CCX_SET(m_level, v);
	Utils::submitHighScorePlayerRank(v);
}

void UserTeamInfo::setMultiDeckNum(int v) { m_multiDeckNum = v; }

int UserTeamInfo::getMultiDeckNum()
{
	if (m_multiDeckNum != -1)
		return m_multiDeckNum;

	return 0;
}

void UserTeamInfo::setInboxMessagesCnt(int v)
{
	if (v >= 0)
		m_inboxMessageCount = v;
}

int UserTeamInfo::getInboxMessagesCnt() { return m_indexMessageCount; }


long long UserTeamInfo::getMaxWarehouseCnt() const
{
	return CCX_GET(m_addWarehouseCount) + CCX_GET(m_wareHouseCount);
}

long long UserTeamInfo::getSumFrdCnt() const
{
	return CCX_GET(m_maxFriendCount) + m_addFriendCount;
}

long long UserTeamInfo::getSumUnitCnt() const
{
	return CCX_GET(m_maxUnitCount) + CCX_GET(m_addUnitCount);
}

long long UserTeamInfo::getTotalGems() const
{
	return m_paidGems + m_freeGems;
}

unsigned long long UserTeamInfo::getTotalTickets() const
{
	return m_summonTicket + m_energyTickets + m_itemSpaceTickets + m_inventorySpaceTickets;
}
