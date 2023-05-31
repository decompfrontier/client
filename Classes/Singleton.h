/*
	Simple definition for brave singletons

	THIS CODE IS __DECOMP__
*/
#pragma once

/*!
* Defines a shared singleton inside a class with it's creational body
* @param type class type
*/
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

/*!
* Declare a shared singleton inside a class without it's creational body
* @param type Class type
*/
#define SHARED_SINGLETON_DEF_ONLY(type) \
	private: \
		static type* type ## Instance; \
	public: \
		static type* shared(void);

/*!
* Sets the singleton instance to nullptr
* @param type Class type
*/
#define SET_SHARED_SINGLETON(type) \
	type* type::type ## Instance = nullptr;

/*!
* Signature of the shared singleton creation body
* @param type Class type
*/
#define SHARED_SINGLETON_BODY(type) \
	type* type::shared(void)

/*!
* Gets the name of a shared singleton instance
* @param type Class type
*/
#define SINGLETON_INSTANCE(type) \
	type ## Instance


/*!
* Creates a new shared singleton
* @param type Class type
*/
#define SINGLETON_NEW(type) \
	type ## Instance = new type ();
