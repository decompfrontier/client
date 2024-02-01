#include "pch.h"
#include "SignalKeyResponse.h"
#include "UserInfo.h"

bool SignalKeyResponse::readParam(int, int, const char* key, const char* value, bool)
{
	if (!strcmp(key, SIGNALKEY_VALUE))
		return true;

	UserInfo::shared()->setSignalKey(value);
	return true;
}
