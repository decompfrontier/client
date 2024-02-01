#pragma once

#include "BaseRequest.h"

class GetUserInfoRequest : public BaseRequest
{
public:
	const char* getRequestID() const override { return REQUEST_GETUSERINFO; }
	const char* getEncodeKey() const override { return AESKEY_GETUSERINFO; }
	const char* getUrl() const override { return ACTIONURL_GETUSERINFO; }
	void disableCompression();
	void createBody() override;
};
