#pragma once

class NativeCallbackHandler
{
public:
    void onBackButtonCalled();

    static NativeCallbackHandler* sharedHandler();
};
