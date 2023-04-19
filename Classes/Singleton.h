/*
	Simple definition for brave singletons

	THIS CODE IS __DECOMP__
*/
#pragma once

#define SHARED_SINGLETON(type) \
	private: \
		static type* type ## Instance; \
	public: \
		static type* shared(void) { \
			if (!type ## Instance) { \
				type ## Instance = new type(); \
			} \
			return type ## Instance; \
		}

#define SET_SHARED_SINGLETON(type) \
	type* type::type ## Instance = nullptr;
