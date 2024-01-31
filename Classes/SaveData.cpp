#include "pch.h"
#include "SaveData.h"
#include "SaveDataKeys.h"
#include "SaveUtils.h"
#include "CommonUtils.h"

SET_SHARED_SINGLETON(SaveData);

void SaveData::UnitImgTypeDataDel()
{
	SaveUtils::saveUserDefaults(UNIT_IMG_TYPE_SAVE_KEY, "");
}

void SaveData::clearAchievementSortFlg()
{
	auto conv = CommonUtils::IntToString(280);
	auto key = std::string(SORT_ACHIEVEMENT_FILTER_KEY) + conv;
	SaveUtils::saveUserDefaults(key, SAVE_KEY_DEFAULT_3);
}

void SaveData::clearAllSortFlg()
{
	clearSortFlg();
	clearItemSortFlg();
	clearFriendSortFlg();
	clearItemSpehereSortFlg();
	clearAchievementSortFlg();
	clearUnitDictSortFlg();
	clearItemDictSortFlg();
	clearSummonerExSkillSortFlg();
}

void SaveData::clearFriendSortFlg()
{
	auto p = SOFT_NEW_FRIEND_FILTER_KEY + CommonUtils::IntToString(601);
	SaveUtils::saveUserDefaults(p, "");

	p = SOFT_NEW_FRIEND_FILTER_KEY + CommonUtils::IntToString(113);
	SaveUtils::saveUserDefaults(p, "");

	p = SOFT_NEW_FRIEND_FILTER_KEY + CommonUtils::IntToString(114);
	SaveUtils::saveUserDefaults(p, "");

	p = SOFT_NEW_FRIEND_FILTER_KEY + CommonUtils::IntToString(115);
	SaveUtils::saveUserDefaults(p, "");

	p = SOFT_NEW_FRIEND_FILTER_KEY + CommonUtils::IntToString(1402);
	SaveUtils::saveUserDefaults(p, "");

	p = SOFT_NEW_FRIEND_FILTER_KEY + CommonUtils::IntToString(1503);
	SaveUtils::saveUserDefaults(p, "");

	p = SOFT_NEW_FRIEND_FILTER_KEY + CommonUtils::IntToString(1251);
	SaveUtils::saveUserDefaults(p, "");
}

void SaveData::clearGrandQuestIconFlg()
{
	setGrandQuestIconFlg(0);
}

void SaveData::clearItemDictSortFlg()
{
	auto p = SORT_ITEM_DICT_KEY + CommonUtils::IntToString(830);
	SaveUtils::saveUserDefaults(p, SAVE_KEY_DEFAULT_3);
}

void SaveData::clearItemSortFlg()
{
	auto p = NEW_SORT_FILTER_ITEM_KEY + CommonUtils::IntToString(267);
	SaveUtils::saveUserDefaults(p, "");

	auto p = NEW_SORT_FILTER_ITEM_KEY + CommonUtils::IntToString(265);
	SaveUtils::saveUserDefaults(p, "");

	auto p = NEW_SORT_FILTER_ITEM_KEY + CommonUtils::IntToString(269);
	SaveUtils::saveUserDefaults(p, "");

	auto p = NEW_SORT_FILTER_ITEM_KEY + CommonUtils::IntToString(1284);
	SaveUtils::saveUserDefaults(p, "");

	auto p = NEW_SORT_FILTER_ITEM_KEY + CommonUtils::IntToString(1505);
	SaveUtils::saveUserDefaults(p, "");
}

void SaveData::clearItemSpehereSortFlg()
{
	auto p = SORT_SPHERE_FILTER_KEY + CommonUtils::IntToString(280);
	SaveUtils::saveUserDefaults(p, SAVE_KEY_DEFAULT_3);

	auto p = SORT_SPHERE_FILTER_KEY + CommonUtils::IntToString(901);
	SaveUtils::saveUserDefaults(p, SAVE_KEY_DEFAULT_3);

	auto p = SORT_SPHERE_FILTER_KEY + CommonUtils::IntToString(1320);
	SaveUtils::saveUserDefaults(p, SAVE_KEY_DEFAULT_3);
}

void SaveData::clearLobiConfigInfo()
{
	v1 = (UserConfigInfo*)UserConfigInfo::shared((UserConfigInfo*)this);
	UserConfigInfo::setRecWindow(v1, 0);
	v2 = (UserConfigInfo*)UserConfigInfo::shared(v1);
	UserConfigInfo::setRecMode(v2, 0);
	v3 = (UserConfigInfo*)UserConfigInfo::shared(v2);
	(*(void(__fastcall**)(UserConfigInfo*, __int64))(*(_QWORD*)v3 + 48LL))(v3, 1LL);
	v4 = (UserConfigInfo*)UserConfigInfo::shared(v3);
	(*(void(__fastcall**)(UserConfigInfo*, __int64))(*(_QWORD*)v4 + 64LL))(v4, 1LL);
	v5 = (UserConfigInfo*)UserConfigInfo::shared(v4);
	(*(void(__fastcall**)(UserConfigInfo*, __int64))(*(_QWORD*)v5 + 80LL))(v5, 1LL);
	v6 = (UserConfigInfo*)UserConfigInfo::shared(v5);
	(*(void(__fastcall**)(UserConfigInfo*, __int64))(*(_QWORD*)v6 + 96LL))(v6, 1LL);
	v7 = (UserConfigInfo*)UserConfigInfo::shared(v6);
	(*(void(__fastcall**)(UserConfigInfo*, __int64))(*(_QWORD*)v7 + 112LL))(v7, 1LL);
	v8 = (UserConfigInfo*)UserConfigInfo::shared(v7);
	(*(void(__fastcall**)(UserConfigInfo*, __int64))(*(_QWORD*)v8 + 128LL))(v8, 1LL);
	v9 = (UserConfigInfo*)UserConfigInfo::shared(v8);
	(*(void(__fastcall**)(UserConfigInfo*, __int64))(*(_QWORD*)v9 + 144LL))(v9, 1LL);
	v10 = (SaveData*)UserConfigInfo::shared(v9);
	(*((void(__fastcall**)(SaveData*, __int64))v10->cocos2d::CCObject::cocos2d::CCObject::vftable + 20))(v10, 1LL);
	return SaveData::saveUserConfigInfo(v10);
}

