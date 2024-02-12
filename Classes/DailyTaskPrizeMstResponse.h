#pragma once

#include "BaseResponse.h"
#include "DailyTaskPrizeMst.h"

class DailyTaskPrizeMstResponse : public BaseResponse
{
public:
	virtual bool readParam(int, int, const char* key, const char* value, bool isFirst) override;

private:
	DailyTaskPrizeMst* m_mst;
};
