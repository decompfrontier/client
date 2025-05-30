#pragma once

//-- Synthesize without vftable
#define CC_PROPERTY_NV_READONLY(varType, varName, funName)\
protected: varType varName;\
public: varType get##funName(void);
#define CC_PROPERTY_NV_READONLY_PASS_BY_REF(varType, varName, funName)\
protected: varType varName;\
public: const varType& get##funName(void);
#define CC_PROPERTY_NV(varType, varName, funName)\
protected: varType varName;\
public: varType get##funName(void);\
public: void set##funName(varType var);
#define CC_PROPERTY_NV_PASS_BY_REF(varType, varName, funName)\
protected: varType varName;\
public: const varType& get##funName(void);\
public: void set##funName(const varType& var);
#define CC_SYNTHESIZE_NV(varType, varName, funName)\
protected: varType varName;\
public: varType get##funName(void) const { return varName; }\
public: void set##funName(varType var){ varName = var; }
#define CC_SYNTHESIZE_NV_READONLY(varType, varName, funName)\
protected: varType varName;\
public: varType get##funName(void) const { return varName; }
#define CC_SYNTHESIZE_NV_PASS_BY_REF(varType, varName, funName)\
protected: varType varName;\
public: const varType& get##funName(void) const { return varName; }\
public: void set##funName(const varType& var){ varName = var; }
#define CC_SYNTHESIZE_NV_RETAIN(varType, varName, funName)    \
private: varType varName; \
public: varType get##funName(void) const { return varName; } \
public: void set##funName(varType var)   \
{ \
    if (varName != var) \
    { \
        CC_SAFE_RETAIN(var); \
        CC_SAFE_RELEASE(varName); \
        varName = var; \
    } \
}
//-- Synthesize with is and vftable:
#define CC_PROPERTY_IS(varType, varName, funName)\
protected: varType varName;\
public: virtual varType is##funName(void) const;
#define CC_SYNTHESIZE_IS_READONLY(varType, varName, funName)\
protected: varType varName;\
public: virtual varType is##funName(void) const { return varName; }
#define CC_SYNTHESIZE_IS(varType, varName, funName)\
protected: varType varName;\
public: virtual varType is##funName(void) const { return varName; }

//-- Synthesize with is without vftable:
#define CC_PROPERTY_NVIS(varType, varName, funName)\
protected: varType varName;\
public: varType is##funName(void) const;
#define CC_SYNTHESIZE_NVIS_READONLY(varType, varName, funName)\
protected: varType varName;\
public: varType is##funName(void) const { return varName; }
#define CC_SYNTHESIZE_NVIS(varType, varName, funName)\
protected: varType varName;\
public: varType is##funName(void) const { return varName; }

//-- Simple declarations
#define CC_DECLARE(varType, varName)\
protected: varType varName;
#define CC_UNKNOWN(varType, id) \
protected: varType unk##id
