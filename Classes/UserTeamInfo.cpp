#include "pch.h"
#include "UserTeamInfo.h"
#include "DefineMst.h"
#include "Utils.h"

SET_SHARED_SINGLETON(UserTeamInfo);

#define XOR_SET(x) \
	int rng = 0; \
	do \
		rng = RANDOM_FUNC(); \
	while (rng < 0); \
	rng = (rng % 233) + 2; \
	x##Xor = rng ^ v; \
	x##Magic = rng; \
	x = v;

int UserTeamInfo::getFightRecoveryTime() const
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

void UserTeamInfo::setActionPoint(int v) { XOR_SET(m_actionPoint); }
int UserTeamInfo::getActionPoint() { return m_actionPointXor ^ m_actionPointMagic; }
int UserTeamInfo::getActionRestTimer() { return m_actionRestTimerXor ^ m_actionRestTimerMagic; }

void UserTeamInfo::setActionRestTimer(int v)
{
	if (v <= 0)
		v = 0;

	XOR_SET(m_actionRestTimer);
	m_actionRestTimerTimestamp = CommonUtils::getNowUnitxTime();
}

void UserTeamInfo::setAddUnitCnt(int v) { XOR_SET(addUnitCount); }
void UserTeamInfo::setAddWarehouseCnt(int v) { XOR_SET(addWarehouseCount); }
void UserTeamInfo::setBraveCoin(int v) { XOR_SET(braveCoin); }

void UserTeamInfo::setExp(int v)
{
	XOR_SET(exp);

	if (exp > 0 && getExp() < exp)
		Utils::submitHighScorePlayerExp();
}

void UserTeamInfo::setFightPoint(int v)
{
	XOR_SET(fightPoint);
}

void UserTeamInfo::setFightRestTimer(int v)
{
	if (v <= 0)
		v = 0;

	XOR_SET(fightRestTimer);
	fightRestTimerTimestamp = CommonUtils::getNowUnitxTime();
}

void UserTeamInfo::setFriendAgreeCnt(int v)
{
	XOR_SET(friendAgreeCount);
}

void UserTeamInfo::setFriendPoint(int v)
{
	XOR_SET(friendPoint);
}

void UserTeamInfo::setGiftRecieveCnt(int v)
{
	XOR_SET(giftReceiveCount);
}

void UserTeamInfo::setKarma(int v)
{
	XOR_SET(karma);
}

void UserTeamInfo::setMaxActionPoint(int v)
{
	XOR_SET(maxActionPoint);
}

void UserTeamInfo::setMaxEqpSlotCnt(int v)
{
	XOR_SET(maxEqpSlot);
}

void UserTeamInfo::setMaxFightPoint(int v)
{
	XOR_SET(maxFightPoint);
}

void UserTeamInfo::setMaxFrdCnt(int v)
{
	XOR_SET(maxFrdCount);
}

void UserTeamInfo::setMaxUnitCnt(int v)
{
	XOR_SET(maxUnitCount);
}

void UserTeamInfo::setLv(int v)
{
	XOR_SET(level);
}

void UserTeamInfo::setPresentCnt(int v)
{
	XOR_SET(presentCnt);
}

void UserTeamInfo::setWarehouseCnt(int v)
{
	XOR_SET(warehouseCnt);
}

void UserTeamInfo::setZel(int v)
{
	XOR_SET(zel);
}

void UserTeamInfo::decActionRestTimer()
{
	if (actionRestTimerMagic != actionRestTimerXor)
	{
		auto current = CommonUtils::getNowUnitxTime();
        auto diff = current - actionRestTimerTimestamp;
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

		actionRestTimerTimestamp = current;
	}
}


void UserTeamInfo::decFightRestTimer()
{
	if (fightRestTimerXor != fightRestTimerMagic)
	{
		auto current = CommonUtils::getNowUnitxTime();
		auto diff = current - fightRestTimerTimestamp;
		
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

		fightRestTimerTimestamp = current;
	}
}
