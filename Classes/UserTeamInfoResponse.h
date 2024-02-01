#pragma once

#include "BaseResponse.h"

class UserTeamInfoResponse : public BaseResponse
{
public:
	virtual bool readParam(int, int, const char* key, const char* value, bool) override;
};
