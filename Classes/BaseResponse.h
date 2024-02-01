#pragma once

#include "ResponseData.h"

class BaseResponse : public ResponseData
{
public:
	BaseResponse() : m_error(false) {}
	virtual ~BaseResponse() = default;

	virtual bool readParam(int, int, const char* key, const char* value, bool isFirst) = 0;

	CC_SYNTHESIZE_READONLY(bool, m_error, IsError);
};

