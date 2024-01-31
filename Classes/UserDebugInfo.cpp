#include "pch.h"
#include "UserDebugInfo.h"

SET_SHARED_SINGLETON(UserDebugInfo);

#ifndef _DEBUG
bool UserDebugInfo::getAgeingFlg() { return false; }
bool UserDebugInfo::getArenaTutoDebugFlg() { return false; }
bool UserDebugInfo::getIsColoAuto() { return false; }
bool UserDebugInfo::getTutoAllPlayFlg() { return false; }
bool UserDebugInfo::getTutoDebugFlg() { return false; }
bool UserDebugInfo::getUnitBattleCheckFlg() { return false; }
bool UserDebugInfo::getIsColoAgeing() { return false; }
#else
bool UserDebugInfo::getAgeingFlg() { return m_ageingFlag; }
bool UserDebugInfo::getArenaTutoDebugFlg() { return m_arenaTutoDebugFlag; }
bool UserDebugInfo::getIsColoAuto() { return m_coloAuto; }
bool UserDebugInfo::getTutoAllPlayFlg() { return m_tutoAllPlayFlag; }
bool UserDebugInfo::getTutoDebugFlg() { return m_tutoDebugFlag; }
bool UserDebugInfo::getUnitBattleCheckFlg() { return m_unitBattleCheckFlag; }
bool UserDebugInfo::getIsColoAgeing() { return m_coloAgeing; }
#endif

void UserDebugInfo::setAgeingFlg(bool v) { m_ageingFlag = v; }
void UserDebugInfo::setArenaTutoDebugFlg(bool v) { m_arenaTutoDebugFlag = v; }
void UserDebugInfo::setIsColoAuto(bool v) { m_coloAuto = v; }
void UserDebugInfo::setTutoAllPlayFlg(bool v) { m_tutoAllPlayFlag = v; }
void UserDebugInfo::setTutoDebugFlg(bool v) { m_tutoDebugFlag = v; }
void UserDebugInfo::setUnitBattleCheckFlg(bool v) { m_unitBattleCheckFlag = v; }
void UserDebugInfo::setIsColoAgeing(bool v) { m_coloAgeing = v; }
