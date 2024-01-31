#pragma once

class UserTeamInfo : public cocos2d::CCObject
{
	SHARED_SINGLETON(UserTeamInfo);

public:
	
	UserTeamInfo() = default;
	~UserTeamInfo() = default;

	void decActionRestTimer(void);
	void decFightRestTimer(void);
	int getActionPoint(void) const { return actionPointXor ^ actionPointMagic; }
	int getActionRestTimer(void) const { return actionRestTimerXor ^ actionRestTimerMagic; }
	int getActiveDeckNum(void) const { return activeDeckNum; }
	unsigned int getAddFrdCnt(void) const { return addFrdCount; }
	int getAddUnitCnt(void) const { return addUnitCountXor ^ addUnitCountMagic; }
	int getAddWarehouseCnt(void) const { return addWarehouseCountXor ^ addWarehouseCountMagic; }
	int getArenaDeckNum(void) const
	{
		if (arenaDeckNum != -1)
			return arenaDeckNum;

		return 0;
	}
	int getBraveCoin(void) const { return braveCoinXor ^ braveCoinMagic; }
	int getBravePointsCurrent(void) const { return bravePointsCurrent; }
	int getBravePointsTotal(void) const { return bravePointsTotal; }
	int getColosseumTicket(void) const { return colosseumTickets; }
	int getCompletedTaskCount(void) const { return completedTaskCount; }
	int getDeckCost(void) const { return deckCost; }
	short getEnergyTickets(void) const { return 1; }
	int getExp(void) const { return expXor ^ expMagic; }
	int getFightPoint(void) const { return fightPointXor ^ fightPointMagic; }
	int getFightRecoveryTime(void) const;
	int getFightRestTimer(void) const { return fightRestTimerXor ^ fightRestTimerMagic; }
	int getFreeGems(void) const { return gems[static_cast<int>(GemType::Free)]; }
	int getFriendAgreeCnt(void) const { return friendAgreeCountXor ^ friendAgreeCountMagic; }
	std::string getFriendMessage(void) const { return friendMessage; }
	std::string getFriendMessageOrg(void) const { return friendMessageOrg; }
	int getFriendPoint(void) const { return friendPointXor ^ friendPointMagic; }
	int getGiftRecieveCnt(void) const { return giftReceiveCountXor ^ giftReceiveCountMagic; }
	int getInboxMessagesCnt(void) const { return inboxMessageCount; }
	int getInventorySpaceTickets(void) const { return 1; }
	int getItemSpaceTickets(void) const { return 1; }
	int getKarma(void) const { return karmaXor ^ karmaMagic; }
	int getLv(void) const { return levelXor ^ levelMagic; }
	int getMaxActionPoint(void) const { return maxActionPointXor ^ maxActionPointMagic; }
	int getMaxEqpSlotCnt(void) const { return maxEqpSlotXor ^ maxEqpSlotMagic; }
	int getMaxFightPoint(void) const { return maxFightPointXor ^ maxFightPointMagic; }
	int getMaxFrdCnt(void) const { return maxFrdCountXor ^ maxFrdCountMagic; }
	int getMaxUnitCnt(void) const { return maxUnitCountXor ^ maxUnitCountMagic; }
	int getMaxWarehouseCnt(void) const { return (warehouseCntXor ^ warehouseCntMagic) + (addWarehouseCountXor ^ addWarehouseCountMagic); }
	int getMultiDeckNum(void) const
	{
		if (multiDeckNum != -1)
			return multiDeckNum;

		return 0;
	}

