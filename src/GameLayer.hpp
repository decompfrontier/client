#pragma once

#include "ScrlLayer.hpp"
#include "GameSprite.hpp"
#include "BaseScene.hpp"
#include "NodeStatus.hpp"

class GameLayer : public cocos2d::CCObject
{
public:
    ~GameLayer() {}

    void addChild(int, cocos2d::CCNode*);
    void addChild(int, int, cocos2d::CCNode*);
    void addScene(void*);
    void addSprite(int, GameSprite*, int, int, int);
    void clear(int);
    void clear(int, int);
    void clear();
    void clearTrnsList();
    void clearWork();
    void clearWorkSpecial();
    void clearZorder(int, int);
    void containsChild(int, cocos2d::CCNode*);
    void fadeIn(int, int);
    void fadeInStart(int);
    void fadeOut(int, int);
    void fadeOutStart(int);
    void getAllNodeList(int);
    void getLayer(int);
    void getMask(int);
    void getMask(std::string);
    void getNextMaskTag();
    void getNode(int);
    void getNodeOpacity(cocos2d::CCNode*);
    void getNodeStatus(int);
    void getNodeStatus(int, int);
    void getOffsetX();
    void getOffsetY();
    void getParentLayer(cocos2d::CCNode*);
    void getScrlX(int);
    void getScrlY(int);
    void initScrl();
    bool isDispNode(int, cocos2d::CCNode*);
    bool isFade();
    bool isMask(GameSprite*);
    bool isRunningAction(int);
    bool isScrlArea(cocos2d::CCTouch*);
    bool isSlide(int);
    bool isSlide();
    bool needFadeIn();
    void pauseNodeActions();
    void reEntry(int, cocos2d::CCNode*);
    void reEntry(int, int, cocos2d::CCNode*);
    void removeChild(cocos2d::CCNode*);
    void removeChild(cocos2d::CCNode*, bool);
    void removeChild(int, cocos2d::CCNode*);
    void removeChild(int, cocos2d::CCNode*, bool);
    void removeMask(int);
    void removeMask(std::string);
    void removeMask();
    void removeScene(BaseScene*);
    void reorderChild(int, int, cocos2d::CCNode*);
    void resumeNodeActions();
    void scrlLayer(int, cocos2d::CCPoint);
    void scrollBy(int, cocos2d::CCPoint, float);
    void scrollTo(int, cocos2d::CCPoint, float);
    void setActive(int, bool);
    void setActive(int, int, bool);
    void setClip(int, float, float, float, float);
    void setFadeInList(int);
    void setFaceOutList(int);
    void setIPhoneXWallpaper(bool);
    void setMask(GameSprite*);
    void setMask(std::string, int, float, float, float, float, int, int, cocos2d::ccColor3B);
    void setModifyTransList(bool);
    void setNodeOpacity(cocos2d::CCNode*, int);
    void setOpacity(int);
    void setOpacity(int, int);
    void setPosition(int, float, float);
    void setSlideEnable(int, bool);
    void setSlideEnable(int, int, bool);
    void setTransParentNodeList(int);
    void setTransParentNodeList();
    void setVisible(int, bool);
    void setVisible(int, int, bool);
    void setWallPaper();

    static GameLayer* shared();

    void touchBeganForGameLayer(cocos2d::CCTouch*, cocos2d::CCEvent*);
    void touchCancelledForGameLayer(cocos2d::CCTouch*, cocos2d::CCEvent*);
    void touchEndedForGameLayer(cocos2d::CCTouch*, cocos2d::CCEvent*);
    void touchMovedForGameLayer(cocos2d::CCTouch*, cocos2d::CCEvent*);
    void update() {}

protected:
    cocos2d::CCMutableArray<ScrlLayer*>* m_scrlLayers;
    cocos2d::CCMutableArray<NodeStatus*>* m_nodeStatuses;
    cocos2d::CCMutableArray<cocos2d::CCParticleSystem*>* m_particles;
    cocos2d::CCMutableDictionary<std::string, GameSprite*>* m_gameSprites;
    short m_unk;

private:
    GameLayer();

    static GameLayer* m_gameLayer;

};

