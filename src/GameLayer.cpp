#include "Pch.hpp"
#include "GameLayer.hpp"
#include "CommonUtils.hpp"

GameLayer::GameLayer()
{
	m_scrlLayers = new cocos2d::CCMutableArray<ScrlLayer*>();
	for (int i = 0; i < 110; i++) // why 110?
	{
		ScrlLayer* layer = new ScrlLayer();
		float offset = 0.0f;
		if (i != 69)
		{
			offset = 0.0f;
			if (i != 75)
			{
				int w = CommonUtils::getScreenWidth();
				cocos2d::CCSize unsd(cocos2d::CCDirector::sharedDirector()->getOpenGLView()->getFrameSize()); // yes this seems unused yay!!
				int h = CommonUtils::getScreenHeight();
				int hviewport = h - 480;
				if ((h - 480) < 0)
				{
					hviewport = h - 479;
				}
				offset = -(float)(hviewport / 2);
			}
		}

		layer->setOffset(0.0f, offset);
		layer->setLayerPosition(cocos2d::CCPointZero);
		layer->retain();
		m_scrlLayers->addObject(layer);
	}
	m_nodeStatuses = new cocos2d::CCMutableArray<NodeStatus*>();
	m_particles = new cocos2d::CCMutableArray<cocos2d::CCParticleSystem*>();
	m_gameSprites = new cocos2d::CCMutableDictionary<std::string, GameSprite*>();
	m_unk = 0;
}

GameLayer* GameLayer::shared()
{
	if (!m_gameLayer)
	{
		m_gameLayer = new GameLayer();
	}

	return m_gameLayer;
}

GameLayer* GameLayer::m_gameLayer = NULL;
