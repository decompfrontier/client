#pragma once

#include "BaseResponse.h"
#include "UnitMst.h"

class UnitMstResponse : public BaseResponse
{
public:
	virtual bool readParam(int, int, const char* key, const char* value, bool) override;

private:
	UnitMst* m_unit;
};
