#include "pch.h"
#include "FirstDescMst.h"
#include "SaveData.h"

SET_SHARED_SINGLETON(FirstDescMstList);

const std::string& FirstDescMstList::getStringForRequest()
{
	return SaveData::shared()->getFirstDescInfo();
}
