#include "pch.h"
#include "Utils.h"
#include "GameLayer.h"

void Utils::showLoadingScreen(cocos2d::extension::CCHttpRequest* req, bool, cocos2d::CCLayer*, bool)
{
	if (req)
	{
		cocos2d::extension::CCHttpClient::getInstance()->send(req);
	}

	auto gl = GameLayer::shared();

	/* TODO... */
}
