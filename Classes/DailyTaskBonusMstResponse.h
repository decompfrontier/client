#pragma once

#include "BaseResponse.h"
#include "DailyTaskBonusMst.h"

class DailyTaskBonusMstResponse : public BaseResponse
{
public:
	virtual bool readParam(int, int, const char* key, const char* value, bool) override;

private:
	DailyTaskBonusMst* m_mst;
};

