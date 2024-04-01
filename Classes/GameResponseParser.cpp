#include "pch.h"
#include "GameResponseParser.h"
#include "HeaderResponse.h"
#include "BodyResponse.h"
#include "MessageResponse.h"

void GameResponseParser::parseTag(const std::map<std::string, picojson::value>& json, const char* key, const char* aesKey)
{
	BaseResponse* resp = nullptr;

	if (!strcmp(key, GAMERESPONSE_HEADER))
	{
		resp = new HeaderResponse();
	}
	else if (!strcmp(key, GAMERESPONSE_BODY))
	{
		resp = new BodyResponse();
	}
	else if (!strcmp(key, GAMERESPONSE_MESSAGE))
	{
		resp = new MessageResponse();
	}

	resp->autorelease();

	auto it = json.find(key);

	if (it != json.end())
	{
		if (it->second.is<picojson::object>())
		{
			auto& obj = it->second.get<picojson::object>();

			bool isFirst = true;
			bool result = true;

			for (const auto& v : obj)
			{
				result = resp->readParam(0, 0, v.first.c_str(), v.second.to_str().c_str(), isFirst);
				isFirst = false; // DECOMP note: this is not 100% what bf does but I think it does this way

				if (!result)
					break;
			}

			if (result && (strcmp(key, GAMERESPONSE_HEADER) > 0 || !dynamic_cast<HeaderResponse*>(resp)->getIsError()))
			{
				if (!strcmp(key, GAMERESPONSE_BODY))
				{
                    auto q = dynamic_cast<BodyResponse*>(resp);
					auto decoded = CommonUtils::decodeCStringForBase64(q->getEncodeData().c_str(), aesKey);

					if (decoded != "[]")
					{
                        picojson::value out;
                        picojson::parse(out, decoded);

                        for (i = (const char*)v42[6]; ; i = (char*)v42 + 33)
                        {
                            if (!(unsigned __int8)GameResponseParser::parseBodyTag(v40, v41, i))
                            {
                                v50 = 1;
                                goto LABEL_90;
                            }
                            v40 = (SaveData*)i;
                            if (!strcmp(i, "KeC10fuL"))
                                v44 = 1;
                            v46 = (_QWORD*)v42[1];
                            if (v46)
                            {
                                do
                                {
                                    v47 = v46;
                                    v46 = (_QWORD*)*v46;
                                } while (v46);
                            }
                            else
                            {
                                v47 = (_QWORD*)v42[2];
                                if ((_QWORD*)*v47 != v42)
                                {
                                    v48 = (__int64)(v42 + 2);
                                    do
                                    {
                                        v49 = *(_QWORD*)v48;
                                        v47 = *(_QWORD**)(*(_QWORD*)v48 + 16LL);
                                        v48 = *(_QWORD*)v48 + 16LL;
                                    } while (*v47 != v49);
                                }
                            }
                            v42 = v47;
                            if (v47 == v43)
                                break;
                            if ((v47[4] & 1) != 0)
                                goto LABEL_73;
                        LABEL_72:
                            ;
                        }
                        v50 = 0;
                        if ((v44 & 1) != 0)
                        {
                            v51 = (SaveData*)SaveData::shared(v40);
                            SaveData::saveVersionInfo(v51);
                            v52 = (SaveData*)SaveData::shared(v51);
                            SaveData::saveFileCntInfo(v52);
                        }
                    }
				}
			}
		}
	}
}