void SaveData::clearSortFlg()
{
	auto SORT_FILTER = "new_sort_filter_unit_key";
	auto p = SORT_FILTER + CommonUtils::IntToString(642);
	SaveUtils::saveUserDefaults(p, "");

	auto p = SORT_FILTER + CommonUtils::IntToString(643);
	SaveUtils::saveUserDefaults(p, "");

	auto p = SORT_FILTER + CommonUtils::IntToString(317);
	SaveUtils::saveUserDefaults(p, "");

	auto p = SORT_FILTER + CommonUtils::IntToString(130);
	SaveUtils::saveUserDefaults(p, "");

	auto p = SORT_FILTER + CommonUtils::IntToString(132);
	SaveUtils::saveUserDefaults(p, "");

	auto p = SORT_FILTER + CommonUtils::IntToString(316);
	SaveUtils::saveUserDefaults(p, "");

	auto p = SORT_FILTER + CommonUtils::IntToString(97);
	SaveUtils::saveUserDefaults(p, "");

	auto p = SORT_FILTER + CommonUtils::IntToString(1808);
	SaveUtils::saveUserDefaults(p, "");
}

void SaveData::clearSummonerExSkillSortFlg()
{
	auto p = std::string("sort_summoner_exskill_filter_key_") + CommonUtils::IntToString(4020);
	SaveUtils::saveUserDefaults(p, "\n0,0,0");
}

void SaveData::clearUnitDictSortFlg()
{
	auto p = std::string("&sort_unit_dict_key_") + CommonUtils::IntToString(840);
	SaveUtils::saveUserDefaults(p, "\n0,0,0");
}

std::string SaveData::getAchievementSortFlg(int id)
{
	return SaveUtils::loadUserDefaults(std::string("sort_achievement_filter_key_") + CommonUtils::IntToString(id));
}

std::string SaveData::getArenaBattleID()
{
	auto p = SaveUtils::loadUserDefaults("arena_battle_id");
	if (!p.length())
		return "0";

	return p;
}

bool SaveData::getBoostItemResetFlg()
{
	auto p = SaveUtils::loadUserDefaults("boost_item_reset");
	if (p == "valid")
		return true;

	return false;
}

long SaveData::getCADailyCoolingPeriodEnd()
{
	auto p = SaveUtils::loadUserDefaults("CA_SaveData_DailyCoolingPeriodEnd");
	if (!p.length())
		return 0;

	return std::stol(p);
}

long SaveData::getCADailyCoolingPeriodStart()
{
	auto p = SaveUtils::loadUserDefaults("CA_SaveData_DailyCoolingPeriodStart");
	if (!p.length())
		return 0;

	return std::stol(p);
}

long SaveData::getCAWeeklyResetTime()
{
	auto p = SaveUtils::loadUserDefaults("CA_SaveData_WeeklyResetTime");
	if (!p.length())
		return 0;

	return std::stol(p);
}

std::string SaveData::getCampaignScriptAgain()
{
	return SaveUtils::loadUserDefaults("*campaign_script_again");
}

std::string SaveData::getCsvSring(std::string str)
{
	return str + ",";
}

bool SaveData::getDeviceTransferFlg()
{
	auto p = SaveUtils::loadUserDefaults("device_transfer");
	return p.length() > 0 && CommonUtils::StrToInt(p) != 0;
}

bool SaveData::getEp3BoostItemResetFlg()
{
	auto p = SaveUtils::loadUserDefaults("ep3_boost_item_reset");
	return p == "valid";
}

bool SaveData::getEp3TitleLogoFlg()
{
	auto p = SaveUtils::loadUserDefaults("EP3_TITLE_LOGO_KEY");
	return p == "valid";
}

void SaveData::loadKeyChain()
{
	auto accountName = SaveUtils::getAccountNameFromKeyChain(DEFAULT_KEY_CHAIN);
	if (!accountName.empty())
	{
#error "X"
	}
	auto password = SaveUtils::getPasswordFromKeyChain(DEFAULT_KEY_CHAIN);
}

std::string SaveData::getServiceRequestEndpoint()
{
	return SaveUtils::loadUserDefaults("srepParm");
}
