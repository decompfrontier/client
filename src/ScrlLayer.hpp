#pragma once

#include "ScrollBar.hpp"

enum eScrollTouch
{
    SCROLL_TOUCH_NONE = 0,
    SCROLL_TOUCH_POINT2D = 1,
    SCROLL_TOUCH_SCROLLBAR = 2,
};

class ScrlLayer : public cocos2d::CCLayer
{
public:
    ScrlLayer();
    ~ScrlLayer() {}

    void addTouchPosition(cocos2d::CCPoint point);
    void clearClip();
    cocos2d::CCPoint getGamePosition() const;
    float getMaxPositionY() const { return m_maxPosition.y; }
    cocos2d::CCPoint getMovePoint(cocos2d::CCPoint) const;
    cocos2d::CCPoint getNewestAccelOfTouchPositions() const;
    float getScrlX() const { return m_scrollPosition.x; }
    float getScrlY() const { return m_scrollPosition.y; }
    void init();
    void initScrl();
    void initScrlArea();
    void initTouchPosition(cocos2d::CCPoint);
    bool isScrl() const;
    bool isScrlArea(cocos2d::CCTouch*) const;
    bool isScrlEnableDown() const { return m_isActive && m_isVerticalScrollEnable && m_scrollPosition.y > m_maxPosition.y; }
    bool isScrlEnableLeft() const { return m_isActive && m_isHorizontalScrollEnable && m_scrollPosition.x < m_maxPosition.x; }
    bool isScrlEnableRight() const { return m_isActive && m_isHorizontalScrollEnable && m_scrollPosition.x > m_currentSize.x; }
    bool isScrlEnableUp() const { return m_isActive && m_isVerticalScrollEnable && m_scrollPosition.y > m_currentSize.y; }
    bool isScrlLeft() const { return m_scrollVertical.x > 0.0f; }
    bool isScrlRight() const { return m_scrollVertical.y > 0.0f; }
    bool isScroll() const { return m_isTouchInScrollArea && m_touchScrollType != SCROLL_TOUCH_NONE; }
    void lockDrug() { m_lockDrug = true; }
    void lockScrl() { m_lockScroll = true; }
    void moveScroll(float);
    void scrlLayer(cocos2d::CCPoint);
    void setClip(float, float, float, float);
    void setDestPosition(float, float, cocos2d::CCPoint);
    void setLayerPosition(cocos2d::CCPoint);
    void setOffset(float x, float y);
    void setScrlArea(float, float, float, float);
    void setScrollAreaWithMinPosition(cocos2d::CCPoint, cocos2d::CCPoint);
    void setScrollHeight(float);
    void setScrollHeight(float, float);
    void setScrollPosition(cocos2d::CCPoint);
    void setScrollWidth(float);
    void setScrollWidth(float, float);

    void touchBeganForScrlLayer(cocos2d::CCTouch*, cocos2d::CCEvent*);
    void touchCancelledForScrlLayer(cocos2d::CCTouch*, cocos2d::CCEvent*);
    void touchEndedForScrlLayer(cocos2d::CCTouch*, cocos2d::CCEvent*);
    void touchMovedForScrlLayer(cocos2d::CCTouch*, cocos2d::CCEvent*);

    void unlockScrl() { m_lockScroll = false; }
    void visit();

protected:
    CC_SYNTHESIZE_READONLY(cocos2d::CCPoint, m_scrollPosition, ScrollPosition);
    cocos2d::CCPoint unk;
    CC_SYNTHESIZE_READONLY(cocos2d::CCPoint, m_scrollVertical, ScrollVertical);
    CC_SYNTHESIZE_READONLY(cocos2d::CCPoint, m_currentSize, CurrentSize);
    CC_SYNTHESIZE_READONLY(cocos2d::CCPoint, m_maxPosition, MaxPosition);
    CC_SYNTHESIZE_READONLY(cocos2d::CCPoint, m_touchScrollPosition, TouchScrollPosition);
    cocos2d::CCPoint unk2;
    CC_DECLARE(bool, m_isMoveDest);
    cocos2d::CCPoint unk3, unk4, unk5, unk6, unk7, unk8, unk9, unk10, unk11, unk12, unk13;
    CC_SYNTHESIZE(bool, m_isVerticalScrollEnable, VerticalScrollEnable);
    CC_SYNTHESIZE(bool, m_isHorizontalScrollEnable, HorizontalScrollEnable);
    CC_SYNTHESIZE(bool, m_reverseScroll, ReverseScrollEnable);
    CC_DECLARE(cocos2d::CCPoint, m_touchLocationArena);
    bool unk14;
    CC_SYNTHESIZE_IS_READONLY(bool, m_isMoveFlag, isMoveFlg);
    CC_SYNTHESIZE(ScrollBar*, m_pScrollBar, ScrollBar);
    CC_SYNTHESIZE_READONLY(cocos2d::CCRect, m_clipRect, ClipRect);
    CC_DECLARE(cocos2d::CCRect, m_scrollArea);
    CC_DECLARE(bool, m_isTouchInScrollArea);
    CC_DECLARE(bool, m_lockDrug);
    CC_DECLARE(bool, m_lockScroll);
    CC_SYNTHESIZE_IS(bool, m_isActive, Active);
    CC_SYNTHESIZE_READONLY(float, m_offsetX, LayerOffsetX);
    CC_SYNTHESIZE_READONLY(float, m_offsetY, LayerOffsetY);
    CC_SYNTHESIZE_IS(bool, m_isSlideEnable, SlideEnable);
    CC_SYNTHESIZE(bool, m_slideEnable, SlideEnable);
    CC_DECLARE(eScrollTouch, m_touchScrollType);
};
