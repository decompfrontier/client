#pragma once

#include "BaseResponse.h"

class HeaderResponse : public BaseResponse
{
public:
	virtual bool readParam(int, int, const char* key, const char* value, bool) override;
	
	CC_SYNTHESIZE_READONLY(std::string, m_errorMsg, ErrorMsg);
};
