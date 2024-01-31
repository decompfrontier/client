#pragma once

#include "ResponseData.h"

class BaseResponse : public ResponseData
{
public:
	BaseResponse() = default;
	virtual ~BaseResponse() = default;

	virtual bool readParam(int, int, const char* key, const char* value, bool) = 0;
};
