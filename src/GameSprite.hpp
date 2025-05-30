#pragma once

class GameSprite : public cocos2d::CCSprite
{
public:
    void clearColor();
    void clearMask();
    float getCachePosX() const { return m_cachePos.x; }
    float getCachePosY() const { return m_cachePos.y; }
    float getGlPositionX() { return getPosition().x; }
    float getGlPositionY() { return getPosition().y; }
    float getHeight() { return getContentSize().height * getScaleY(); }
    float getLeft() { return getPosition().x - (getContentSize().width * (getScaleX() / 2)); }
    float getTop() { return getPosition().y - (getContentSize().height * (getScaleY() / 2)); }
    float getWidth() { return getContentSize().width * getScaleX(); }
    void incPosition(float x, float y);
    static GameSprite* init(cocos2d::CCTexture2D* tex);
    static GameSprite* init(cocos2d::CCTexture2D* tex, cocos2d::CCRect);
    static GameSprite* initWithFrameName(std::string frame);
    static GameSprite* initWithSize(cocos2d::CCTexture2D* tex, float w, float h);
    void setCachePos();
    void setHeight(float h);
    void setMask();
    void setPosition(float x, float y);
    void setSize(float w, float h);
    void setWidth(float w);

protected:
    GameSprite();

    CC_UNKNOWN(long long, 1);
    CC_UNKNOWN(std::string, 2);
    CC_SYNTHESIZE_NV(int, m_layerId, LayerId);
    CC_PROPERTY_NV(float, m_positionX, PositionX);
    CC_PROPERTY_NV(float, m_positionY, PositionY);
    CC_SYNTHESIZE_NV(std::string, m_mngId, MngID);
    CC_SYNTHESIZE_NV_READONLY(cocos2d::CCPoint, m_cachePos, CachePos);
    CC_SYNTHESIZE_NV(GameSprite*, m_animSprite, AnimeSprite);
    CC_SYNTHESIZE_NV(std::string, m_fileName, FileName);
    CC_SYNTHESIZE_NV(int, m_defaultOpacity, DefaultOpacity);
};
