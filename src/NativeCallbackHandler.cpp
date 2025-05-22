#include "Pch.hpp"
#include "NativeCallbackHandler.hpp"

#ifdef __ANDROID__
void NativeCallbackHandler::onBackButtonCalled()
{

}

void NativeCallbackHandler::onVideoFinished()
{

}

void NativeCallbackHandler::onVideoSkipped()
{

}

void NativeCallbackHandler::onVideoPrepared()
{

}

void NativeCallbackHandler::onDeviceShake()
{

}

void NativeCallbackHandler::onPurchaseStateChanged(std::string iapData, std::string iapSignature, std::string purchase)
{

}

void NativeCallbackHandler::playPhonePurchaseSuccessCallBack(std::string arg1, std::string arg2, std::string arg3)
{

}

void NativeCallbackHandler::onPurchaseStateChangedAdmobCallback(std::string arg1, std::string arg2, std::string arg3)
{

}

NativeCallbackHandler* NativeCallbackHandler::sharedHandler()
{
    return NULL;
}
#endif
