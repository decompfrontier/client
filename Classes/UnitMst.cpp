#include "pch.h"
#include "UnitMst.h"
#include "CommonUtils.h"
#include "TextManager.h"

std::string UnitMst::getAtkStartVoiceFile1() { return m_atkStartVoice1; }
std::string UnitMst::getAtkStartVoiceFile2() { return m_atkStartVoice2; }
std::string UnitMst::getAtkVoiceFile1() { return m_atkVoice1; }
std::string UnitMst::getAtkVoiceFile2() { return m_atkVoice2; }
std::string UnitMst::getBbCutinVoiceFile() { return m_bbCutinVoice; }
std::string UnitMst::getBbVoiceFile() { return m_bbVoice; }
std::string UnitMst::getDamageVoiceFile() { return m_damageVoice; }

std::string UnitMst::getConfirmImgPos(int imgId)
{
	if (imgId > 1)
		return m_extConfirmImgPos;

	return m_confirmImgPos;
}

std::string UnitMst::getDetailImgPos(int imgId)
{
	if (imgId > 1)
		return m_extDetailImgPos;

	return m_detailImgPos;
}

std::string UnitMst::getHomeImgPos(int imgId)
{
	if (imgId > 1)
		return m_extHomeImgPos;

	return m_homeImgPos;
}

std::string UnitMst::getSkillCutinImgPos(int imgId)
{
	if (imgId > 1)
		return m_extSkillCutinImgPos;

	return m_skillCutinImgPos;
}

std::string UnitMst::getSummonImgPos(int imgId)
{
	if (imgId > 1)
		return m_extSummonImgPos;

	return m_summonImgPos;
}

std::string UnitMst::getDescription()
{
	std::string idStr = CommonUtils::IntToString((m_idXor ^ m_idMagic));
	std::string text = "MST_UNIT_";
	text += idStr;
	text += "_DESC";

	TextManager::shared()->getMstText(text, m_desc);
	return m_desc;
}

std::string UnitMst::getUnitName()
{
	std::string unitId = getUnitID();
	std::string text = "MST_UNIT_";
	text += unitId;
	text += "_NAME";

	TextManager::shared()->getMstText(text, m_unitName);
	return m_unitName;
}

std::string UnitMst::getUnitID() { return CommonUtils::IntToString(m_idXor ^ m_idMagic); }
void UnitMst::setConfirmImgPos(std::string p) { m_confirmImgPos = p; }
void UnitMst::setDescription(std::string p) { m_desc = p; }
void UnitMst::setAtkStartVoiceFile1(std::string p) { if (p != "none") m_atkStartVoice1 = p; }
void UnitMst::setAtkStartVoiceFile2(std::string p) { if (p != "none") m_atkStartVoice2 = p; }
void UnitMst::setAtkVoiceFile1(std::string p) { if (p != "none") m_atkVoice1 = p; }
void UnitMst::setAtkVoiceFile2(std::string p) { if (p != "none") m_atkVoice2 = p; }
void UnitMst::setBbCutinVoiceFile(std::string p) { if (p != "none") m_bbCutinVoice = p; }
void UnitMst::setBbVoiceFile(std::string p) { if (p != "none") m_bbVoice = p; }
void UnitMst::setDamageVoiceFile(std::string p) { if (p != "none") m_damageVoice = p; }

void UnitMst::setUnitID(std::string id)
{
	int idint = CommonUtils::StrToInt(id);

	int32_t rng = 0;
	do rng = RANDOM_FUNC(); while (rng < 0);
	rng = (rng % 233) + 2;

	m_idMagic = rng;
	m_idXor = idint ^ rng;
}

cocos2d::CCPoint UnitMst::getMoveOffsetPoint()
{
	std::vector<std::string> x = CommonUtils::parseList(m_moveOffset, ",");

	if (x.size() >= 2)
	{
		cocos2d::CCPoint out(CommonUtils::StrToFloat(x[0].c_str()), CommonUtils::StrToFloat(x[1].c_str()));
		return out;
	}

	return cocos2d::CCPoint();
}

void UnitMst::parseParamMax(const std::string& param)
{
	m_extHp = 0;
	m_extAtk = 0;
	m_extDef = 0;
	m_extHel = 0;

	std::vector<std::string> p = CommonUtils::parseList(param, ":");

	if (p.size() > 0)
		m_extHp = CommonUtils::StrToInt(p[0]);

	if (p.size() > 1)
		m_extAtk = CommonUtils::StrToInt(p[1]);

	if (p.size() > 2)
		m_extDef = CommonUtils::StrToInt(p[2]);

	if (p.size() > 3)
		m_extHel = CommonUtils::StrToInt(p[3]);
}
