#pragma once

#include "RequestData.hpp"

class GameResponseParser : public cocos2d::CCObject
{
public:
    static GameResponseParser *shared()
    {
        GameResponseParser *parser = responseParserInstance;
        if (!parser)
        {
            parser = new GameResponseParser();
            responseParserInstance = parser;
        }
    
        return parser;
    }

    virtual ~GameResponseParser() {}

    virtual void parse(const char *, RequestData *);
    virtual void parse(const char *, const char *);
    virtual void addParseResponseAsyncCallback(float);

    void parseTag(std::map<std::string, picojson::value>& kv, const char *, const char *);
    void parseBodyTag(std::map<std::string, picojson::value>& kv, const char *);
    void getResponseObject(const char *);
    void parseDummy(const char *);

private:
    static GameResponseParser *responseParserInstance;
};
