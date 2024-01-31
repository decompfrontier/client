#pragma once

class UserDebugInfo : public cocos2d::CCObject
{
	SHARED_SINGLETON(UserDebugInfo);

public:
	UserDebugInfo() = default;
	~UserDebugInfo() = default;

	CC_PROPERTY(bool, m_ageingFlag, AgeingFlg);
	CC_PROPERTY(bool, m_arenaTutoDebugFlag, ArenaTutoDebugFlg);
	CC_PROPERTY(bool, m_coloAuto, IsColoAuto);
	CC_PROPERTY(bool, m_tutoAllPlayFlag, TutoAllPlayFlg);
	CC_SYNTHESIZE(int, m_tutoChapterID, TutoChapterID);
	CC_PROPERTY(bool, m_tutoDebugFlag, TutoDebugFlg);
	CC_PROPERTY(bool, m_unitBattleCheckFlag, UnitBattleCheckFlg);
	CC_PROPERTY(bool, m_coloAgeing, IsColoAgeing);

	void setDebugMode(bool mode) { m_debugMode = mode; }
	bool isDebugMode() { return false; }
	bool isDebugMode(int) { return false; }

protected:
	bool m_debugMode;
};
