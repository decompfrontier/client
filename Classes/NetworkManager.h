#pragma once

class NetworkManager : public cocos2d::CCObject
{
public:
	NetworkManager();
	~NetworkManager();

	// singleton
	static NetworkManager* sharedInstance();

private:
	char unk2[27];
	std::string apiUrl;
	char unk[109];

	static NetworkManager* instance;
};
