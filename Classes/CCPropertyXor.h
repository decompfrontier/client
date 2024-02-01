#pragma once

#define CCXOR_PROPERTY_READONLY(varType, varName, funName)\
protected: varType varName##Xor; uint8_t varName##Magic; varType varName; \
public: virtual varType get##funName(void);

#define CCXOR_PROPERTY(varType, varName, funName)\
protected: varType varName##Xor; uint8_t varName##Magic; varType varName; \
public: virtual varType get##funName(void);\
public: virtual void set##funName(varType var);

#define CCXOR_SYNTHESIZE(varType, varName, funName)\
protected: varType varName##Xor; uint8_t varName##Magic; varType varName; \
public: virtual varType get##funName(void) const { return varName##Xor ^ varName##Magic; }\
public: virtual void set##funName(varType var){\
	int32_t rng = 0; \
	do rng = RANDOM_FUNC(); while (rng < 0); \
	rng = (rng % 233) + 2; \
	varName##Xor = rng ^ v; \
	varName##Magic = rng; \
	varName = v; \
}

#define CCXOR_SYNTHESIZE_READONLY(varType, varName, funName)\
protected: varType varName##Xor; uint8_t varName##Magic; varType varName; \
public: virtual varType get##funName(void) const { return varName##Xor ^ varName##Magic; }

#define CCXOR_SYNTHESIZE_SETTER(varType, varName, className, funName)\
void className##::##funName (varType v) {\
	int32_t rng = 0; \
	do rng = RANDOM_FUNC(); while (rng < 0); \
	rng = (rng % 233) + 2; \
	varName##Xor = rng ^ v; \
	varName##Magic = rng; \
	varName = v; \
}
