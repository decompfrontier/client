#pragma once

class GumiLiveManager : public cocos2d::CCObject
{
	SHARED_SINGLETON(GumiLiveManager);

public:
	void appendLoginCredentials(std::string& ret);

private:
	_BYTE unk114[3];
	int unk44;
	_BYTE unk45[4];
	int unk66;
	_BYTE unk2[12];
	std::string str_unk;
	std::string str_unk2;
	std::string unk5;
	std::string unk6;
	std::string unk7;
	std::string unk8;
	int unk9;
	std::string unk10;
	std::string unk11;

};
