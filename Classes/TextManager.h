#pragma once

class TextManager : public cocos2d::CCObject
{
	SHARED_SINGLETON(TextManager);

public:
	TextManager();

	std::string getFileName();
	std::string getMstText(const std::string&, const std::string&);
	std::string getText(const char* key);
	std::string getText(const std::string&);
	void loadCsv(cocos2d::CCMutableArray<cocos2d::CCString*>);
	void loadCsvList(std::string);
	void loadCsvList(void);
	void reload();
};
