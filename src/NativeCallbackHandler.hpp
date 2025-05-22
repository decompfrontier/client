#pragma once

class NativeCallbackHandler
{
public:
    void onBackButtonCalled();
    void onVideoFinished();
    void onVideoSkipped();
    void onVideoPrepared();
    void onDeviceShake();
    void onPurchaseStateChanged(std::string iapData, std::string iapSignature, std::string purchase);
    void playPhonePurchaseSuccessCallBack(std::string arg1, std::string arg2, std::string arg3);
    void onPurchaseStateChangedAdmobCallback(std::string arg1, std::string arg2, std::string arg3);
    void playPhonePurchaseFailCallBack();

    static NativeCallbackHandler* sharedHandler();
};
