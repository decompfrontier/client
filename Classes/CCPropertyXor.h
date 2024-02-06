#pragma once

/*
* This are macros of CC_PROPERTY/CC_SYNTHESIZE
* but with the XOR variant so that we can
* mimic the functionality of BF
* 
* NOTE: keep in mind that this is not 100% the
* same as the official decomp but we don't care
* as we care more for fast prototyping
*/

#define CCX_PROPERTY_READONLY(varType, varName, funName)\
protected: varType varName##Xor; uint8_t varName##Magic; \
public: virtual varType get##funName(void) const;

#define CCX_PROPERTY(varType, varName, funName)\
protected: varType varName##Xor; uint8_t varName##Magic; \
public: virtual varType get##funName(void) const;\
public: virtual void set##funName(varType var);

#define CCX_SYNTHESIZE(varType, varName, funName)\
protected: varType varName##Xor; uint8_t varName##Magic; \
public: virtual varType get##funName(void) const { return varName##Xor ^ varName##Magic; }\
public: virtual void set##funName(varType var){\
	int32_t rng = 0; \
	do rng = RANDOM_FUNC(); while (rng < 0); \
	rng = (rng % 233) + 2; \
	varName##Xor = rng ^ var; \
	varName##Magic = rng; \
}

#define CCX_SYNTHESIZE_READONLY(varType, varName, funName)\
protected: varType varName##Xor; uint8_t varName##Magic; \
public: virtual varType get##funName(void) const { return varName##Xor ^ varName##Magic; }

#define CCX_SET(varName, v)\
	int32_t rng = 0; \
	do rng = RANDOM_FUNC(); while (rng < 0); \
	rng = (rng % 233) + 2; \
	varName##Xor = rng ^ v; \
	varName##Magic = rng;

#define CCX_GET(varName) (varName##Xor ^ varName##Magic)