BaseResponse* GameResponseParser::getResponseObject(const char* groupName)
{
    MessageResponse* v2; // rbx

    if (!strcmp(a2, GAMERESPONSE_MESSAGE))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MessageResponse::MessageResponse(v2);
    }
    else
    {
        v2 = 0LL;
    }
    if (!strcmp(a2, RESPONSE_ERROR))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ErrorResponse::ErrorResponse(v2);
    }
    if (!strcmp(a2, RESPONSE_SIGNALKEY))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        SignalKeyResponse::SignalKeyResponse(v2);
    }
    if (!strcmp(a2, RESPONSE_USERINFO))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        UserInfoResponse::UserInfoResponse((UserInfoResponse*)v2);
    }
    if (!strcmp(a2, RESPONSE_USERTEAMINFO))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        UserTeamInfoResponse::UserTeamInfoResponse(v2);
    }
    if (!strcmp(a2, RESPONSE_USERTEAMARCHIVE))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserTeamArchiveResponse::UserTeamArchiveResponse(v2);
    }
    if (!strcmp(a2, "4ceMWH6k"))
    {
        v2 = (MessageResponse*)operator new(0x28uLL);
        UserUnitInfoResponse::UserUnitInfoResponse(v2, 1);
    }
    if (!strcmp(a2, "qC2tJs4E"))
    {
        v2 = (MessageResponse*)operator new(0x28uLL);
        UserUnitInfoResponse::UserUnitInfoResponse(v2, 0);
    }
    if (!strcmp(a2, "3kcmQy7B"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitFavoriteResponse::UnitFavoriteResponse(v2);
    }
    if (!strcmp(a2, "VSRPkdId"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ItemFavoriteResponse::ItemFavoriteResponse(v2);
    }
    if (!strcmp(a2, GROUP_MSTLIST))
    {
        v2 = (MessageResponse*)operator new(0x38uLL);
        VersionInfoResponse::VersionInfoResponse(v2);
    }
    if (!strcmp(a2, "fcoRMe14"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitMstResponse::UnitMstResponse(v2);
    }
    if (!strcmp(a2, "07bx6EXN"))
    {
        v2 = (MessageResponse*)operator new(0x28uLL);
        UnitCgsMstResponse::UnitCgsMstResponse(v2);
    }
    if (!strcmp(a2, "3SG2wX0R"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        AreaMstResponse::AreaMstResponse(v2);
    }
    if (!strcmp(a2, "01pzrTCm"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        DungeonMstResponse::DungeonMstResponse(v2);
    }
    if (!strcmp(a2, "oXeC1Ak9"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MissionMstResponse::MissionMstResponse(v2);
    }
    if (!strcmp(a2, "UT1SVg59"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserClearMissionInfoResponse::UserClearMissionInfoResponse(v2);
    }
    if (!strcmp(a2, "xZH6EIQ7"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ReinforcementInfoResponse::ReinforcementInfoResponse(v2);
    }
    if (!strcmp(a2, "pj41dy9g"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        BattleGroupMstResponse::BattleGroupMstResponse(v2);
    }
    if (!strcmp(a2, "Kz7qfSs5"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        MissionNumResponse::MissionNumResponse(v2);
    }
    if (!strcmp(a2, "F5Vs19mb"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MissionRewardResponse::MissionRewardResponse(v2);
    }
    if (!strcmp(a2, "5hafnym7"))
    {
        v2 = (MessageResponse*)operator new(0x28uLL);
        UnitSkillMstResponse::UnitSkillMstResponse(v2, 0);
    }
    if (!strcmp(a2, "8aiBoHg5"))
    {
        v2 = (MessageResponse*)operator new(0x28uLL);
        UnitSkillMstResponse::UnitSkillMstResponse(v2, 1);
    }
    if (!strcmp(a2, "z5U7utsm"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ExtraPassiveSkillMstResponse::ExtraPassiveSkillMstResponse(v2);
    }
    if (!strcmp(a2, "U0v5IeJo"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MonsterMstResponse::MonsterMstResponse(v2);
    }
    if (!strcmp(a2, "8hoyIF9Q"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MonsterCgsMstResponse::MonsterCgsMstResponse(v2);
    }
    if (!strcmp(a2, "YDv9bJ3s"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserLevelMstResponse::UserLevelMstResponse(v2);
    }
    if (!strcmp(a2, "yY3Mvp51"))
    {
        v2 = (MessageResponse*)operator new(0x278uLL);
        UnitEvoMstResponse::UnitEvoMstResponse(v2);
    }
    if (FUNC_FIELD_AI && !strcmp(a2, "EGiqSu5f"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FieldAIMstResponse::FieldAIMstResponse(v2);
    }
    if (!strcmp(a2, "0H1hV6iD"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitExtMstResponse::UnitExtMstResponse(v2);
    }
    if (!strcmp(a2, "I82p0wCL"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitOpeEvoResponse::UnitOpeEvoResponse(v2);
    }
    if (!strcmp(a2, "yXNM8kL3"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        PermitPlaceResponse::PermitPlaceResponse(v2);
    }
    if (!strcmp(a2, "Y73tHKS8"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        PermitPlaceSpResponse::PermitPlaceSpResponse(v2);
    }
    if (!strcmp(a2, "tojMy68W"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FriendInfoResponse::FriendInfoResponse(v2);
    }
    if (!strcmp(a2, "5Y4GJeo3"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GachaMstResponse::GachaMstResponse(v2);
    }
    if (!strcmp(a2, "1IR86sAv"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GachaInfoResponse::GachaInfoResponse(v2);
    }
    if (!strcmp(a2, "8JEzC89y"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GachaFixInfoResponse::GachaFixInfoResponse(v2);
    }
    if (!strcmp(a2, "Km35HAXv"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        OpeUserUnitResponse::OpeUserUnitResponse(v2);
    }
    if (!strcmp(a2, "89ausgc4"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        AIMstResponse::AIMstResponse((AIMstResponse*)v2);
    }
    if (!strcmp(a2, "3ktU0D2e"))
    {
        v2 = (MessageResponse*)operator new(0x30uLL);
        UnitSkillLevelMstResponse::UnitSkillLevelMstResponse(v2, 0);
    }
    if (!strcmp(a2, "VZwB7f3j"))
    {
        v2 = (MessageResponse*)operator new(0x30uLL);
        UnitSkillLevelMstResponse::UnitSkillLevelMstResponse(v2, 1);
    }
    if (!strcmp(a2, "m9Ada1PN"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MonsterSkillMstResponse::MonsterSkillMstResponse(v2);
    }
    if (!strcmp(a2, "sEA41vFK"))
    {
        v2 = (MessageResponse*)operator new(0x28uLL);
        UserPresentInfoResponse::UserPresentInfoResponse(v2);
    }
    if (FUNC_CAMPAIGN_SCHEME && !strcmp(a2, "4MCxgS5p"))
    {
        v2 = (MessageResponse*)operator new(0x30uLL);
        CampaignReceiptResponse::CampaignReceiptResponse(v2);
    }
    if (!strcmp(a2, "2C7LDzYk"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ItemMstResponse::ItemMstResponse(v2);
    }
    if (!strcmp(a2, "8f0bCciN"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RecipeMstResponse::RecipeMstResponse(v2);
    }
    if (!strcmp(a2, "71U5wzhI"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserEquipItemInfoResponse::UserEquipItemInfoResponse(v2);
    }
    if (!strcmp(a2, "nAligJSQ"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserEquipBoostItemInfoResponse::UserEquipBoostItemInfoResponse(v2);
    }
    if (!strcmp(a2, "9wjrh74P"))
    {
        v2 = (MessageResponse*)operator new(0x28uLL);
        UserWarehouseInfoResponse::UserWarehouseInfoResponse(v2);
    }
    if (!strcmp(a2, "VkoZ5t3K"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        DefineMstResponse::DefineMstResponse(v2);
    }
    if (!strcmp(a2, "2bGtP7vV"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        LeaderSkillMstResponse::LeaderSkillMstResponse(v2);
    }
    if (!strcmp(a2, "JYFGe9y6"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitExpPatternMstResponse::UnitExpPatternMstResponse(v2);
    }
    if (!strcmp(a2, "LBY10TPU"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitTypeMstResponse::UnitTypeMstResponse(v2);
    }
    if (!strcmp(a2, "Ked15IpH"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        TrophyGradeMstResponse::TrophyGradeMstResponse(v2);
    }
    if (!strcmp(a2, "1NTG2oVZ"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        TrophyGroupMstResponse::TrophyGroupMstResponse(v2);
    }
    if (!strcmp(a2, "6CTU8m2v"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        TrophyMstResponse::TrophyMstResponse(v2);
    }
    if (!strcmp(a2, "H18CjPKI"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserTrophyGradeInfoResponse::UserTrophyGradeInfoResponse(v2);
    }
    if (!strcmp(a2, "L5j19iny"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        EffectMstResponse::EffectMstResponse(v2);
    }
    if (!strcmp(a2, "9KUSHf4s"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        EffectGroupMstResponse::EffectGroupMstResponse(v2);
    }
    if (!strcmp(a2, "51yQrDBR"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        PermitRecipeResponse::PermitRecipeResponse(v2);
    }
    if (!strcmp(a2, "5nBa3CAe"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        InformationMstResponse::InformationMstResponse(v2);
    }
    if (!strcmp(a2, "GV81ctzR"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserUnitDictionaryResponse::UserUnitDictionaryResponse(v2);
    }
    if (!strcmp(a2, "75t0sx9z"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        BattleMonsterGroupMstResponse::BattleMonsterGroupMstResponse(v2);
    }
    if (!strcmp(a2, "n64NOpfy"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        BattleMonsterGroupPartsMstResponse::BattleMonsterGroupPartsMstResponse(v2);
    }
    if (!strcmp(a2, "vW75Pgpw"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        PurchaseMstResponse::PurchaseMstResponse(v2);
    }
    if (!strcmp(a2, "R5E9odPZ"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        PurchaseGpMstResponse::PurchaseGpMstResponse(v2);
    }
    if (!strcmp(a2, "ABg6Lr3p"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        PurchaseAsMstResponse::PurchaseAsMstResponse(v2);
    }
    if (!strcmp(a2, "UVz5cuZ4"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        PurchaseAgeLimitMstResponse::PurchaseAgeLimitMstResponse(v2);
    }
    if (!strcmp(a2, "4W6EhXLS"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        UserPurchaseInfoResponse::UserPurchaseInfoResponse(v2);
    }
    if (!strcmp(a2, "Lh1I3dGo"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        TownFacilityMstResponse::TownFacilityMstResponse(v2);
    }
    if (!strcmp(a2, "1y2JDv79"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        TownLocationMstResponse::TownLocationMstResponse(v2);
    }
    if (!strcmp(a2, "YRgx49WG"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserTownFacilityInfoResponse::UserTownFacilityInfoResponse(v2);
    }
    if (!strcmp(a2, "yj46Q2xw"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserTownLocationInfoResponse::UserTownLocationInfoResponse(v2);
    }
    if (!strcmp(a2, "s8TCo2MS"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserTownLocationDetailResponse::UserTownLocationDetailResponse(v2);
    }
    if (!strcmp(a2, "Bm1WNDQ0"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GiftItemMstResponse::GiftItemMstResponse(v2);
    }
    if (!strcmp(a2, "30uygM9m"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserGiftInfoResponse::UserGiftInfoResponse(v2);
    }
    if (!strcmp(a2, "d0EkJ4TB"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        TownFacilityLvMstResponse::TownFacilityLvMstResponse(v2);
    }
    if (!strcmp(a2, "9ekQ4tZq"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        TownLocationLvMstResponse::TownLocationLvMstResponse(v2);
    }
    if (!strcmp(a2, "9x4zZCeN"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        HelpMstResponse::HelpMstResponse(v2);
    }
    if (!strcmp(a2, "5C9LuNrk"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        HelpSubMstResponse::HelpSubMstResponse(v2);
    }
    if (!strcmp(a2, "a3IgzZ0q"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        HelpDetailMstResponse::HelpDetailMstResponse(v2);
    }
    if (!strcmp(a2, "8jBJ7uKR"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserArenaInfoResponse::UserArenaInfoResponse(v2);
    }
    if (!strcmp(a2, "6kWq78zx"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ArenaRankMstResponse::ArenaRankMstResponse(v2);
    }
    if (!strcmp(a2, "fQ3XImM2"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ArenaSuggestEnemyResponse::ArenaSuggestEnemyResponse(v2);
    }
    if (!strcmp(a2, "9NX83YVe"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ArenaBattleEnemyInfoResponse::ArenaBattleEnemyInfoResponse(v2);
    }
    if (!strcmp(a2, "5K5wsBoL"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ArenaBattlePartyInfoResponse::ArenaBattlePartyInfoResponse(v2);
    }
    if (!strcmp(a2, "bd5Rj6pN"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserItemDinctionaryInfoResponse::UserItemDinctionaryInfoResponse(v2);
    }
    if (!strcmp(a2, "gkVH5p8r"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitCommentMstResponse::UnitCommentMstResponse(v2);
    }
    if (!strcmp(a2, "36Sd0Aub"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SoundMstResponse::SoundMstResponse(v2);
    }
    if (!strcmp(a2, "yTEvm85f"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ArenaBattleResultResponse::ArenaBattleResultResponse(v2);
    }
    if (!strcmp(a2, "okd0y3Ir"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ArenaBattleRewardResponse::ArenaBattleRewardResponse(v2);
    }
    if (!strcmp(a2, "1ZbHB6Im"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitOpeResultResponse::UnitOpeResultResponse(v2);
    }
    if (!strcmp(a2, "BYaF62TE"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FriendUserArenaInfoResponse::FriendUserArenaInfoResponse(v2);
    }
    if (!strcmp(a2, "PQ56vbkI"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserTeamArenaArchiveResponse::UserTeamArenaArchiveResponse(v2);
    }
    if (!strcmp(a2, "d98mjNDc"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserSoundInfoResponse::UserSoundInfoResponse(v2);
    }
    if (!strcmp(a2, "dX7S2Lc1"))
    {
        v2 = (MessageResponse*)operator new(0x30uLL);
        UserPartyDeckInfoResponse::UserPartyDeckInfoResponse(v2);
    }
    if (!strcmp(a2, "5PR2VmH1"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MissionBreakInfoResponse::MissionBreakInfoResponse(v2);
    }
    if (!strcmp(a2, "9Q1Lq5FS"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MissionStartInfoResponse::MissionStartInfoResponse(v2);
    }
    if (!strcmp(a2, "Hmiwj75u"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeRankingResponse::ChallengeRankingResponse(v2);
    }
    if (!strcmp(a2, "h09mEvDR"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeHrResponse::ChallengeHrResponse(v2);
    }
    if (!strcmp(a2, "cvg8hzp9"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeInfoResponse::ChallengeInfoResponse(v2);
    }
    if (!strcmp(a2, "21BqKn8b"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeEndResponse::ChallengeEndResponse(v2);
    }
    if (!strcmp(a2, "zW1i02aG"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeGradeMstResponse::ChallengeGradeMstResponse(v2);
    }
    if (!strcmp(a2, "dn0NfRy1"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeItemMstResponse::ChallengeItemMstResponse(v2);
    }
    if (!strcmp(a2, "5M8jI4cP"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeMisMstResponse::ChallengeMisMstResponse(v2);
    }
    if (!strcmp(a2, "ZYSWU9V1"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeResultResponse::ChallengeResultResponse(v2);
    }
    if (!strcmp(a2, "ZSC3t4Fv"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeRewardResponse::ChallengeRewardResponse(v2);
    }
    if (!strcmp(a2, "4C1Wt8sS"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeRewardMstResponse::ChallengeRewardMstResponse(v2);
    }
    if (!strcmp(a2, "7B5Ue9kH"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeStartResponse::ChallengeStartResponse(v2);
    }
    if (!strcmp(a2, "cv9rC2KN"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeMisUserInfoResponse::ChallengeMisUserInfoResponse(v2);
    }
    if (!strcmp(a2, "mQC4s5ka"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeMstResponse::ChallengeMstResponse(v2);
    }
    if (!strcmp(a2, "nUmaEC41"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeMvpResponse::ChallengeMvpResponse(v2);
    }
    if (!strcmp(a2, "p73SJiWv"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeRankRewardResponse::ChallengeRankRewardResponse(v2);
    }
    if (!strcmp(a2, "P8V71kbw"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeRankRewardMstResponse::ChallengeRankRewardMstResponse(v2);
    }
    if (!strcmp(a2, "s7A3bGLe"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeUserInfoResponse::ChallengeUserInfoResponse(v2);
    }
    if (!strcmp(a2, "kN2i7qds"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeUserTeamResponse::ChallengeUserTeamResponse(v2);
    }
    if (!strcmp(a2, "dPM7oJDl"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FrontierGateInfoResponse::FrontierGateInfoResponse(v2);
    }
    if (!strcmp(a2, "33W6N124"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FrontierGateRankingResponse::FrontierGateRankingResponse(v2);
    }
    if (!strcmp(a2, "d8DM0Fvx"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SPChallengeMisMstResponse::SPChallengeMisMstResponse(v2);
    }
    if (!strcmp(a2, "4GXhvmE8"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SPChallengeGradeMstResponse::SPChallengeGradeMstResponse(v2);
    }
    if (!strcmp(a2, "7RFJ2YD9"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SPChallengeRewardMstResponse::SPChallengeRewardMstResponse(v2);
    }
    if (!strcmp(a2, "Sro5ef9G"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SPChallengeItemMstResponse::SPChallengeItemMstResponse(v2);
    }
    if (!strcmp(a2, "Gn2T1wgJ"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SPChallengeUserInfoResponse::SPChallengeUserInfoResponse(v2);
    }
    if (!strcmp(a2, "1IudP9Yv"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        SPChallengeStartResponse::SPChallengeStartResponse(v2);
    }
    if (!strcmp(a2, "Is8aJg4e"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        SPChallengeEndResponse::SPChallengeEndResponse(v2);
    }
    if (!strcmp(a2, "57YByKEt"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        SPChallengeResultResponse::SPChallengeResultResponse(v2);
    }
    if (!strcmp(a2, "fN15PJDa"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SPChallengeRewardResponse::SPChallengeRewardResponse(v2);
    }
    if (!strcmp(a2, "Q63q1b5G"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SPChallengeRankingResponse::SPChallengeRankingResponse(v2);
    }
    if (!strcmp(a2, "W58bU1k7"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        SPChallengeInfoResponse::SPChallengeInfoResponse(v2);
    }
    if (!strcmp(a2, "8UawtzHw"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FirstDescMstResponse::FirstDescMstResponse(v2);
    }
    if (!strcmp(a2, "n4y6zU1I"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        NpcMessageMstResponse::NpcMessageMstResponse(v2);
    }
    if (!strcmp(a2, "yNnvj59x"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        NpcMessageOverwriteInfoResponse::NpcMessageOverwriteInfoResponse(v2);
    }
    if (!strcmp(a2, "S9zI8iPU"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidWorldMstResponse::RaidWorldMstResponse(v2);
    }
    if (!strcmp(a2, "K3H0uzhE"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidMapMstResponse::RaidMapMstResponse(v2);
    }
    if (!strcmp(a2, "4XA9m7of"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidMapPointMstResponse::RaidMapPointMstResponse(v2);
    }
    if (!strcmp(a2, "maU6riN7"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidMapRouteMstResponse::RaidMapRouteMstResponse(v2);
    }
    if (!strcmp(a2, "VyDP51hx"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidBossRouteMstResponse::RaidBossRouteMstResponse(v2);
    }
    if (!strcmp(a2, "0qK4FXpC"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidBossMstResponse::RaidBossMstResponse(v2);
    }
    if (!strcmp(a2, "8J0dh4YZ"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidMissionMstResponse::RaidMissionMstResponse(v2);
    }
    if (!strcmp(a2, "QEv8VG6C"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidBattleGroupMstResponse::RaidBattleGroupMstResponse(v2);
    }
    if (!strcmp(a2, "20EeL8T1"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidChatTempMstResponse::RaidChatTempMstResponse(v2);
    }
    if (!strcmp(a2, "n2kNWdq5"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidPlayStyleMstResponse::RaidPlayStyleMstResponse(v2);
    }
    if (!strcmp(a2, "Yr5PCpo3"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidRCMstResponse::RaidRCMstResponse(v2);
    }
    if (!strcmp(a2, "9EmPTqn3"))
    {
        v2 = (MessageResponse*)operator new(0x70uLL);
        RaidMisCondClearMstResponse::RaidMisCondClearMstResponse(v2);
    }
    if (!strcmp(a2, "6hLn3bBr"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidMissionPointMstResponse::RaidMissionPointMstResponse(v2);
    }
    if (!strcmp(a2, "8iBuZa3n"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidRoomInfoResponse::RaidRoomInfoResponse(v2);
    }
    if (!strcmp(a2, "9PS8BLiC"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidRoomPartyInfoResponse::RaidRoomPartyInfoResponse(v2);
    }
    if (!strcmp(a2, "uwH62EZy"))
    {
        v2 = (MessageResponse*)operator new(0x140uLL);
        RaidRoomUserInfoResponse::RaidRoomUserInfoResponse(v2);
    }
    if (!strcmp(a2, "g97TWISb"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        RaidRoomMissionInfoResponse::RaidRoomMissionInfoResponse(v2);
    }
    if (!strcmp(a2, "1ry6BKoe"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        RaidUserInfoResponse::RaidUserInfoResponse(v2);
    }
    if (!strcmp(a2, "8LF2ohCY"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidClearMissionInfoResponse::RaidClearMissionInfoResponse(v2);
    }
    if (!strcmp(a2, "taWh0Pr7"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidWorldInfoResponse::RaidWorldInfoResponse(v2);
    }
    if (!strcmp(a2, "81B4TvEK"))
    {
        v2 = (MessageResponse*)operator new(0x40uLL);
        RaidMissionUesrInfoResponse::RaidMissionUesrInfoResponse(v2);
    }
    if (!strcmp(a2, "wc2DiMY1"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidUserPartyDeckInfoResponse::RaidUserPartyDeckInfoResponse(v2);
    }
    if (!strcmp(a2, "awQFCW03"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidMissionRCUpInfoResponse::RaidMissionRCUpInfoResponse(v2);
    }
    if (!strcmp(a2, "4sbU8kdW"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidMissionRewardInfoResponse::RaidMissionRewardInfoResponse(v2);
    }
    if (!strcmp(a2, "zS9s5bEI"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidMissionBatResultInfoResponse::RaidMissionBatResultInfoResponse(v2);
    }
    if (!strcmp(a2, "2cBk5WH8"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidMissionBattleInfoResponse::RaidMissionBattleInfoResponse(v2);
    }
    if (!strcmp(a2, "UsDq1E6o"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidUserBagInfoResponse::RaidUserBagInfoResponse(v2);
    }
    if (!strcmp(a2, "Fx60pCaU"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidBattleMonsterGroupInfoResponse::RaidBattleMonsterGroupInfoResponse(v2);
    }
    if (!strcmp(a2, "ZX7gzQ6s"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidSelFriendInfoResponse::RaidSelFriendInfoResponse(v2);
    }
    if (!strcmp(a2, "8c47oISW"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidRoomMissionItemInfoResponse::RaidRoomMissionItemInfoResponse(v2);
    }
    if (!strcmp(a2, "S2fpqc7K"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidUseItemInfoResponse::RaidUseItemInfoResponse(v2);
    }
    if (!strcmp(a2, "ft4zY6Uv"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidRoomFriendGetResponse::RaidRoomFriendGetResponse(v2);
    }
    if (!strcmp(a2, "2WqoMm4d"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidPermitMissionInfoResponse::RaidPermitMissionInfoResponse(v2);
    }
    if (!strcmp(a2, "Ts2zJ6gj"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidMissionBossInfoResponse::RaidMissionBossInfoResponse(v2);
    }
    if (!strcmp(a2, "7pJD5tNd"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidMissionBossMstResponse::RaidMissionBossMstResponse(v2);
    }
    if (!strcmp(a2, "CEVQ4p7A"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidUserUnitInfoResponse::RaidUserUnitInfoResponse(v2);
    }
    if (!strcmp(a2, "6nqGvdY1"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        RaidUserTeamArchiveInfoResponse::RaidUserTeamArchiveInfoResponse(v2);
    }
    if (!strcmp(a2, "HQtqJGor"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidMissionBossBuffInfoResponse::RaidMissionBossBuffInfoResponse(v2);
    }
    if (!strcmp(a2, "x7YLyM7H"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidMissionBossStatusInfoResponse::RaidMissionBossStatusInfoResponse(v2);
    }
    if (!strcmp(a2, "Qazc0Qv8"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidUserBlackListInfoResponse::RaidUserBlackListInfoResponse(v2);
    }
    if (!strcmp(a2, "ZKHrus68"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserStampInfoResponse::UserStampInfoResponse(v2);
    }
    if (!strcmp(a2, "WCA9VhL3"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidGetChatLogResponse::RaidGetChatLogResponse(v2);
    }
    if (!strcmp(a2, "UC8S1AiX"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        RaidMissionResultResponse::RaidMissionResultResponse(v2);
    }
    if (!strcmp(a2, "Pj6zDW3m"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        NoticeInfoResponse::NoticeInfoResponse(v2);
    }
    if (FUNC_NOTICE_LIST)
    {
        if (!strcmp(a2, "CihCiL05"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            NoticeUserNoticeInfoResponse::NoticeUserNoticeInfoResponse(v2);
        }
        if (!strcmp(a2, "oOmzJJSd"))
        {
            v2 = (MessageResponse*)operator new(0x28uLL);
            NoticeMstResponse::NoticeMstResponse(v2, 0LL);
        }
        if (!strcmp(a2, "bcdqwXES"))
        {
            v2 = (MessageResponse*)operator new(0x28uLL);
            NoticeMstResponse::NoticeMstResponse(v2, 1LL);
        }
        if (!strcmp(a2, "mqq6eMFZ"))
        {
            v2 = (MessageResponse*)operator new(0x28uLL);
            NoticeMstResponse::NoticeMstResponse(v2, 2LL);
        }
        if (!strcmp(a2, "yPOwZWDU"))
        {
            v2 = (MessageResponse*)operator new(0x28uLL);
            NoticeMstResponse::NoticeMstResponse(v2, 3LL);
        }
    }
    else if (!strcmp(a2, "Nq12W9wY"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        NoticeListMstResponse::NoticeListMstResponse(v2);
    }
    if (!strcmp(a2, "AhSmZF07"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UpdateInfoResponse::UpdateInfoResponse(v2);
    }
    if (!strcmp(a2, "hV5vWu6C"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        NpcMstResponse::NpcMstResponse(v2);
    }
    if (!strcmp(a2, "5MJd7t6F"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ResourceMstResponse::ResourceMstResponse((ResourceMstResponse*)v2);
    }
    if (!strcmp(a2, "59U8uoXm"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GachaChangeRateMstResponse::GachaChangeRateMstResponse(v2);
    }
    if (!strcmp(a2, "uGey4j67"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        AppVersionInfoResponse::AppVersionInfoResponse((AppVersionInfoResponse*)v2);
    }
    if (!strcmp(a2, "u3JU7LzQ"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ModelChangeUserInfoResponse::ModelChangeUserInfoResponse(v2);
    }
    if (!strcmp(a2, "Pf97SzVw"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GachaEffectMstResponse::GachaEffectMstResponse(v2);
    }
    if (!strcmp(a2, "B6KhoZ1P"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        BuyCoinInfoResponse::BuyCoinInfoResponse(v2);
    }
    if (!strcmp(a2, "At7Gny2V"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        UrlMstResponse::UrlMstResponse(v2);
    }
    if (!strcmp(a2, "uGey4j67"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        AppVersionInfoResponse::AppVersionInfoResponse((AppVersionInfoResponse*)v2);
    }
    if (!strcmp(a2, "uYHZWC73"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GateMstResponse::GateMstResponse(v2);
    }
    if (!strcmp(a2, "4NG79sX1"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        DungeonKeyMstResponse::DungeonKeyMstResponse(v2);
    }
    if (!strcmp(a2, "eFU7Qtb0"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserDungeonKeyInfoResponse::UserDungeonKeyInfoResponse(v2);
    }
    if (!strcmp(a2, "PnjQ49wK"))
    {
        v2 = (MessageResponse*)operator new(0x30uLL);
        UserTrialPartyDeckInfoResponse::UserTrialPartyDeckInfoResponse(v2);
    }
    if (!strcmp(a2, "Md9gG3c0"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GeneralEventMstResponse::GeneralEventMstResponse(v2);
    }
    if (!strcmp(a2, "QP4cwt7n"))
    {
        v2 = (MessageResponse*)operator new(0x30uLL);
        RaidUserProgressInfoResponse::RaidUserProgressInfoResponse(v2);
    }
    if (!strcmp(a2, "yL3Yrf1M"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        RaidBattleStatusResponse::RaidBattleStatusResponse(v2);
    }
    if (!strcmp(a2, "Fihk73ZR"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidEquipItemInfoResponse::RaidEquipItemInfoResponse(v2);
    }
    if (!strcmp(a2, "GvV1w4yM"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidRsvItemInfoResponse::RaidRsvItemInfoResponse(v2);
    }
    if (!strcmp(a2, "LApo1M3n"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidServeInfoResponse::RaidServeInfoResponse(v2);
    }
    if (!strcmp(a2, "Dp0MjKAf"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserReleaseInfoResponse::UserReleaseInfoResponse(v2);
    }
    if (!strcmp(a2, "4FVXsAw8"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RaidRoomMissionUserPerformanceInfoResponse::RaidRoomMissionUserPerformanceInfoResponse(v2);
    }
    if (!strcmp(a2, "GHUsyi76"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MedalMstResponse::MedalMstResponse(v2);
    }
    if (!strcmp(a2, "6C0kzwM5"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserBraveMedalInfoResponse::UserBraveMedalInfoResponse(v2);
    }
    if (!strcmp(a2, "C7J9hbmr"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        SlotgameInfoResponse::SlotgameInfoResponse(v2);
    }
    if (!strcmp(a2, "s8r5M6wI"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SlotgameResultInfoResponse::SlotgameResultInfoResponse(v2);
    }
    if (!strcmp(a2, "6e4b7sQt"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        FriendPointInfoResponse::FriendPointInfoResponse(v2);
    }
    if (!strcmp(a2, "sBbp47fi"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        UserScenarioInfoResponse::UserScenarioInfoResponse(v2);
    }
    if (!strcmp(a2, "h23iRjGN"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        BadgeInfoResponse::BadgeInfoResponse(v2);
    }
    if (!strcmp(a2, "Bnc4LpM8"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        UserAchievementInfoResponse::UserAchievementInfoResponse(v2);
    }
    if (!strcmp(a2, "YTRJLG65"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserAchievementSubjectInfoResponse::UserAchievementSubjectInfoResponse(v2);
    }
    if (!strcmp(a2, "9j3ALx8I"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserAchievementTradeInfoResponse::UserAchievementTradeInfoResponse(v2);
    }
    if (!strcmp(a2, "LcFCx1Uz"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserAchievementSPSubjectInfoResponse::UserAchievementSPSubjectInfoResponse(v2);
    }
    if (!strcmp(a2, "ZmyQqzLx"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ClearedTutorialSubjectInfoResponse::ClearedTutorialSubjectInfoResponse(v2);
    }
    if (!strcmp(a2, "uxpnA5rF"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FrontierSupportMstResponse::FrontierSupportMstResponse(v2);
    }
    if (!strcmp(a2, "eIQ79KO2"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        FrontierBattleInfoResponse::FrontierBattleInfoResponse(v2);
    }
    if (!strcmp(a2, "mu0kXAlV"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        FrontierEndInfoResponse::FrontierEndInfoResponse(v2);
    }
    if (!strcmp(a2, "Mg8K8Y1a"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        FrontierGateNumResponse::FrontierGateNumResponse(v2);
    }
    if (!strcmp(a2, "QXFCkE67"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FrontierResRewardInfoResponse::FrontierResRewardInfoResponse(v2);
    }
    if (!strcmp(a2, "gmeGFd2s"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        FrontierGateSuspendedInfoResponse::FrontierGateSuspendedInfoResponse(v2);
    }
    if (!strcmp(a2, "MxmCpDRC"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FrontierGatePartyDeckInfoResponse::FrontierGatePartyDeckInfoResponse(v2);
    }
    if (!strcmp(a2, "52xDBRGr"))
    {
        v2 = (MessageResponse*)operator new(0x38uLL);
        FrontierGateUserUnitInfoResponse::FrontierGateUserUnitInfoResponse(v2);
    }
    if (!strcmp(a2, "BxlwipDT"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChronologyMstResponse::ChronologyMstResponse(v2);
    }
    if (!strcmp(a2, "ijpug9FD"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChronologyDetailMstResponse::ChronologyDetailMstResponse(v2);
    }
    if (!strcmp(a2, "aCy8V5Uy"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChronologyWordMstResponse::ChronologyWordMstResponse(v2);
    }
    if (!strcmp(a2, "tjTwQOEb"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserChronologyInfoResponse::UserChronologyInfoResponse(v2);
    }
    if (FUNC_UNIT_VIRTUALLY)
    {
        if (!strcmp(a2, "kvSPgNAk"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            UnitFEGategoryMstResponse::UnitFEGategoryMstResponse(v2);
        }
        if (!strcmp(a2, "kXes8fSi"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            UnitFESkillMstResponse::UnitFESkillMstResponse(v2);
        }
    }
    if (FUNCK_CAMPAIGN)
    {
        if (!strcmp(a2, "C5sF9iAz"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            GuestUnitMstResponse::GuestUnitMstResponse(v2);
        }
        if (!strcmp(a2, "as8G75gK"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignMissionMstResponse::CampaignMissionMstResponse(v2);
        }
        if (!strcmp(a2, "CHd0w9WU"))
        {
            v2 = (MessageResponse*)operator new(0x18uLL);
            CampaignMissionNumInfoResponse::CampaignMissionNumInfoResponse(v2);
        }
        if (!strcmp(a2, "27tgwCUN"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignFlgMstResponse::CampaignFlgMstResponse(v2);
        }
        if (!strcmp(a2, "0sT4d2zV"))
        {
            v2 = (MessageResponse*)operator new(0x40uLL);
            CampaignMissionEndCndMstResponse::CampaignMissionEndCndMstResponse(v2);
        }
        if (!strcmp(a2, "2I9V0o6J"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignMissionInfoResponse::CampaignMissionInfoResponse(v2);
        }
        if (!strcmp(a2, "hT95cq8K"))
        {
            v2 = (MessageResponse*)operator new(0x28uLL);
            CampaignPartyDeckListResponse::CampaignPartyDeckListResponse(v2);
        }
        if (!strcmp(a2, "Ckf3Zx7E"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignEqpItemInfoResponse::CampaignEqpItemInfoResponse(v2);
        }
        if (!strcmp(a2, "5EByfWJ4"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignRsvItemInfoResponse::CampaignRsvItemInfoResponse(v2);
        }
        if (!strcmp(a2, "NjZ6ds1S"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignEqpItemInfoResponse::CampaignEqpItemInfoResponse(v2);
        }
        if (!strcmp(a2, "Nv94RF5q"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignMissionBagInfoResponse::CampaignMissionBagInfoResponse(v2);
        }
        if (!strcmp(a2, "Q83eyrJM"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignRewardBonusMstResponse::CampaignRewardBonusMstResponse(v2);
        }
        if (!strcmp(a2, "p04iC2wr"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignRewardBonusInfoResponse::CampaignRewardBonusInfoResponse(v2);
        }
        if (!strcmp(a2, "iyCP4N1h"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignMapMstResponse::CampaignMapMstResponse(v2);
        }
        if (!strcmp(a2, "aqb6LS9y"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignMapSpotMstResponse::CampaignMapSpotMstResponse(v2);
        }
        if (!strcmp(a2, "Ri1S0JQX"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignMapRouteMstResponse::CampaignMapRouteMstResponse(v2);
        }
        if (!strcmp(a2, "HUo5G8gz"))
        {
            v2 = (MessageResponse*)operator new(0x28uLL);
            CampaignMapIconMstResponse::CampaignMapIconMstResponse(v2);
        }
        if (!strcmp(a2, "hpGBs2R1"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignMapTreasureMstResponse::CampaignMapTreasureMstResponse(v2);
        }
        if (!strcmp(a2, "RseDpY04"))
        {
            v2 = (MessageResponse*)operator new(0x18uLL);
            CampaignMissionEventInfoResponse::CampaignMissionEventInfoResponse(v2);
        }
        if (!strcmp(a2, "x3kCNR4Y"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignEventMstResponse::CampaignEventMstResponse(v2);
        }
        if (!strcmp(a2, "Yusr3Zg5"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignMissionDeckInfoResponse::CampaignMissionDeckInfoResponse(v2);
        }
        if (!strcmp(a2, "w5vHLT0q"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignMissionPartyDeckInfoResponse::CampaignMissionPartyDeckInfoResponse(v2);
        }
        if (!strcmp(a2, "W6F9ECH5"))
        {
            v2 = (MessageResponse*)operator new(0x38uLL);
            CampaignUserUnitInfoResponse::CampaignUserUnitInfoResponse(v2);
        }
        if (!strcmp(a2, "Kwj0nX9T"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            CampaignBattleGroupMstResponse::CampaignBattleGroupMstResponse(v2);
        }
        if (!strcmp(a2, "knPuw69N"))
        {
            v2 = (MessageResponse*)operator new(0x18uLL);
            CampaignMissionBattleStatusInfoResponse::CampaignMissionBattleStatusInfoResponse(v2);
        }
        if (!strcmp(a2, "wVTBA6b5"))
        {
            v2 = (MessageResponse*)operator new(0x18uLL);
            CampaignMissionArchiveInfoResponse::CampaignMissionArchiveInfoResponse(v2);
        }
        if (!strcmp(a2, "4WLJhj2U"))
        {
            v2 = (MessageResponse*)operator new(0x18uLL);
            CampaignSuspendInfoResponse::CampaignSuspendInfoResponse(v2);
        }
        if (!strcmp(a2, "5HXZ0bG8"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            TieupCampaignInfoResponse::TieupCampaignInfoResponse(v2);
        }
    }
    if (FUNC_COLOSSEUM)
    {
        if (!strcmp(a2, "xNCYRb5G"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumRewardInfoResponse::ColosseumRewardInfoResponse(v2);
        }
        if (!strcmp(a2, "1x6T2Trz"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumRewardCategoryInfoResponse::ColosseumRewardCategoryInfoResponse(v2);
        }
        if (!strcmp(a2, "HgYvs3am"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumClassInfoResponse::ColosseumClassInfoResponse(v2);
        }
        if (!strcmp(a2, "7oXGyuVH"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumBattlePartyInfoResponse::ColosseumBattlePartyInfoResponse(v2);
        }
        if (!strcmp(a2, "y7A1mZft"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumBattleEnemyInfoResponse::ColosseumBattleEnemyInfoResponse(v2);
        }
        if (!strcmp(a2, "BZ95Eg3M"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumBattleResultResponse::ColosseumBattleResultResponse(v2);
        }
        if (!strcmp(a2, "wmpLokaR"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumClassMstResponse::ColosseumClassMstResponse(v2);
        }
        if (!strcmp(a2, "iG9H7mHS"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumExtraRuleMstResponse::ColosseumExtraRuleMstResponse(v2);
        }
        if (!strcmp(a2, "aCmTmaOx"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumFormationMstResponse::ColosseumFormationMstResponse(v2);
        }
        if (!strcmp(a2, "O3FDdzHb"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumRewardMstResponse::ColosseumRewardMstResponse(v2);
        }
        if (!strcmp(a2, "bmfB5g09"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumRewardCategoryMstResponse::ColosseumRewardCategoryMstResponse(v2);
        }
        if (!strcmp(a2, "U6MWwoW4"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumRewardStageMstResponse::ColosseumRewardStageMstResponse(v2);
        }
        if (!strcmp(a2, "kaQtidz4"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumSupportMstResponse::ColosseumSupportMstResponse(v2);
        }
        if (!strcmp(a2, "FfBebG0R"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumUserInfoResponse::ColosseumUserInfoResponse(v2);
        }
        if (!strcmp(a2, "7UVKrgle"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumArchiveInfoResponse::ColosseumArchiveInfoResponse(v2);
        }
        if (!strcmp(a2, "dSC5DzuN"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumPastRankingResponse::ColosseumPastRankingResponse(v2);
        }
        if (!strcmp(a2, "Q5jXpri7"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ColosseumDurationInfoResponse::ColosseumDurationInfoResponse(v2);
        }
    }
    if (!strcmp(a2, "JuBeb6YR"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SummonerArmMstResponse::SummonerArmMstResponse(v2);
    }
    if (FUNC_SUMMONER_UNIT)
    {
        if (!strcmp(a2, "BbmQBxhV"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            SummonerImageMstResponse::SummonerImageMstResponse(v2);
        }
        if (!strcmp(a2, "LRB9Z5zm"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            SummonerLevelMstResponse::SummonerLevelMstResponse(v2);
        }
        if (!strcmp(a2, "kgaeVEzx"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            SummonerArmElementMstResponse::SummonerArmElementMstResponse(v2);
        }
        if (!strcmp(a2, "eKPdSFdy"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            SummonerElementLevelMstResponse::SummonerElementLevelMstResponse(v2);
        }
        if (!strcmp(a2, "aYcBePEP"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            SummonerAbilityMstResponse::SummonerAbilityMstResponse(v2);
        }
        if (!strcmp(a2, "6XHRamCn"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            SummonerAbilityLevelMstResponse::SummonerAbilityLevelMstResponse(v2);
        }
        if (!strcmp(a2, "xUo0D9Xb"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            SummonerAbilityUpMstResponse::SummonerAbilityUpMstResponse(v2);
        }
        if (!strcmp(a2, "n5mdIUqj"))
        {
            v2 = (MessageResponse*)operator new(0x18uLL);
            UserSummonerInfoResponse::UserSummonerInfoResponse(v2);
        }
        if (!strcmp(a2, "dhMmbm5p"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            UserSummonerArmsInfoResponse::UserSummonerArmsInfoResponse(v2);
        }
        if (!strcmp(a2, "YsCJD0c5"))
        {
            v2 = (MessageResponse*)operator new(0x18uLL);
            UserSummonerArchiveInfoResponse::UserSummonerArchiveInfoResponse(v2);
        }
        if (!strcmp(a2, "5DXlaudE"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            ShopItemMstResponse::ShopItemMstResponse(v2);
        }
        if (!strcmp(a2, "vE0Qdg33"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            MissionEp3MstResponse::MissionEp3MstResponse(v2);
        }
        if (!strcmp(a2, "XFD1gtfo"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            UnitEp3MstResponse::UnitEp3MstResponse(v2);
        }
        if (!strcmp(a2, "M4w9LT7t"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            MissionEp3RewardResponse::MissionEp3RewardResponse(v2);
        }
        if (!strcmp(a2, "6gEp9sFW"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            SummonerReinforcementInfoResponse::SummonerReinforcementInfoResponse(v2);
        }
        if (!strcmp(a2, "PAd9aS1H"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            SummonerFriendInfoResponse::SummonerFriendInfoResponse(v2);
        }
        if (!strcmp(a2, "s2sZaCgW"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            SummonerOpeResultResponse::SummonerOpeResultResponse(v2);
        }
        if (!strcmp(a2, "YmaEqtA8"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            UserItemTimeLimitInfoResponse::UserItemTimeLimitInfoResponse(v2);
        }
        if (!strcmp(a2, "cq1n3Nfy"))
        {
            v2 = (MessageResponse*)operator new(0x20uLL);
            SummonerExSkillMstResponse::SummonerExSkillMstResponse(v2);
        }
    }
    if (!strcmp(a2, "7biAaD5h"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MinigameSmashResultResponse::MinigameSmashResultResponse(v2);
    }
    if (!strcmp(a2, "RAP6c4X0"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MinigameCardResultResponse::MinigameCardResultResponse(v2);
    }
    if (!strcmp(a2, "V5xcd3SB"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MinigameMyRankingInfoResponse::MinigameMyRankingInfoResponse(v2);
    }
    if (!strcmp(a2, "yCBM81kW"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FriendUserMinigameInfoResponse::FriendUserMinigameInfoResponse(v2);
    }
    if (!strcmp(a2, "Ajp4SmMG"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        MinigameInfoResponse::MinigameInfoResponse(v2);
    }
    if (!strcmp(a2, "C6Au3tzm"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MinigamePastRankingInfoResponse::MinigamePastRankingInfoResponse(v2);
    }
    if (!strcmp(a2, "4xzt8Ra7"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MinigameDurationInfoResponse::MinigameDurationInfoResponse(v2);
    }
    if (!strcmp(a2, "rj5fgJh0"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        DataSettingInfoResponse::DataSettingInfoResponse(v2);
    }
    if (!strcmp(a2, "T_FIXED_REINFORCEMENT"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FixedReinforcementInfoResponse::FixedReinforcementInfoResponse(v2);
    }
    if (!strcmp(a2, "z4Die02g"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitRequirementResponse::UnitRequirementResponse(v2);
    }
    if (!strcmp(a2, "T_TOURNAMENT_MAIN"))
    {
        v2 = (MessageResponse*)operator new(0x28uLL);
        TierTourneyMstResponse::TierTourneyMstResponse(v2);
    }
    if (!strcmp(a2, "T_USER_TOURNAMENT_RANK_INFO"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FriendTierTourneyInfoResponse::FriendTierTourneyInfoResponse(v2);
    }
    if (!strcmp(a2, "T_TOURNAMENT_REWARDS"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        TierTourneyRewardMstResponse::TierTourneyRewardMstResponse(v2);
    }
    if (!strcmp(a2, "T_USER_TOURNAMENT_INFO"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserTournamentInfoResponse::UserTournamentInfoResponse(v2);
    }
    if (!strcmp(a2, "T_TOURNAMENT_POINTS"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        TierTourneyPointsResponse::TierTourneyPointsResponse(v2);
    }
    if (!strcmp(a2, "T_DAILY_REWARD_STATUS"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        DailyRewardClaimResponse::DailyRewardClaimResponse(v2);
    }
    if (!strcmp(a2, "k23D7d43"))
    {
        v2 = (MessageResponse*)operator new(0x38uLL);
        DailyTaskMstResponse::DailyTaskMstResponse(v2);
    }
    if (!strcmp(a2, "a739yK18"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        DailyTaskPrizeMstResponse::DailyTaskPrizeMstResponse(v2);
    }
    if (!strcmp(a2, "p283g07d"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        DailyTaskBonusMstResponse::DailyTaskBonusMstResponse(v2);
    }
    if (!strcmp(a2, "K72eC4nz"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        BundlePacksInfoResponse::BundlePacksInfoResponse(v2);
    }
    if (!strcmp(a2, "yT8fs6jL"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        BundlePacksItemsInfoResponse::BundlePacksItemsInfoResponse(v2);
    }
    if (!strcmp(a2, "yd3D8x1g"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        BundlePurchaseIapResponse::BundlePurchaseIapResponse(v2);
    }
    if (!strcmp(a2, "7itHlDro"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        BundlePacksCategoryInfoResponse::BundlePacksCategoryInfoResponse(v2);
    }
    if (!strcmp(a2, "d2b8d79J"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        VortexArenaMstResponse::VortexArenaMstResponse(v2);
    }
    if (!strcmp(a2, "Qe2iLri2"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserVortexArenaInfoResponse::UserVortexArenaInfoResponse(v2);
    }
    if (!strcmp(a2, "AYd82i1B"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        VortexArenaRewardResponse::VortexArenaRewardResponse(v2);
    }
    if (!strcmp(a2, "93Dimd1w"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        VortexArenaDailyRewardResponse::VortexArenaDailyRewardResponse(v2);
    }
    if (!strcmp(a2, "279baD8Y"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        VortexArenaLeaderboardResponse::VortexArenaLeaderboardResponse(v2);
    }
    if (!strcmp(a2, "7eCia0o3"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        VortexArenaDailyLeaderboardResponse::VortexArenaDailyLeaderboardResponse(v2);
    }
    if (!strcmp(a2, "a3c0d5bi"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        VortexArenaRankMstResponse::VortexArenaRankMstResponse(v2);
    }
    if (!strcmp(a2, "b38aD8by"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        VortexArenaBattleEnemyInfoResponse::VortexArenaBattleEnemyInfoResponse(v2);
    }
    if (!strcmp(a2, "p4db73ba"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        VortexArenaSuggestEnemyResponse::VortexArenaSuggestEnemyResponse(v2);
    }
    if (!strcmp(a2, "d6gl3Yk8"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        VortexArenaBattleRewardResponse::VortexArenaBattleRewardResponse(v2);
    }
    if (!strcmp(a2, "x38dT257"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        VortexArenaClaimResponse::VortexArenaClaimResponse(v2);
    }
    if (!strcmp(a2, "fdLaupiU"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        CollabRewardResponse::CollabRewardResponse(v2);
    }
    if (!strcmp(a2, "3b6da94Y"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        RegistrationPromoPresentResponse::RegistrationPromoPresentResponse(v2);
    }
    if (!strcmp(a2, "Pk5F1vhx"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        BannerInfoMstResponse::BannerInfoMstResponse(v2);
    }
    if (!strcmp(a2, "j2389b5D"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        EventSkillInfoResponse::EventSkillInfoResponse(v2);
    }
    if (!strcmp(a2, "CJyIGhAl"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaBattleRewardInfoResponse::ChallengeArenaBattleRewardInfoResponse(v2);
    }
    if (!strcmp(a2, "zyJhDEiF"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaOpponentUnitInfoResponse::ChallengeArenaOpponentUnitInfoResponse(v2);
    }
    if (!strcmp(a2, "27qwcBAX"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaItemMstResponse::ChallengeArenaItemMstResponse(v2);
    }
    if (!strcmp(a2, "dPB4gW8y"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeArenaMstVersionUpdateResponse::ChallengeArenaMstVersionUpdateResponse(v2);
    }
    if (!strcmp(a2, "t6bRQfln"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeArenaResetInfoResponse::ChallengeArenaResetInfoResponse(v2);
    }
    if (!strcmp(a2, "oICPHRd0"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeArenaSettingInfoResponse::ChallengeArenaSettingInfoResponse(v2);
    }
    if (!strcmp(a2, "O3RRXMgK"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaShopInfoResponse::ChallengeArenaShopInfoResponse(v2);
    }
    if (!strcmp(a2, "15llpugi"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaTreasureRewardInfoResponse::ChallengeArenaTreasureRewardInfoResponse(v2);
    }
    if (!strcmp(a2, "W5F5xe9v"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaTeamInfoResponse::ChallengeArenaTeamInfoResponse(v2);
    }
    if (!strcmp(a2, "sZ8IlW1U"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaTeamInfoUpdateResponse::ChallengeArenaTeamInfoUpdateResponse(v2);
    }
    if (!strcmp(a2, "XGmGpmYW"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeArenaUserInfoResponse::ChallengeArenaUserInfoResponse(v2);
    }
    if (!strcmp(a2, "kS9EnYpd"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeArenaUserInfoResponse::ChallengeArenaUserInfoResponse(v2);
    }
    if (!strcmp(a2, "Tmx1rq96"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaOpponentTeamInfoResponse::ChallengeArenaOpponentTeamInfoResponse(v2);
    }
    if (!strcmp(a2, "Ki8Okx9B"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaUserProgressResponse::ChallengeArenaUserProgressResponse(v2);
    }
    if (!strcmp(a2, "ndrym03D"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeArenaUserRankInfoResponse::ChallengeArenaUserRankInfoResponse(v2);
    }
    if (!strcmp(a2, "FmYdRkjQ"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaUserRewardInfoResponse::ChallengeArenaUserRewardInfoResponse(v2);
    }
    if (!strcmp(a2, "UahnBfGc"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaUserRewardRankInfoResponse::ChallengeArenaUserRewardRankInfoResponse(v2);
    }
    if (!strcmp(a2, "aCo6FrSO"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeArenaLeaguePromotionInfoResponse::ChallengeArenaLeaguePromotionInfoResponse(v2);
    }
    if (!strcmp(a2, "P4VhXkNS"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaUserWarehouseInfoResponse::ChallengeArenaUserWarehouseInfoResponse(v2);
    }
    if (!strcmp(a2, "pZWEqax1"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaRankingResponse::ChallengeArenaRankingResponse(v2);
    }
    if (!strcmp(a2, "XHNT0LR3"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaRankingRewardResponse::ChallengeArenaRankingRewardResponse(v2);
    }
    if (!strcmp(a2, "GgrW4Ble"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeArenaBonusResponse::ChallengeArenaBonusResponse(v2);
    }
    if (!strcmp(a2, "da4AXDup"))
    {
        v2 = (MessageResponse*)operator new(0x28uLL);
        ChallengeArenaMstSettingLeagueResponse::ChallengeArenaMstSettingLeagueResponse(v2);
    }
    if (!strcmp(a2, "wabMqWHq"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ChallengeArenaPvMatchingMstResponse::ChallengeArenaPvMatchingMstResponse(v2);
    }
    if (!strcmp(a2, "OZMHeNoy"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        ChallengeArenaMstPurchaseResponse::ChallengeArenaMstPurchaseResponse(v2);
    }
    if (!strcmp(a2, "2375D38i"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FeatureGatingInfoResponse::FeatureGatingInfoResponse(v2);
    }
    if (!strcmp(a2, "2386Diw1"))
    {
        v2 = (MessageResponse*)operator new(0x28uLL);
        UserEnteredFeatureListResponse::UserEnteredFeatureListResponse(v2);
    }
    if (!strcmp(a2, "2bD8ak3D"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        TutorialSkipResponse::TutorialSkipResponse(v2);
    }
    if (!strcmp(a2, "ad52Diwq"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SummonerJournalMilestoneMstResponse::SummonerJournalMilestoneMstResponse(v2);
    }
    if (!strcmp(a2, "T38aBiw3"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SummonerJournalTaskMstResponse::SummonerJournalTaskMstResponse(v2);
    }
    if (!strcmp(a2, "b2DjiaXp"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SummonerJournalRewardsMstResponse::SummonerJournalRewardsMstResponse(v2);
    }
    if (!strcmp(a2, "M3dw18eB"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        SummonerJournalUserInfoResponse::SummonerJournalUserInfoResponse(v2);
    }
    if (!strcmp(a2, "da38tRai"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SummonerJournalUserTaskInfoResponse::SummonerJournalUserTaskInfoResponse(v2);
    }
    if (!strcmp(a2, "r3D28bqW"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SummonerJournalUserMilestoneInfoResponse::SummonerJournalUserMilestoneInfoResponse(v2);
    }
    if (!strcmp(a2, "a36Dai1b"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GachaBonusGateResponse::GachaBonusGateResponse(v2);
    }
    if (!strcmp(a2, "3da6bd0a"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        UserLoginCampaignInfoResponse::UserLoginCampaignInfoResponse(v2);
    }
    if (!strcmp(a2, "bD18x9Ti"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        LoginCampaignRewardResponse::LoginCampaignRewardResponse(v2);
    }
    if (!strcmp(a2, "Bd29Pqw0"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        LoginCampaignMstResponse::LoginCampaignMstResponse(v2);
    }
    if (!strcmp(a2, "j129kD6r"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        VideoAdInfoResponse::VideoAdInfoResponse(v2);
    }
    if (!strcmp(a2, "bpD29eiQ"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        VideoAdRegionResponse::VideoAdRegionResponse(v2);
    }
    if (!strcmp(a2, "hE1d083b"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SummonTicketV2MstResponse::SummonTicketV2MstResponse(v2);
    }
    if (!strcmp(a2, "a3d5d12i"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SummonTicketV2UserInfoResponse::SummonTicketV2UserInfoResponse(v2);
    }
    if (!strcmp(a2, "sdu3jvhH"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserUnitResummonInfoSgResponse::UserUnitResummonInfoSgResponse(v2);
    }
    if (!strcmp(a2, "da3qD39b"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ResummonGachaMstResponse::ResummonGachaMstResponse(v2);
    }
    if (!strcmp(a2, "D9dkXSPY"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MessageInboxResponse::MessageInboxResponse(v2);
    }
    if (!strcmp(a2, "IkdSufj5"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildInfoResponse::GuildInfoResponse(v2);
    }
    if (!strcmp(a2, "csIuech30"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildMembersInfoResponse::GuildMembersInfoResponse(v2);
    }
    if (!strcmp(a2, "T_GUILD_MESSAGES"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildMessagesResponse::GuildMessagesResponse(v2);
    }
    if (!strcmp(a2, "aj38Jk10"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildJoinRequestInfoResponse::GuildJoinRequestInfoResponse(v2);
    }
    if (!strcmp(a2, "csI7dh30"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRecommendedInfoResponse::GuildRecommendedInfoResponse(v2);
    }
    if (!strcmp(a2, "kj1d80ai"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildSearchResponse::GuildSearchResponse(v2);
    }
    if (!strcmp(a2, "T_GUILD_GUARDIAN"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianResponse::GuildGuardianResponse(v2);
    }
    if (!strcmp(a2, "FxewAaR2"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianSkillResponse::GuildGuardianSkillResponse(v2);
    }
    if (!strcmp(a2, "dag38b71"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildMemberSkillResponse::GuildMemberSkillResponse(v2);
    }
    if (!strcmp(a2, "asl26Gjz"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianSummonResponse::GuildGuardianSummonResponse(v2);
    }
    if (!strcmp(a2, "fDLao3cs"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildMemberSkillMstResponse::GuildMemberSkillMstResponse(v2);
    }
    if (!strcmp(a2, "p3bDi1jq"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildInfoMstResponse::GuildInfoMstResponse(v2);
    }
    if (!strcmp(a2, "d39BqKil"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildInsigniaMstResponse::GuildInsigniaMstResponse(v2);
    }
    if (!strcmp(a2, "jKeiqDbl"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildCreateCostResponse::GuildCreateCostResponse(v2);
    }
    if (!strcmp(a2, "8bD18LPe"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildMemberGuildInfoResponse::GuildMemberGuildInfoResponse(v2);
    }
    if (!strcmp(a2, "adf38ba1"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildBoardInfoResponse::GuildBoardInfoResponse(v2);
    }
    if (!strcmp(a2, "baD81eqw"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildExchangeMstResponse::GuildExchangeMstResponse(v2);
    }
    if (!strcmp(a2, "nMe3ai17"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildUserExchangeInfoResponse::GuildUserExchangeInfoResponse(v2);
    }
    if (!strcmp(a2, "by8ad3ga"))
    {
        v2 = (MessageResponse*)operator new(0x68uLL);
        GuildContributionRestrictionResponse::GuildContributionRestrictionResponse(v2);
    }
    if (!strcmp(a2, "zw8zo15D"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildUserContributionBonusResponse::GuildUserContributionBonusResponse(v2);
    }
    if (!strcmp(a2, "BgawIieF"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        GuildRatesMstResponse::GuildRatesMstResponse(v2);
    }
    if (!strcmp(a2, "g7hx43Pq"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRankingResponse::GuildRankingResponse(v2);
    }
    if (!strcmp(a2, "mK6fd7L7"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidSeasonDataResponse::GuildRaidSeasonDataResponse(v2);
    }
    if (!strcmp(a2, "R6YwbeCB"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRankingDetailResponse::GuildRankingDetailResponse(v2);
    }
    if (!strcmp(a2, "LoLJTlaG"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildPreviousRankingResponse::GuildPreviousRankingResponse(v2);
    }
    if (!strcmp(a2, "IG8atK9y"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidUserInfoResponse::GuildRaidUserInfoResponse(v2);
    }
    if (!strcmp(a2, "Nebq4d8x"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidRoundInfoResponse::GuildRaidRoundInfoResponse(v2);
    }
    if (!strcmp(a2, "mUS1176P"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidRoundRankingResponse::GuildRaidRoundRankingResponse(v2);
    }
    if (!strcmp(a2, "m67Di3wq"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianGroupInfoResponse::GuildGuardianGroupInfoResponse(v2);
    }
    if (!strcmp(a2, "m3hq3kLc"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianPartInfoResponse::GuildGuardianPartInfoResponse(v2);
    }
    if (!strcmp(a2, "o3BeU91Y"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidDifficultyMstResponse::GuildRaidDifficultyMstResponse(v2);
    }
    if (!strcmp(a2, "37uDa81B"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianGroupMstResponse::GuildGuardianGroupMstResponse(v2);
    }
    if (!strcmp(a2, "p3Axq56b"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianGroupLevelMstResponse::GuildGuardianGroupLevelMstResponse(v2);
    }
    if (!strcmp(a2, "83Bda1Bv"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianPartMstResponse::GuildGuardianPartMstResponse(v2);
    }
    if (!strcmp(a2, "TNTSQNCT"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianPartLevelMstResponse::GuildGuardianPartLevelMstResponse(v2);
    }
    if (!strcmp(a2, "H04AphFP"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianPassiveSkillMstResponse::GuildGuardianPassiveSkillMstResponse(v2);
    }
    if (!strcmp(a2, "zCe38bie"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianSkillGroupMstResponse::GuildGuardianSkillGroupMstResponse(v2);
    }
    if (!strcmp(a2, "HwCngVzI"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianSkillGroupLevelMstResponse::GuildGuardianSkillGroupLevelMstResponse(v2);
    }
    if (!strcmp(a2, "zwDqib73"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianSkillMstResponse::GuildGuardianSkillMstResponse(v2);
    }
    if (!strcmp(a2, "88bDa1bw"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianSkillLevelMstResponse::GuildGuardianSkillLevelMstResponse(v2);
    }
    if (!strcmp(a2, "bVe381bC"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildGuardianSkillAIMstResponse::GuildGuardianSkillAIMstResponse(v2);
    }
    if (!strcmp(a2, "K39BaO3e"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidRewardMstResponse::GuildRaidRewardMstResponse(v2);
    }
    if (!strcmp(a2, "8ut74Dai"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidMapBossMstResponse::GuildRaidMapBossMstResponse(v2);
    }
    if (!strcmp(a2, "d29bi8e3"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidLootMstResponse::GuildRaidLootMstResponse(v2);
    }
    if (!strcmp(a2, "D30b6d82"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidSeasonMstResponse::GuildRaidSeasonMstResponse(v2);
    }
    if (!strcmp(a2, "gl7aQbv8"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidSeasonChestMstResponse::GuildRaidSeasonChestMstResponse(v2);
    }
    if (!strcmp(a2, "j38a79px"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidSeasonChestQtyMstResponse::GuildRaidSeasonChestQtyMstResponse(v2);
    }
    if (!strcmp(a2, "6D38bDae"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidChestMstResponse::GuildRaidChestMstResponse(v2);
    }
    if (!strcmp(a2, "hei2Bawq"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidMapPointMstResponse::GuildRaidMapPointMstResponse(v2);
    }
    if (!strcmp(a2, "p76Dci3b"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidMapRouteMstResponse::GuildRaidMapRouteMstResponse(v2);
    }
    if (!strcmp(a2, "z3aid2b7"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidMapMstResponse::GuildRaidMapMstResponse(v2);
    }
    if (!strcmp(a2, "Q4XeCah7"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidDropRateMstResponse::GuildRaidDropRateMstResponse(v2);
    }
    if (!strcmp(a2, "b9Dq1xzi"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidRoomListResponse::GuildRaidRoomListResponse(v2);
    }
    if (!strcmp(a2, "b839kdi1"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidRoomMemberListResponse::GuildRaidRoomMemberListResponse(v2);
    }
    if (!strcmp(a2, "71ocHdQe"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidRoomGuardianGroupResponse::GuildRaidRoomGuardianGroupResponse(v2);
    }
    if (!strcmp(a2, "3quwOva9"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidRoomGuardianPartResponse::GuildRaidRoomGuardianPartResponse(v2);
    }
    if (!strcmp(a2, "IxiGlf2f"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildUserPartyDeckInfoResponse::GuildUserPartyDeckInfoResponse(v2);
    }
    if (!strcmp(a2, "ib718btd"))
    {
        v2 = (MessageResponse*)operator new(0x30uLL);
        GuildPartyDeckInfoResponse::GuildPartyDeckInfoResponse(v2);
    }
    if (!strcmp(a2, "7boad17y"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidBattleMapResponse::GuildRaidBattleMapResponse(v2);
    }
    if (!strcmp(a2, "9b7aDa71"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidBattleGuardianResponse::GuildRaidBattleGuardianResponse(v2);
    }
    if (!strcmp(a2, "yDa71iOw"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidBattleGuardianPartResponse::GuildRaidBattleGuardianPartResponse(v2);
    }
    if (!strcmp(a2, "J3k3Diq0"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidActionMstResponse::GuildRaidActionMstResponse(v2);
    }
    if (!strcmp(a2, "gJdju7aE"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildDefineMstResponse::GuildDefineMstResponse(v2);
    }
    if (!strcmp(a2, "d3gaby8a"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildContributionRestrictionMstResponse::GuildContributionRestrictionMstResponse(v2);
    }
    if (!strcmp(a2, "M9ctnu4Q"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidBattleLogListResponse::GuildRaidBattleLogListResponse(v2);
    }
    if (!strcmp(a2, "fGhi37w9"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidOutpostBuffMstResponse::GuildRaidOutpostBuffMstResponse(v2);
    }
    if (!strcmp(a2, "a3a8ck16"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildItemSkillResponse::GuildItemSkillResponse(v2);
    }
    if (!strcmp(a2, "g71qWtnQ"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidBattleResultScoreResponse::GuildRaidBattleResultScoreResponse(v2);
    }
    if (!strcmp(a2, "YcDPfGlN"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidOverallbattleResultResponse::GuildRaidOverallbattleResultResponse(v2);
    }
    if (!strcmp(a2, "d3gaby8a"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildContributionRestrictionMstResponse::GuildContributionRestrictionMstResponse(v2);
    }
    if (!strcmp(a2, "fRaBu6et"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRecomendedMemberResponse::GuildRecomendedMemberResponse(v2);
    }
    if (!strcmp(a2, "8lAroepR"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRecommendFriendInfoResponse::GuildRecommendFriendInfoResponse(v2);
    }
    if (!strcmp(a2, "ldHdS87"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidTeamQuestObjectiveResponse::GuildRaidTeamQuestObjectiveResponse(v2);
    }
    if (!strcmp(a2, "Nds65hg"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidUserQuestObjectiveResponse::GuildRaidUserQuestObjectiveResponse(v2);
    }
    if (!strcmp(a2, "M94dsd6H"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRaidQuestBcpResponse::GuildRaidQuestBcpResponse(v2);
    }
    if (!strcmp(a2, "r32SB54s"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        GuildRaidSlideQuestResponse::GuildRaidSlideQuestResponse(v2);
    }
    if (!strcmp(a2, "LH8Hss0"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRewardQuestMstResponse::GuildRewardQuestMstResponse(v2);
    }
    if (!strcmp(a2, "PjfsG32"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildQuestMstResponse::GuildQuestMstResponse(v2);
    }
    if (!strcmp(a2, "lHDysPRp"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildBattleScoreInfoResponse::GuildBattleScoreInfoResponse(v2);
    }
    if (!strcmp(a2, "em4noAR5"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SGChatLogInfoListResponse::SGChatLogInfoListResponse(v2);
    }
    if (!strcmp(a2, "N3d1supA"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        SGChatRoomMemberInfoListResponse::SGChatRoomMemberInfoListResponse(v2);
    }
    if (!strcmp(a2, "mebW7mKD"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        VideoAdsSlotgameInfoResponse::VideoAdsSlotgameInfoResponse(v2);
    }
    if (!strcmp(a2, "JMUTrqXh"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        VideoAdsSlotgameResultInfoResponse::VideoAdsSlotgameResultInfoResponse(v2);
    }
    if (!strcmp(a2, "3b6aDakz"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MultiGachaIapMstResponse::MultiGachaIapMstResponse(v2);
    }
    if (!strcmp(a2, "3s2WIvPN"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MultiGachaIapPurchaseIapResponse::MultiGachaIapPurchaseIapResponse(v2);
    }
    if (!strcmp(a2, "18Dak67b"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildLeaderSkillResponse::GuildLeaderSkillResponse(v2);
    }
    if (!strcmp(a2, "d0ajLeRi"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MysteryBoxResponse::MysteryBoxResponse(v2);
    }
    if (!strcmp(a2, "CoAp2aph"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        MysteryBoxRewardResponse::MysteryBoxRewardResponse(v2);
    }
    if (!strcmp(a2, "18Dak67b"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildLeaderSkillResponse::GuildLeaderSkillResponse(v2);
    }
    if (!strcmp(a2, "TMLTZ4tk"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRoundAndBattlesSummaryResponse::GuildRoundAndBattlesSummaryResponse(v2);
    }
    if (!strcmp(a2, "b56mrqM9"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildRoundAndBattlesSummaryResponse::GuildRoundAndBattlesSummaryResponse(v2);
    }
    if (!strcmp(a2, "1fFtoLva"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        GuildRoundAndBattleMembersCountResponse::GuildRoundAndBattleMembersCountResponse(v2);
    }
    if (!strcmp(a2, "l234vdKs"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        EventTokenInfoResponse::EventTokenInfoResponse(v2);
    }
    if (!strcmp(a2, "c2Sls4DD"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        EventTokenExchangeInfoResponse::EventTokenExchangeInfoResponse(v2);
    }
    if (!strcmp(a2, "Sdvs2lds"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        EventTokenExchangeResultInfoResponse::EventTokenExchangeResultInfoResponse(v2);
    }
    if (!strcmp(a2, "49cks405"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        EventUnitInfoResponse::EventUnitInfoResponse(v2);
    }
    if (!strcmp(a2, "a81qad7j"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GuildExtraSkillResponse::GuildExtraSkillResponse(v2);
    }
    if (!strcmp(a2, "NiVkgJL3"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        VortexArenaFixedSettingsResponse::VortexArenaFixedSettingsResponse(v2);
    }
    if (!strcmp(a2, "KFJBwYbR"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        PvpFixedSettingMstResponse::PvpFixedSettingMstResponse(v2);
    }
    if (!strcmp(a2, "K4d9sl4s"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitOmniEvoMstResponse::UnitOmniEvoMstResponse(v2);
    }
    if (!strcmp(a2, "34d0Slkf"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitOmniEvoCostMstResponse::UnitOmniEvoCostMstResponse(v2);
    }
    if (!strcmp(a2, "Odks40sl"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitOmniEvoRecipeMstResponse::UnitOmniEvoRecipeMstResponse(v2);
    }
    if (!strcmp(a2, "DutHa7tA"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GlobalCampaignMessageResponse::GlobalCampaignMessageResponse(v2);
    }
    if (!strcmp(a2, "heP5upra"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FGPlusRankingReponse::FGPlusRankingReponse(v2);
    }
    if (!strcmp(a2, "P7uSpube"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        FGPlusBattleInfoResponse::FGPlusBattleInfoResponse(v2);
    }
    if (!strcmp(a2, "cr6qeFUP"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FGPlusPartyDeckInfoResponse::FGPlusPartyDeckInfoResponse(v2);
    }
    if (!strcmp(a2, "cexa3Uwr"))
    {
        v2 = (MessageResponse*)operator new(0x38uLL);
        FGPlusUserUnitInfoResponse::FGPlusUserUnitInfoResponse(v2);
    }
    if (!strcmp(a2, "spuPre2e"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        FGPlusSuspendedInfoResponse::FGPlusSuspendedInfoResponse(v2);
    }
    if (!strcmp(a2, "Che7rAre"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        FGPlusNumResponse::FGPlusNumResponse(v2);
    }
    if (!strcmp(a2, "heP5upra"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FrontierGateRankingResponse::FrontierGateRankingResponse(v2);
    }
    if (!strcmp(a2, "S2aTaxaP"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FGPlusGuildMstResponse::FGPlusGuildMstResponse(v2);
    }
    if (!strcmp(a2, "phusesW3"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FGPlusGuildRankingResponse::FGPlusGuildRankingResponse(v2);
    }
    if (!strcmp(a2, "s9d3lksS"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserGemShardInfoResponse::UserGemShardInfoResponse(v2);
    }
    if (!strcmp(a2, "T_POINT_EXCHANGE_INFO"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserPointExchnageInfoResponse::UserPointExchnageInfoResponse(v2);
    }
    if (!strcmp(a2, "T_USER_PAYMENT_INFO"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        UserPaymentInfoResponse::UserPaymentInfoResponse(v2);
    }
    if (!strcmp(a2, "olzMKWSZ"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        FrontierGateSgMstResponse::FrontierGateSgMstResponse(v2);
    }
    if (!strcmp(a2, "3aDk1xk7"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        ExcludedDungonMissionMstResponse::ExcludedDungonMissionMstResponse(v2);
    }
    if (!strcmp(a2, "fTV3A0By"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        DbbMstResponse::DbbMstResponse(v2);
    }
    if (!strcmp(a2, "sxorQ3Mb"))
    {
        v2 = (MessageResponse*)operator new(0x28uLL);
        UserUnitDbbInfoResponse::UserUnitDbbInfoResponse(v2, 1);
    }
    if (!strcmp(a2, "tR4katob"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UserUnitDbbLevelInfoResponse::UserUnitDbbLevelInfoResponse(v2);
    }
    if (!strcmp(a2, "keTrog0p"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        DbbBondRecipeMstResponse::DbbBondRecipeMstResponse(v2);
    }
    if (!strcmp(a2, "Y73mHKS8"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        PermitPlaceMLResponse::PermitPlaceMLResponse(v2);
    }
    if (!strcmp(a2, "IBs49NiH"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        GachaCategoryMstResponse::GachaCategoryMstResponse(v2);
    }
    if (!strcmp(a2, "sT4ph0fe"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        UserSubscriptionPurchaseInfoResponse::UserSubscriptionPurchaseInfoResponse(v2);
    }
    if (!strcmp(a2, "Drudr2w5"))
    {
        v2 = (MessageResponse*)operator new(0x18uLL);
        DailyLoginRewardsUserInfoResponse::DailyLoginRewardsUserInfoResponse(v2);
    }
    if (!strcmp(a2, "JukkSeNA"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitSelectorGachaMstResponse::UnitSelectorGachaMstResponse(v2);
    }
    if (!strcmp(a2, "CGHaOZda"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitSelectorGachaUserInfoResponse::UnitSelectorGachaUserInfoResponse(v2);
    }
    if (!strcmp(a2, "B8lchAPr"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        InteractiveBannerInfoResponse::InteractiveBannerInfoResponse(v2);
    }
    if (!strcmp(a2, "fr7p6EyU"))
    {
        v2 = (MessageResponse*)operator new(0x20uLL);
        UnitTypeBonusSkillMstResponse::UnitTypeBonusSkillMstResponse(v2);
    }
    return v2;
}
