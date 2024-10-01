#pragma once

class UnitMst : public cocos2d::CCObject
{
public:
	CCX_SYNTHESIZE(int, m_adjustExp, AdjustExp);
	CC_SYNTHESIZE(std::string, m_aiID, AiID);
	CCX_SYNTHESIZE(int, m_amountMix, AmountMix);
	CCX_SYNTHESIZE(int, m_amountSell, AmountSell);
	CC_SYNTHESIZE(std::string, m_anmCgg, AnmCgg);
	CCX_SYNTHESIZE(int, m_arousal, Arousal);
	CC_PROPERTY(std::string, m_atkStartVoice1, AtkStartVoiceFile1); // get is normal, set check for "none"
	CC_PROPERTY(std::string, m_atkStartVoice2, AtkStartVoiceFile2); // get is normal, set check for "none"
	CC_PROPERTY(std::string, m_atkVoice1, AtkVoiceFile1); // get is normal, set check for "none"
	CC_PROPERTY(std::string, m_atkVoice2, AtkVoiceFile2); // get is normal, set check for "none"
	CC_SYNTHESIZE(int, m_atkMoveType, AttackMoveType);
	CC_SYNTHESIZE(int, m_backMoveType, BackMoveType);
	CC_SYNTHESIZE(std::string, m_badStateResists, BadStateResists);
	CC_PROPERTY(std::string, m_bbCutinVoice, BbCutinVoiceFile); // get is normal, set check for "none"
	CC_PROPERTY(std::string, m_bbVoice, BbVoiceFile); // get is normal, set check for "none"
	CC_SYNTHESIZE(int, m_categoryNo, CategoryNo);
	CC_SYNTHESIZE(int, m_cautionType, CautionType);

	void setConfirmImgPos(std::string p) { m_confirmImgPos = p; }
	std::string getConfirmImgPos(int imgId);
	
	CCX_SYNTHESIZE(int, m_cost, Cost);
	CC_SYNTHESIZE(std::string, m_cursorDispPos, CursorDispPos);
	CC_SYNTHESIZE(std::string, m_damageFrame, DamageFrame);
	CC_PROPERTY(std::string, m_damageVoice, DamageVoiceFile); // get is normal, set check for "none"
	CC_PROPERTY(std::string, m_desc, Description);

	void setDetailImgPos(std::string p) { m_detailImgPos = p; }
	std::string getDetailImgPos(int imgId);

	CC_SYNTHESIZE(int, m_dispDictFlg, DispDictFlg);
	CC_SYNTHESIZE(int, m_dispOrder, DispOrder);
	CCX_SYNTHESIZE(int, m_dropCheckCnt, DropCheckCnt);
	CC_SYNTHESIZE(std::string, m_effectFrame, EffectFrame);
	CC_SYNTHESIZE(int, m_element, Element);
	CC_SYNTHESIZE(int, m_expPatternID, ExpPatternID);
	CC_SYNTHESIZE_READONLY(int, m_extAtk, ExtAtk);
	CC_SYNTHESIZE(std::string, m_extConfirmImgPos, ExtConfirmImgPos);
	CC_SYNTHESIZE_READONLY(int, m_extDef, ExtDef);
	CC_SYNTHESIZE(std::string, m_extDetailImgPos, ExtDetailImgPos);
	CC_SYNTHESIZE_READONLY(int, m_extHel, ExtHel);
	CC_SYNTHESIZE(std::string, m_extHomeImgPos, ExtHomeImgPos);
	CC_SYNTHESIZE_READONLY(int, m_extHp, ExtHp);
	CC_SYNTHESIZE(std::string, m_extSkillCutinImgPos, ExtSkillCutinImgPos);
	CC_SYNTHESIZE(std::string, m_extSummonImgPos, ExtSummonImgPos);
	CC_SYNTHESIZE(std::string, m_extraPassiveSkillID, ExtraPassiveSkillID);
	CC_SYNTHESIZE(std::string, m_extraSkillID, ExtraSkillID);
	CC_SYNTHESIZE(int, m_gettinType, GettingType);

	std::string getHomeImgPos(int imgId);
	void setHomeImgPos(std::string p) { m_homeImgPos = p; }

	CC_SYNTHESIZE(std::string, m_hpDispPos, HpDispPos);
	CC_SYNTHESIZE(int, m_kind, Kind);
	CC_SYNTHESIZE(std::string, m_leaderSkillID, LeaderSkillID);
	CCX_SYNTHESIZE(int, m_maxAtk, MaxAtk);
	CCX_SYNTHESIZE(int, m_maxDef, MaxDef);
	CCX_SYNTHESIZE(int, m_maxHel, MaxHel);
	CCX_SYNTHESIZE(int, m_maxHp, MaxHp);
	CC_SYNTHESIZE(int, m_maxLv, MaxLv);
	CCX_SYNTHESIZE(int, m_minAtk, MinAtk);
	CCX_SYNTHESIZE(int, m_minDef, MinDef);
	CCX_SYNTHESIZE(int, m_minHel, MinHel);
	CCX_SYNTHESIZE(int, m_minHp, MinHp);
	CC_SYNTHESIZE(std::string, m_moveOffset, MoveOffset);
	
	cocos2d::CCPoint getMoveOffsetPoint();

	CC_SYNTHESIZE(int, m_moveSpeed, MoveSpeed);
	CC_SYNTHESIZE(int, m_overDriveIncCost, OverDriveIncCost);
	CC_SYNTHESIZE(int, m_overDriveUpAtk, OverDriveUpAtk);
	CC_SYNTHESIZE(int, m_overDriveUpDef, OverDriveUpDef);
	CC_SYNTHESIZE(int, m_overDriveUpHeal, OverDriveUpHeal);
	CC_SYNTHESIZE(std::string, m_paramMax, ParamMax);

	std::string getRare(std::string);

	CC_SYNTHESIZE(int, m_rare, Rare);
	CC_SYNTHESIZE(int, m_sex, Sex);

	std::string getSkillCutinImgPos(int imgId);
	void setSkillCutinImgPos(std::string p) { m_skillCutinImgPos = p; }
	std::string getSkillCutinImgPosDef() { return m_skillCutinImgPos; }

	CC_SYNTHESIZE(std::string, m_skillID, SkillID);
	CC_SYNTHESIZE(int, m_skillMoveType, SkillMoveType);
	CC_SYNTHESIZE(int, m_skillupAdjust, SkillupAdjust);

	std::string getSummonImgPos(int imgId);
	void setSummonImgPos(std::string p) { m_summonImgPos = p; }
	std::string getSkillCutinImgPosDef() { return m_skillCutinImgPos; }

	CC_SYNTHESIZE(int, m_tribe, Tribe);
	CC_SYNTHESIZE(std::string, m_ultimateSkillID, UltimateSkillID);

	std::string getUnitID();
	void setUnitID(std::string id);

	std::string getUnitName();
	void setUnitName(std::string p) { m_unitName = p; }

	void parseParamMax(const std::string& param);

protected:
	int m_idXor;
	uint8_t m_idMagic;
	std::string m_confirmImgPos;
	std::string m_detailImgPos;
	std::string m_homeImgPos;
	std::string m_skillCutinImgPos;
	std::string m_summonImgPos;
	std::string m_unitName;
};
