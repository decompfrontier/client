#pragma once

class SaveData : public cocos2d::CCObject
{
	SHARED_SINGLETON(SaveData);

public:
	SaveData();
	~SaveData();

	void UnitImgTypeDataDel();
	void addParam(std::string&, std::string);
	void clearAchievementSortFlg();
	void clearAllSortFlg();
	void clearFriendSortFlg();
	void clearGrandQuestIconFlg();
	void clearItemSortFlg();
	void clearItemDictSortFlg();
	void clearItemSpehereSortFlg();
	void clearLobiConfigInfo();
	void clearSortFlg();
	void clearSummonerExSkillSortFlg();
	void clearUnitDictSortFlg();
	void decodeBadgeInfoData();
	void decodeCASuspendData(std::string);
	void decodeCampaignSuspendData(std::string);
	void decodeMissionReinforcement(std::string);
	void decodeMissionSuspendData(std::string);
	void decodeTrialMissionFriends(std::string);
	void decodeUnitImgTypeData(std::string);
	void deleteCASuspendData();
	void deleteKeyChain();
	void deleteMissionReinforcement();
	void deleteMissionSuspendData();
	void deleteUserInfo();
	void encodeBadgeInfoData(std::string);
	void encodeCampaignSuspendData(std::string, std::string);
	void encodeCASuspendData(std::string, std::string);
	void encodeCampaignSuspendDel();
	void encodeMissionReinforcement(std::string, std::string);
	void encodeMissionSuspendData(std::string, std::string);
	void encodeTrialMissionFriends(std::string, std::string);
	void encodeUnitImgTypeData(std::string);
	bool existCASuspendData(std::string);
	bool existMissionReinforcement(std::string);
	bool existMissionSuspendData(std::string);
	bool existReloadFileID(int);
	bool existTrialMissionFriends(std::string);
	bool existUser();
	std::string getAchievementSortFlg(int id);
	std::string getArenaBattleID();
	bool getBoostItemResetFlg();
	long getCADailyCoolingPeriodEnd();
	long getCADailyCoolingPeriodStart();
	long getCAWeeklyResetTime();
	std::string getCampaignScriptAgain();
	std::string getContactID() const { return contactID; }
	std::string getCsvSring(std::string str);
	bool getDeviceTransferFlg();
	bool getEp3BoostItemResetFlg();
	bool getEp3TitleLogoFlg();
	void getEventTokenStatus();
	void getFirstDescInfo();
	void getFriendID();
	void getFriendSortFlg();
	void GrandQuestIconFlg();
	int getGumiLivePaidGemsCount() const { return paidGemsCount; }
	std::string getHandleName() const { return handleName; }
	void getItemDictSortFlg(int);
	void getItemSetID();
	void getItemSortFlg(int);
	void getLoadResourceMstVersion();
	void getLoadUnitMstVersion();
	void getLobiSignupFlg();
	void getLoginErrorCnt();
	void getLoginState();
	unsigned int getModeChangeCnt() const { return modeChangeCount; }
	void getMultiFirstPlayFlg();
	void getMultiRoomAutoJoinValue();
	void getMultiRoomInvitationLimitValue();
	void getMultiRoomDisplayListValue();
	void getMultiRoomQuickMatchingValue();
	void getMultiTuToMbbValue();
	void getNewItemFilterFlg(int);
	void getNewSphereFilterFlg(int);
	void getNewUnitFilterFlg(int);
	void getNoticeID();
	void getPauseFlg();
	void getPlayMovieFlg();
	void getRaidRestartErrorCnt();
	void getRecFlg();
	void getRecUseSettingFlg();
	bool getResumeFlag() const { return resumeFlag; }
	void getServiceRequestEndpoint();
	void getSortFlg(int);
	void getSphereSortFlg(int);
	void getSphereSortFlg(int);
	void getSummonerExSkillSortFlg(int);
	void getTitleErrorCnt();
	void getUnitDictSortFlg(int);
	void getUnitGatchaFixSortFlg(int);
	std::string getUserID() const { return userId; }
	void incLoginErrorCnt();
	void incRaidRestartErrorCnt();
	void incTitleErrorCnt();
	bool isArenaRandomRule();
	bool isArenaRetire();
	bool isCompaignRecovery();
	bool isDataRecovery();
	bool isSaveTargetVersion(std::string);
	bool isUseSummonTickets();
	void loadCABattleLog();
	void loadCASuspendData();
	void loadCurrentLanguageSetting();
	void loadDungeonEventState();
	void loadFieldAISuspendData();
	void loadFileCntInfo();
	void loadFirstDescInfo();
	void loadKeyChain();
	void loadLanguageDownloadFlag();
	void loadLastHomeSceneID();
	void loadMissionFixPartyDeckInfo();
	void loadMissionRepeatConfigInfo();
	void loadMissionSuspendData();
	void loadMissionSuspendParts();
	void loadReloadFileIDList(std::vector<int>&);
	void loadSandbagConfigData();
	void loadSupportID();
	void loadSupportURL();
	void loadTargetLanguageSetting();
	void loadUserConfigInfo();
	void loadUserInfo();
	void loadVersionInfo();
	void readParam(std::string&);
	void saveCABattleLog(std::string);
	void saveCASuspendData(std::string);
	void saveCurrentLanguageSetting(int);
	void saveDungeonEventState(std::string, std::string, std::string);
	void saveEventTokenStatus(std::string);
	void saveFieldAISuspendData(int);
	void saveFileCntINfo();
	void saveItemSetID(std::string);
	void saveKeyChain();
	void saveLanguageDownloadFlag(int);
	void saveLastHomeSceneID(int);
	void saveMissionFixPartyDeckInfo(std::string);
	void saveMissionRepeatConfigInfo();
	void saveMissionSuspendData(std::string);
	void saveMissionSuspendParts(int);
	void saveMultiGuildClassID(int);
	void saveMultiSelectStampInfo(int, std::string);
	void saveMultihistoryStampInfo(std::string);
	void saveReloadFileIDList(std::vector<int>&);
	void saveSandbagConfigData(std::string);
	void saveSupportID(std::string);
	void saveSupportURL(std::string);
	void saveTargetLanguageSetting(int);
	void saveUserConfigInfo();
	void saveUserInfo();
	void saveVersionInfo();
	void saveVersionInfoForce();
	void setAchievementSortFlg(std::string, int);
	void setArenaBattleID(std::string);
	void setArenaRandomRule(bool);
	void setArenaRetireFlg(bool);
	void setBoostItemResetFlg(bool);
	void setCADailyCoolingPeriodEnd(long);
	void setCADailyCoolingPeriodStart(long);
	void setCAWeeklyResetTime(long);
	void setCampaignRecoveryFlg(bool);
	void setCampaignScriptAgain(std::string);
	void setDataRecoveryFlg(bool);
	void setDeviceTransferFlg(bool);
	void setEp3BoostItemResetFlg(bool);
	void setEP3TitleLogoFlg(bool);
	void setFirstDescInfo(std::string);
	void setFriendID(std::string);
	void setFriendSortFlg(std::string, int);
	void setGrandQuestIconFlg(bool);
	void setGumilivePaidGemsCount(int paidGems) { paidGemsCount = paidGems; }
	void setHandleName(std::string handleName) { this->handleName = handleName; }
	void setItemDictSortFlg(std::string, int);
	void setItemSortFlg(std::string, int);
	void setLoadResourceMstVersion(int);
	void setLoadUnitMsgVersion(int);
	void setLobiSignupFlg(bool);
	void setLoginErrorCnt(int);
	void setLoginState(int);
	void setMultiFirstPlayFlg(bool);
	void setMultiRoomAutoJoinValue(bool);
	void setMultiRoomDisplayListValue(bool);
	void setMultiRoomInvitationLimitValue(bool);
	void setMultiRoomQuickMatchingValue(bool);
	void setMultiTuToMbbValue(bool);
	void setNewItemFilter(int, int, int, int);
	void setNewItemFilterFlg(int, bool);
	void setNewSphereFilter(int, int, int, int);
	void setNewSphereFilterFlg(int, bool);
	void setNewUnitFilter(int, int, int, long, long);
	void setNewUnitFilterFlg(int, bool);
	void setNoticeID(int);
	void setPauseFlg(bool);
	void setPlayMovieFlg(bool);
	void setRaidRestartErrorCnt(int);
	void setRecFlg(bool);
	void setRecUseSettingFlg(bool);
	void setReloadFileID(int);
	void setResumeFlag(bool);
	void setServiceRequestEndpoint(std::string);
	void setSortFlg(std::string, int);
	void setSphereSortFlg(std::string, int);
	void setSummonerExSkillSortFlg(std::string, int);
	void setTitleErrorCnt(int);
	void setUnitDictSortFlg(std::string, int);
	void setUseSummonTickets(bool);
	void setUserID(std::string userid) { this->userId = userid; }

private:
	// byte[3];
	std::string userId;
	// byte[8]
	std::string handleName;
	// byte[8]
	unsigned int modeChangeCount;
	// byte[4]
	std::string contactID;
	// byte[16]
	bool resumeFlag;
	// byte[28]
	int paidGemsCount;
	// byte[25]
};
