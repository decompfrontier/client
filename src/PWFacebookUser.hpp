#pragma once

class PWFacebookUser : cocos2d::CCObject
{
    enum STATUS
    {

    };

public:
    CC_SYNTHESIZE(std::string, userId, UserID);
    CC_SYNTHESIZE(std::string, fullName, FullName);
    CC_SYNTHESIZE(long, requestTime, RequestTime);
    CC_SYNTHESIZE(PWFacebookUser::STATUS, status, Status);
    CC_SYNTHESIZE(std::string, handleName, HandleName);
    CC_SYNTHESIZE(std::string, unitId, UnitID);
    CC_SYNTHESIZE(std::string, friendId, FriendID);
    CC_SYNTHESIZE(std::string, level, Level);
};
