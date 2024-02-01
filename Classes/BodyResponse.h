#pragma once

#include "BaseResponse.h"

class BodyResponse : public BaseResponse
{
public:
	virtual bool readParam(int, int, const char* key, const char* value, bool) override
	{
		if (!strcmp(key, BODY_ENCODED_DATA))
			m_encodedData = value;

		return true;
	}

	CC_SYNTHESIZE_READONLY(std::string, m_encodedData, EncodeData);
};
