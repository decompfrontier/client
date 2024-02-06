#pragma once

class UserTeamInfo : public cocos2d::CCObject
{
	SHARED_SINGLETON(UserTeamInfo);

public:
	UserTeamInfo();
	~UserTeamInfo() = default;

	CCX_SYNTHESIZE(int, m_actionPoint, ActionPoint);
	CCX_PROPERTY(int, m_actionRestTimer, ActionRestTimer);
	CC_SYNTHESIZE(int, m_activeDeckNum, ActiveDeckNum);
	CCX_SYNTHESIZE(int, m_addUnitCount, AddUnitCnt);
	CC_SYNTHESIZE(int, m_addFriendCount, AddFrdCnt);
	CCX_SYNTHESIZE(int, m_unitCount, UnitCnt);
	CCX_SYNTHESIZE(int, m_addWarehouseCount, AddWarehouseCnt);
	CC_PROPERTY(int, m_arenaDeckNum, ArenaDeckNum);
	CCX_SYNTHESIZE(int, m_braveCoin, BraveCoin);
	CC_SYNTHESIZE(int, m_bravePointsCurrent, BravePointsCurrent);
	CC_SYNTHESIZE(int, m_bravePointsTotal, BravePointsTotal);
	CC_SYNTHESIZE(int, m_colosseumTickets, ColosseumTicket);
	CC_SYNTHESIZE(int, m_completedTaskCount, CompletedTaskCount);
	CC_SYNTHESIZE(int, m_deckCost, DeckCost);
	CC_PROPERTY(unsigned short, m_energyTickets, EnergyTickets);
	CCX_PROPERTY(int, m_exp, Exp);
	CCX_PROPERTY(int, m_fightPoint, FightPoint);
	CCX_PROPERTY_READONLY(bool, m_FightRecoveryTime, FightRecoveryTime);
	CCX_PROPERTY(int, m_fightRestTimer, FightRestTimer);
	CC_SYNTHESIZE(int, m_freeGems, FreeGems);
	CCX_SYNTHESIZE(int, m_friendAgreeCount, FriendAgreeCnt);
	CC_SYNTHESIZE(std::string, m_friendMessage, FriendMessage);
	CC_SYNTHESIZE(std::string, m_friendMessageOriginal, FriendMessageOrg);
	CCX_SYNTHESIZE(int, m_friendPoint, FriendPoint);
	CCX_SYNTHESIZE(int, m_giftFriendCount, GiftFriendCount);
	CCX_SYNTHESIZE(int, m_giftReceiveCount, GiftRecieveCnt);
	CC_PROPERTY(int, m_indexMessageCount, IndexMessageCount);
	CC_PROPERTY(unsigned short, m_inventorySpaceTickets, InventorySpaceTickets);
	CC_PROPERTY(unsigned short, m_itemSpaceTickets, ItemSpaceTickets);
	CCX_SYNTHESIZE(int, m_karma, Karma);
	CCX_PROPERTY(int, m_level, Lv);
	CCX_SYNTHESIZE(int, m_maxActionPoint, MaxActionPoint);
	CCX_SYNTHESIZE(int, m_maxEquipSlotCount, MaxEqpSlotCnt);
	CCX_SYNTHESIZE(int, m_maxFightPoint, MaxFightPoint);
	CCX_SYNTHESIZE(int, m_maxFriendCount, MaxFrdCnt);
	CCX_SYNTHESIZE(int, m_maxUnitCount, MaxUnitCnt);
	CC_PROPERTY(int, m_multiDeckNum, MultiDeckNum);
	CC_SYNTHESIZE(int, m_mysteryBoxCount, MysteryBoxCount);
	CC_SYNTHESIZE(int, m_paidGems, PaidGems);
	CCX_SYNTHESIZE(int, m_presentCount, PresentCnt);
	CC_SYNTHESIZE(int, m_rainbowCoin, RainbowCoin);
	CC_SYNTHESIZE(int, m_reinforcementDeckEx1, ReinforcementDeckEx1Num);
	CC_SYNTHESIZE(int, m_reinforcementDeckEx2, ReinforcementDeckEx2Num);
	CC_SYNTHESIZE(int, m_reinforcementDeckNum, ReinforcementDeckNum);
	CC_SYNTHESIZE(int, m_serverActiveDeckNum, ServerActiveDeckNum);
	CC_SYNTHESIZE(bool, m_slotGameFlag, SlotgameFlg);
	CC_SYNTHESIZE(unsigned short, m_summonTicket, SummonTicket);
	CC_SYNTHESIZE(std::string, m_userId, UserID);
	CC_SYNTHESIZE(std::string, m_wantGift, WantGift);
	CCX_SYNTHESIZE(int, m_wareHouseCount, WarehouseCnt);
	CCX_SYNTHESIZE(int, m_zel, Zel);
	CC_PROPERTY(int, m_inboxMessageCount, InboxMessagesCnt);

	long long getMaxWarehouseCnt() const;
	long long getSumFrdCnt() const;
	long long getSumUnitCnt() const;
	long long getTotalGems() const;
	unsigned long long getTotalTickets() const;

	void incKarma(int k);
	void incZel(int z);
	void decActionRestTimer();
	void decFightRestTimer();

protected:
	time_t m_actionRestTimerTimestamp, m_fightRestTimerTimestamp;
};

