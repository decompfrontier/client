#include "pch.h"
#include "ServiceRequestManager.h"
#include "SaveUtils.h"
#include "UserInfo.h"

#include <openssl/hmac.h>
#include <openssl/aes.h>
#include <openssl/buffer.h>

SET_SHARED_SINGLETON(ServiceRequestManager);

/* not 100% the same as original but who cares... */
SHARED_SINGLETON_BODY(ServiceRequestManager)
{
	if (!SINGLETON_INSTANCE(ServiceRequestManager))
	{
		SINGLETON_NEW(ServiceRequestManager);

		auto s = SINGLETON_INSTANCE(ServiceRequestManager);

		s->landingPageKey = SaveUtils::loadUserDefaults("DYNAMIC_LOADING_PAGE_KEY");
	}

	return SINGLETON_INSTANCE(ServiceRequestManager);
}

std::string ServiceRequestManager::hmacHashing(const std::string& data)
{
	HMAC_CTX* ctx = HMAC_CTX_new();
	const EVP_MD* evp = EVP_sha256();

	HMAC_Init_ex(ctx, GUMILIVE_HMAC_KEY, strlen(GUMILIVE_HMAC_KEY), evp, nullptr);

	HMAC_Update(ctx, (const unsigned char*)data.data(), data.size());

	unsigned char hmac_data[32];
	unsigned int len = 32;
	HMAC_Final(ctx, hmac_data, &len);

	char hex_buffer[512];

	for (unsigned int i = 0, char* it = hex_buffer; i < len; i++, it += 2)
	{
		sprintf(it, "%02x", hmac_data[i]);
	}

	return hex_buffer;
}

void ServiceRequestManager::encodeTobase64(char* in, int in_len, char* out)
{
	if (in && out)
	{
		auto method = BIO_f_base64();
		auto bioMethod = BIO_new(method);
		auto src = BIO_s_mem();
		auto bioSrc = BIO_new(src);
		auto b64 = BIO_push(bioMethod, bioSrc);
		BIO_write(b64, in, in_len);
		BIO_flush(b64);

		BUF_MEM* pp;
		BIO_get_mem_ptr(b64, &pp);
		memcpy(out, pp->data, pp->length);
		out[pp->length] = '\0';

		BIO_free_all(b64);
	}
}

std::string ServiceRequestManager::encryptIdentifiersToServer(const std::string& data)
{
	AES_KEY key;
	if (AES_set_encrypt_key((const unsigned char*)GUMILIVE_IDENTIFIER_AES_KEY, 128, &key) >= 0)
	{
		unsigned char aes_out[512];
		AES_cbc_encrypt((const unsigned char*)data.data(), aes_out, data.size(), &key, (unsigned char*)GUMILIVE_IDENTIFIER_IV, 1);

		char b64_out[512];
		encodeTobase64((char*)aes_out, data.size(), b64_out);

		// DECOMP TODO: there's something missing here? verify if it's correct!

		return b64_out;
	}

	return "";
}

cocos2d::CCDictionary* ServiceRequestManager::constructParameters()
{
	auto dict = cocos2d::CCDictionary::create();
	auto currTime = CommonUtils::LongLongToString(CommonUtils::getNowUnitxTime());
	auto hmacHash = hmacHashing(currTime);

	std::string req = "OS=";
	req += CommonUtils::IntToString(CommonUtils::getTargetOs());
	req += "&APP_VERSION=";
	req += CommonUtils::IntToString(APP_VERSION);

	auto ep = UserInfo::shared()->getServiceRequestEndpointParam();
	if (!ep.empty())
	{
		req += "&EXTRA_PARAM=";
		req += ep;
	}

	auto encrypt = encryptIdentifiersToServer(req);
	
	auto ccEncrypt = cocos2d::CCString::create(encrypt);
	dict->setObject(ccEncrypt, "\nPARAM");

	auto ccCurr = cocos2d::CCString::create(currTime);
	dict->setObject(ccCurr, "\nTOKEN");

	auto ccHmac = cocos2d::CCString::create(hmacHash);
	dict->setObject(ccHmac, "SECRET");

	return dict;
}
