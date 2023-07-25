#pragma once

constexpr auto DEFAULT_KEY_CHAIN = "BraveFrontier";

class SaveUtils
{
public:
	static std::string loadUserDefaults(std::string key);
	static void saveUserDefaults(std::string key, std::string value);
	static void saveKeyChain(const char* keychainName, std::string name, std::string pass);
	static void loadKeyChain(const char* keychainName);
	static std::string getPasswordFromKeyChain(const char* keychainName);
	static std::string getAccountNameFromKeyChain(const char* keychainName);
	static void deleteKeyChain(std::string keychainName, const char*);
};
