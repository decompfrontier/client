#include "pch.h"
#include "NetworkManager.h"

NetworkManager* NetworkManager::instance = nullptr;

NetworkManager* NetworkManager::sharedInstance()
{
	if (!instance)
	{
		instance = new NetworkManager();
	}

	return instance;
}

NetworkManager::NetworkManager()
{

}