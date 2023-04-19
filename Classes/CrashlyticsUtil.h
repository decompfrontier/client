#pragma once

class CrashlyticsUtil
{
public:
	static void crashlyticsCustomLog(std::string func, std::string message) noexcept;
	static void crashlyticsForceCrash() noexcept;
	static void crashlyticsSetUserIdentifier(std::string userid) noexcept;
	static void crashlyticsSetUserName(std::string username) noexcept;
};

#define CLOG(msg) CrashlyticsUtil::crashlyticsCustomLog(__func__, msg)
