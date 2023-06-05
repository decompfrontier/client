#pragma once

class ServiceRequestManager : public cocos2d::CCObject
{
	SHARED_SINGLETON(ServiceRequestManager);

public:
	std::string getServiceEndpoint(std::string name, std::string unused);

private:
	_BYTE unk2[35];
	std::string landingPageKey;
	_BYTE unk[5];
};
