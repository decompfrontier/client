#pragma once

#include <layers_scenes_transitions_nodes/CCLayer.h>

class ScrlLayer : public cocos2d::CCLayer
{
public:
	void visit() override;
	void unlockScrl() {
		isLockScrolActive = false;
	}

	void touchMovedForScrlLayer(cocos2d::CCTouch * touch, cocos2d::CCEvent * event);
	void addTouchPosition(cocos2d::CCPoint point);
	void clearClip();
	cocos2d::CCRect getClipRect() const { return rclearClip; }
	cocos2d::CCPoint getGamePosition();
	bool getIsMoveDest() const { return isMoveDest; }
	float getLayerOffsetX() const { return layerOffsetX; }
	float getLayerOffsetY() const { return layerOffsetY; }
	float getMaxPositionY() const { return maxPosition.y; }
	cocos2d::CCPoint getMovePoint(cocos2d::CCPoint base);
	cocos2d::CCPoint getNewestAccelOfTouchPositions();
	cocos2d::CCPoint getNewestSubOfTouchPositionsDuring(int);
	void etScrlX() { return m_pTransformGL[8]; }

private:
	_BYTE unkeee[8];
	_BYTE unkee[24];
	float scrollX;
	float scrollY;
	_BYTE unkddd[32];
	cocos2d::CCPoint scrollPointVertical;
	cocos2d::CCPoint minPosition;
	cocos2d::CCPoint maxPosition;
	cocos2d::CCPoint scrollPos;
	cocos2d::CCPoint p4;
	bool isMoveDest;
	_BYTE unk344[7];
	cocos2d::CCPoint p5;
	cocos2d::CCPoint newestAccelTouch2;
	cocos2d::CCPoint p7;
	cocos2d::CCPoint p8;
	cocos2d::CCPoint p9;
	cocos2d::CCPoint p10;
	cocos2d::CCPoint p11;
	cocos2d::CCPoint a;
	cocos2d::CCPoint b;
	cocos2d::CCPoint c;
	cocos2d::CCPoint newestAccelTouch1;
	bool isScrollEnableHorizontal;
	bool isScrollEnableVertical;
	bool isReverseScrollEnabled;
	cocos2d::CCPoint objectPos;
	bool isMoveFlag;
	bool unk445;
	ScrollBar* scrollBar;
	cocos2d::CCRect clearClip;
	cocos2d::CCRect scrollArea;
	bool scrollEnable;
	bool haveLockDrug;
	bool isLockScrolActive;
	bool isActive;
	float layerOffsetX;
	float layerOffsetY;
	bool slideEnable;
	_BYTE unk3x[3];
	int touchStatus;
	_BYTE unk3[4];
};
