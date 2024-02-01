#include "pch.h"
#include "FriendGetRequest.h"
#include "Globals.h"

void FriendGetRequest::createBody()
{
	createUserInfoTag();
	createSignalKeyTag();
	createVersionTag();

	if (FUNC_SUMMONER_UNIT)
	{
		auto p = addGroup(GROUP_FRIENDGET);
		auto n = p->addNode();
		n->addParam(FRIENDGET_REINTYPE, m_reinType);
	}
}
