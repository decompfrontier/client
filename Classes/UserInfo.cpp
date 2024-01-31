#include "pch.h"
#include "UserInfo.h"
#include "SaveData.h"

std::string UserInfo::getServiceRequestEndpointParam() const
{
	return SaveData::shared()->getServiceRequestEndpoint();
}