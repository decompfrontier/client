#pragma once

#include "BaseResponse.h"

class SignalKeyResponse : public BaseResponse
{
public:
	virtual bool readParam(int, int, const char* key, const char* value, bool) override;
};
