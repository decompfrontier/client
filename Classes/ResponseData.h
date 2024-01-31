#pragma once

class ResponseData : public cocos2d::CCObject
{
public:
	virtual ~ResponseData() = default;
	virtual void onReadFinished() {}
};
