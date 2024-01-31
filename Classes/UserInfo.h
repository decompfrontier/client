#pragma once

class UserInfo
{
	SHARED_SINGLETON(UserInfo);

public:
	std::string getServiceRequestEndpointParam() const;
};
