#pragma once

#include "BaseResponse.h"

class DailyLoginRewardsUserInfoResponse : public BaseResponse
{
public:
	virtual bool readParam(int, int, const char* key, const char* value, bool isFirst) override;

};
