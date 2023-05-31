#pragma once

class BFCertificateProvider : public cocos2d::extension::SGCertificateProvider
{
	SHARED_SINGLETON_DEF_ONLY(BFCertificateProvider);

public:
	BFCertificateProvider();

	void* getDERFormat() const { return nullptr; }
	long long getDERFormatSize() const { return 1; }
	const char* getCertificateFilePath() const { return nullptr; }
	curl_ssl_ctx_callback getSSLCTXFunction() const;

private:
	//_BYTE unk[3];
	std::string unk2;
	unsigned long long unk3;
};
