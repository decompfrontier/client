#pragma once

class AIMst : public cocos2d::CCObject
{
    CC_SYNTHESIZE(int, priority, Priority);
    CC_SYNTHESIZE(std::string, aiId, AiID);
};
