#include "pch.h"
#include "GumiLiveManager.h"
#include "Utils.h"
#include "PWConstants.h"

void GumiLiveManager::appendLoginCredentials(std::string& ret)
{
	ret += Utils::getDeviceModel();
	ret += ",";
	ret += Utils::getDevicePlatform();
	ret += ",";
	ret += Utils::getDeviceVID();
	ret += ",";
	ret += Utils::getDeviceOS();
	ret += ",";
	ret += ?;//unk!
	ret += ",";
	ret += cocos2d::CCUserDefault::sharedUserDefault()->getStringForKey(PWConstants::DEVICE_ID);
	ret += ",";
	ret += Utils::getDeviceAltVid();
	ret += ",";
	ret += str_unk2; // unk2!
	ret += ",";
	ret += str_unk; // unk3!,
}