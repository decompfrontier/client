#include "pch.h"
#include "ScrlLayer.h"
#include "CommonUtils.h"

using namespace cocos2d;

void ScrlLayer::visit()
{
	glEnable(GL_SCISSOR_TEST);
	CCDirector::sharedDirector()->getOpenGLView()->setScissorInPoints(clearClip.origin.x, clearClip.origin.y, clearClip.size.width, clearClip.size.height);
	CCNode::visit();
	glDisable(GL_SCISSOR_TEST);
}

void ScrlLayer::touchMovedForScrlLayer(CCTouch* touch, CCEvent* evt)
{
	if (isActive && ccTouchBegan(touch, evt) && !isMoveDest)
	{
		auto p = touch->getLocationInView();
		addTouchPosition(p);

		switch (touchStatus)
		{
		case 2:
			if (!haveLockDrug && scrollBar)
			{
				scrollBar
			}
			break;
		}
	}
}

void ScrlLayer::clearClip()
{
	auto w = CommonUtils::getScreenWidth();
	auto h = CommonUtils::getScreenHeight();

	rclearClip = CCRect(0.0f, 0.0f, w, h);
}

CCPoint ScrlLayer::getMovePoint(CCPoint base)
{
	return CCPoint(base.x + layerOffsetX, base.y + layerOffsetY);
}

CCPoint ScrlLayer::getNewestAccelOfTouchPositions()
{
	return CCPoint(d.x - p6.x, d.y - p6.y);
}

cocos2d::CCPoint ScrlLayer::getNewestSubOfTouchPositionsDuring(int)
{
//	return CCPoint(d.x - )
}