	int getMysteryBoxCount(void) const { return mysteryBoxCount; }
	int getPaidGems(void) const { return gems[static_cast<int>(GemType::Paid)]; }
	int getPresentCnt(void) const { return presentCntXor ^ presentCntMagic; }
	int getRainbowCoin(void) const { return rainbowCoin; }
	int getReinforcementDeckEx1Num(void) const { return reinforcementDeckEx[static_cast<int>(ReinforcementType::Ex1)]; }
	int getReinforcementDeckEx2Num(void) const { return reinforcementDeckEx[static_cast<int>(ReinforcementType::Ex2)]; }
	int getReinforcementDeckNum(void) const { return reinforcementDeckNum; }
	int getServerActiveDeckNum(void) const { return serverActiveDeckNum; }
	int getSlotgameFlg(void) const { return slotgameFlg; }
	int getSumFrdCnt(void) const { return addFrdCount + (maxFrdCountXor ^ maxFrdCountMagic); }
	int getSumUnitCnt(void) const { return (maxUnitCountXor ^ maxUnitCountMagic) + (addUnitCountXor ^ addUnitCountMagic); }
	int getSummonTicket(void) const { return summonTicket; }
	int getTotalGems(void) const { return gems[static_cast<int>(GemType::Paid)] + gems[static_cast<int>(GemType::Free)]; }
	int getTotalTickets(void) const { return summonTicket + energyTickets + itemSpaceTickets + inventorySpaceTickets; }
	std::string getUserID(void) const { return userId; }
	std::string getWantGift(void) const { return wantedGift; }
	int getWarehouseCnt(void) const { return warehouseCntXor ^ warehouseCntMagic; }
	int getZel(void) const { return zelXor ^ zelMagic; }
	void incKarma(int karma);
	void incZel(int zel);
	void setActionPoint(int v);
	void setActionRestTimer(int v);
	void setActiveDeckNum(int v) { activeDeckNum = v; }
	void setAddFrdCnt(int v) { addFrdCount = v; }
	void setAddUnitCnt(int v);
	void setAddWarehouseCnt(int v);
	void setArenaDeckNum(int v) { arenaDeckNum = v; }
	void setBraveCoin(int v);
	void setBravePointsCurrent(int v) { bravePointsCurrent = v; }
	void setBravePointsTotal(int v) { bravePointsTotal = v; }
	void setColosseumTicket(int v) { colosseumTickets = v; }
	void setCompletedTaskCount(int v) { completedTaskCount = v; }
	void setDeckCost(int v) { deckCost = v; }
	void setEnergyTickets(ushort v) { energyTickets = v; }
	void setExp(int v);
	void setFightPoint(int v);
	void setFightRestTimer(int v);
	void setFreeGems(int v) { gems[static_cast<int>(GemType::Free)] = v; }
	void setFriendAgreeCnt(int v);
	void setFriendMessage(std::string v) { friendMessage = v; }
	void setFriendMessageOrg(std::string v) { friendMessageOrg = v; }
	void setFriendPoint(int v);
	void setGiftRecieveCnt(int v);
	void setInboxMessagesCnt(int v)
	{
		if (v >= 0)
			inboxMessageCount = v;
		else
			inboxMessageCount = 0;
	}
	void setInventorySpaceTickets(ushort v) { inventorySpaceTickets = v; }
	void setItemSpaceTickets(ushort v) { itemSpaceTickets = v; }
	void setKarma(int v);
	void setLv(int v);
	void setMaxActionPoint(int v);
	void setMaxEqpSlotCnt(int v);
	void setMaxFightPoint(int v);
	void setMaxFrdCnt(int v);
	void setMaxUnitCnt(int v);
	void setMultiDeckNum(int v) { multiDeckNum = v; }
	void setMysteryBoxCount(int v) { mysteryBoxCount = v; }
	void setPaidGems(int v) { gems[static_cast<int>(GemType::Paid)] = v; }
	void setPresentCnt(int v);
	void setRainbowCoin(int v) { rainbowCoin = v; }
	void setReinforcementDeckEx1Num(int v) { reinforcementDeckEx[static_cast<int>(ReinforcementType::Ex1)] = v; }
	void setReinforcementDeckEx2Num(int v) { reinforcementDeckEx[static_cast<int>(ReinforcementType::Ex2)] = v; }
	void setReinforcementDeckNum(int v) { reinforcementDeckNum = v; }
	void setServerActiveDeckNum(int v) { serverActiveDeckNum = v; }
	void setSlotgameFlg(bool v) { slotgameFlg = v; }
	void setSummonTicket(ushort v) { summonTicket = v; }
	void setUserID(std::string v) { userId = v; }
	void setWantGift(std::string v) { wantedGift = v; }
	void setWarehouseCnt(int v);
	void setZel(int v);

private:
	std::string userId;
	int levelXor;
	uint8_t levelMagic;
	int level;
	int expXor;
	uint8_t expMagic;
	int exp;
	int actionPointXor;
	uint8_t actionPointMagic;
	int actionPoint;
	int maxActionPointXor;
	uint8_t maxActionPointMagic;
	int maxActionPoint;
	int fightPointXor;
	uint8_t fightPointMagic;
	int fightPoint;
	int maxFightPointXor;
	uint8_t maxFightPointMagic;
	int maxFightPoint;
	int maxUnitCountXor;
	uint8_t maxUnitCountMagic;
	int maxUnitCount;
	int addUnitCountXor;
	uint8_t addUnitCountMagic;
	int addUnitCount;
	int deckCost;
	unsigned int maxEqpSlotXor;
	uint8_t maxEqpSlotMagic;
	int maxEqpSlot;
	int maxFrdCountXor;
	uint8_t maxFrdCountMagic;
	int maxFrdCount;
	int addFrdCount;
	int friendPointXor;
	uint8_t friendPointMagic;
	int friendPoint;
	int zelXor;
	uint8_t zelMagic;
	int zel;
	int karmaXor;
	uint8_t karmaMagic;
	int karma;
	int braveCoinXor;
	uint8_t braveCoinMagic;
	int braveCoin;
	std::string friendMessage;
	std::string friendMessageOrg;
	int warehouseCntXor;
	uint8_t warehouseCntMagic;
	int warehouseCnt;
	int addWarehouseCountXor;
	uint8_t addWarehouseCountMagic;
	int addWarehouseCount;
	std::string wantedGift;
	int presentCntXor;
	uint8_t presentCntMagic;
	int presentCnt;
	int friendAgreeCountXor;
	uint8_t friendAgreeCountMagic;
	int friendAgreeCount;
	int giftReceiveCountXor;
	uint8_t giftReceiveCountMagic;
	int giftReceiveCount;
	int actionRestTimerXor;
	uint8_t actionRestTimerMagic;
	int actionRestTimer;
	int fightRestTimerXor;
	uint8_t fightRestTimerMagic;
	int fightRestTimer;
	time_t actionRestTimerTimestamp;
	time_t fightRestTimerTimestamp;
	int activeDeckNum;
	int arenaDeckNum;
	int multiDeckNum;
	int reinforcementDeckNum;
	int reinforcementDeckEx[static_cast<int>(ReinforcementType::Max)];
	bool slotgameFlg;
	int serverActiveDeckNum;
	int colosseumTickets;
	int bravePointsTotal;
	int bravePointsCurrent;
	short summonTicket;
	short energyTickets;
	short itemSpaceTickets;
	short inventorySpaceTickets;
	int rainbowCoin;
	int inboxMessageCount;
	int completedTaskCount;
	int mysteryBoxCount;
	int gems[static_cast<int>(GemType::Max)];
};
