#pragma once

class WebViewScene /* public GameScene */
{
public:
    static WebViewScene *shared() { return m_singleton; }

    void callBraveMethodFromHtml(std::string url);

private:
    static WebViewScene *m_singleton;
};
