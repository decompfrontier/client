#pragma once

#include "BaseRequest.h"

class FriendGetRequest : public BaseRequest
{
public:
	CC_SYNTHESIZE(int, m_reinType, ReinType);

	const char* getRequestID() const override { return REQUEST_FRIENDGET; }
	const char* getEncodeKey() const override { return AESKEY_GETFRINEDREQUEST; }
	const char* getUrl() const override { return ACTIONURL_FRIENDGET; }
	void createBody() override;
};
