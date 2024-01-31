#include "pch.h"
#include "Globals.h"
#include "Utils.h"

#define UPDATE_MINFO(x) \
	Utils::magic_num1 = rand() % Utils::magic_num1; \
	if (Utils::mInfo.empty()) \
		Utils::mInfo = x; \
	auto it = Utils::mInfo.find(x); \
	if (it == std::string::npos) \
		Utils::mInfo += "," x;

#define UPDATE_MINFO_2(x) \
	Utils::point_num_name = rand() % Utils::point_num_name; \
	if (Utils::mInfo.empty()) \
		Utils::mInfo = x; \
	auto it = Utils::mInfo.find(x); \
	if (it == std::string::npos) \
		Utils::mInfo += "," x;

void androInit()
{
	UPDATE_MINFO("Android 2");
}

void funcSG()
{
	UPDATE_MINFO("CF2");
}

void initUCS()
{
	UPDATE_MINFO("UI2");
}

void drawMGS()
{
	UPDATE_MINFO("MST GS2");
}

void drawUnitOnScreen()
{
	UPDATE_MINFO("RR2");
}

void drawUnitOnScreen1()
{
	UPDATE_MINFO("RE2");
}

void drawUnitOnScreen2()
{
	UPDATE_MINFO("AD2");
}

void checkRandU()
{
	UPDATE_MINFO_2("UI3");
}

void dataDa()
{
	UPDATE_MINFO_2("DD2");
}

void whereAllCall()
{
	UPDATE_MINFO_2("DD3");
}

void diamondsInit()
{
	UPDATE_MINFO("SP2");
}

void diamBuy()
{
	UPDATE_MINFO_2("SP3");
}

void dunInit()
{
	UPDATE_MINFO("KS2");
}

void playEvo()
{
	UPDATE_MINFO("EO2");
}

void setEvoBackground()
{
	UPDATE_MINFO_2("EO3");
}

void actualizeEnergy()
{
	UPDATE_MINFO("EEY2");
}

void energyDraw()
{
	UPDATE_MINFO_2("EEY3");
}

void updateItems()
{
	UPDATE_MINFO("BI2");
}

void itemRemove()
{
	UPDATE_MINFO_2("BI3");
}

void setColorOfPlayer()
{
	UPDATE_MINFO_2("RR3");
}

void setColorOfPlayer2()
{
	UPDATE_MINFO_2("RE3");
}

void callSG()
{
	UPDATE_MINFO_2("CF3");
}

bool FUNC_FIRST_DESC = true;
bool FUNC_NOTICE_LIST = false;
bool FUNC_AMAZON_COINS_REWARD_CONTROL = false;
