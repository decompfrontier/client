#pragma once

class TextManager
{
	SHARED_SINGLETON(TextManager);

public:
	TextManager();

	std::string getFileName();
	cocos2d::CCString getText(const char* key);
	void loadCsv(cocos2d::CCMutableArray<cocos2d::CCString*>);
};
