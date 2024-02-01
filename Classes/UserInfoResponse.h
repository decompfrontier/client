#pragma once

#include "BaseResponse.h"

class UserInfoResponse : public BaseResponse
{
public:
	virtual bool readParam(int, int, const char* key, const char* value, bool isFirst) override;
};
