#pragma once

#include "BaseResponse.h"

class VersionInfoResponse : public BaseResponse
{
public:
	virtual bool readParam(int, int, const char* key, const char* value, bool) override;
};
