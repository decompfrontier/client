#pragma once

#include "RequestData.h"

class GameResponseParser : public cocos2d::CCObject
{
	SHARED_SINGLETON(GameResponseParser);

public:
	void parseTag(const std::map<std::string, picojson::value>& json, const char* key, const char* aesKey);
	void parseDummy(const char*);
	void parseBodyTag(const std::map<std::string, picojson::value>&, const char*, const char* );
	void parse(const char*, const char*);
	void parse(const char*, RequestData* data);
	void getResponseObject(const char*);
	void addParseResponseAsyncCallback(float);
	void addParseResponseAsync(const char*, RequestData* data);
};
