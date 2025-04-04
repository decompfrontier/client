#pragma once

class PTRateThisAppPopup
{
public:
#ifdef __ANDROID__
    static void alertCloseCallback(int rated, jobject thiz);
#endif
};
