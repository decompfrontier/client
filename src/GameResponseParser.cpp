#include "Pch.hpp"
#include "GameResponseParser.hpp"

#if 0
#include "HeaderResponse.hpp"
#include "BodyResponse.hpp"
#endif

GameResponseParser *GameResponseParser::responseParserInstance = NULL;

#if 0
void parseTag(std::map<std::string, picojson::value>& kv, const char *key, const char *)
{
    ResponseData *currentResp;

    if (!strcmp(key, "F4q6i9xe")) // GME Header
    {
        currentResp = new HeaderResponse();
    }
    else
    {
        currentResp = NULL;
    }

    if (!strcmp(key, "a3vSYuq2")) // GME Body
    {
        currentResp = new BodyResponse();
    }

    if (!strcmp(key, "b5PH6mZa"))
    {
        currentResp = new MessageResponse();
    }

    currentResp->autorelease();
    const std::map<std::string, picojson::value>::iterator it = kv.find(key);
    if (it != kv.end())
    {

    }
    
}
#endif
