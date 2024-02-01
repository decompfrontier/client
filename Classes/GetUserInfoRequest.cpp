#include "pch.h"
#include "GetUserInfoRequest.h"

void GetUserInfoRequest::disableCompression()
{
	auto g = replaceGroup(GROUP_GETUSERINFO);
	auto n = g->addNode();
	n->addParam(GETUSERINFO_ENABLECOMPRESSION, "0");
}

void GetUserInfoRequest::createBody()
{
	createUserInfoTag();
	createSignalKeyTag();
	createVersionTag();
	auto g = addGroup(GROUP_GETUSERINFO);
	auto n = g->addNode();
	n->addParam(GETUSERINFO_ENABLECOMPRESSION, "1");
}
