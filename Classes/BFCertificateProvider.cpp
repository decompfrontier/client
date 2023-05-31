#include "pch.h"
#include "BFCertificateProvider.h"

#include <openssl/pem.h>
#include <openssl/ssl.h>

static BIO* g_bfBIOcert = nullptr;
static X509* g_bfCertificate = nullptr;

static CURLcode bf_ssl_provider(CURL* curl, void* ssl_ctx, void* userptr)
{
	if (g_bfBIOcert && g_bfCertificate)
	{
		auto store = SSL_CTX_get_cert_store((const SSL_CTX*)ssl_ctx);
		if (store)
			X509_STORE_add_cert(store, g_bfCertificate);
	}

	return CURLE_OK;
}

SET_SHARED_SINGLETON(BFCertificateProvider);

/* not 100% the same as original but who cares... */
SHARED_SINGLETON_BODY(BFCertificateProvider)
{
	if (!SINGLETON_INSTANCE(BFCertificateProvider))
	{
		SINGLETON_NEW(BFCertificateProvider);

		if (g_bfCertificate)
		{
			X509_free(g_bfCertificate),
			g_bfCertificate = nullptr;
		}

		if (g_bfBIOcert)
		{
			BIO_free(g_bfBIOcert);
			g_bfBIOcert = nullptr;
		}

		g_bfBIOcert = BIO_new_mem_buf(API_CA_CERT, -1);
		PEM_read_bio_X509(g_bfBIOcert, &g_bfCertificate, nullptr, nullptr);

	}
}

BFCertificateProvider::BFCertificateProvider()
{
	unk3 = CommonUtils::getNowUnitxTime() + 10;
}

curl_ssl_ctx_callback BFCertificateProvider::getSSLCTXFunction() const
{
	return bf_ssl_provider;
}
