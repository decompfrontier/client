#pragma once

class GumiLiveNetworkManagement
{
public:
	GumiLiveNetworkManagement() = default;

	void networkRequestAppleGumiLiveExistData(cocos2d::CCString*, cocos2d::CCString*);

private:
	std::string appKey;
	_BYTE unk[0x10];
};
