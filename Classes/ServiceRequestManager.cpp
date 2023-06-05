#include "pch.h"
#include "ServiceRequestManager.h"
#include "SaveUtils.h"

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
