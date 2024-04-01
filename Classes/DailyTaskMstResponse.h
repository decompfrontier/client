#pragma once

#include "BaseResponse.h"

class DailyTaskMstResponse : public BaseResponse
{
public:
	virtual bool readParam(int, int, const char* key, const char* value, bool) override;
};
