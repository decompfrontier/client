#include "Pch.hpp"
#include "GameLayer.hpp"
#include "CommonUtils.hpp"
#include "CcImages.hpp"

GameSprite::GameSprite() : m_layerId(0), m_positionX(0.0f),
m_positionY(0.0f), m_mngId(), m_cachePos(), m_animSprite(nullptr),
m_fileName(), m_defaultOpacity(0xff)
{}

void GameSprite::setHeight(float w)
{
	const cocos2d::CCSize& x = getContentSize();
	if (x.height != w)
	{
		setScaleX(w / x.height);
	}
}

void GameSprite::setWidth(float w)
{
	const cocos2d::CCSize& x = getContentSize();
	if (x.width != w)
	{
		setScaleX(w / x.width);
	}
}

void GameSprite::setSize(float w, float h)
{
	setWidth(w);
	setHeight(h);
}

void GameSprite::setPositionY(float y)
{
	m_positionY = y;
	CCSprite::setPosition(CommonUtils::convertPosition(m_positionX, y));
}

void GameSprite::setPositionX(float x)
{
	m_positionX = x;
	CCSprite::setPosition(CommonUtils::convertPosition(x, m_positionY));
}

void GameSprite::setPosition(float x, float y)
{
	m_positionX = x;
	m_positionY = y;
	CCSprite::setPosition(CommonUtils::convertPosition(x, y));
}

void GameSprite::setMask()
{
	const cocos2d::ccColor3B& color = getColor();
	setColor(cocos2d::ccColor3B{ 100,0,0 });
}

void GameSprite::setCachePos()
{
	cocos2d::CCDirector* director = cocos2d::CCDirector::sharedDirector();
	cocos2d::CCPoint ui = director->convertToUI(CCSprite::getPosition());
	float x = ui.x;
	ui = director->convertToUI(CCSprite::getPosition());
	float x2 = ui.x;
	m_cachePos = cocos2d::CCPoint(x, x2);
}

GameSprite* GameSprite::initWithSize(cocos2d::CCTexture2D* tex, float w, float h)
{
	GameSprite* spr = init(tex);
	spr->setWidth(w);
	spr->setHeight(h);
	return spr;
}

GameSprite* GameSprite::init(cocos2d::CCTexture2D* tex)
{
	GameSprite* spr = new GameSprite();
	if (!tex)
	{
		tex = cocos2d::CCTextureCache::sharedTextureCache()->textureForKey("cc_2x2_null_image");
		if (!tex)
		{
			cocos2d::CCImage* img = new cocos2d::CCImage();
			img->initWithImageData(const_cast<uint8_t*>(cc_2x2_null_image::data), cc_2x2_null_image::size, cocos2d::CCImage::kFmtRawData, cc_2x2_null_image::width, cc_2x2_null_image::height, cc_2x2_null_image::bpp);
			tex = cocos2d::CCTextureCache::sharedTextureCache()->addUIImage(img, "cc_2x2_null_image");
			img->release();
		}
	}
	spr->initWithTexture(tex);
	spr->setAnchorPoint(cocos2d::CCPoint(0.0f, 1.0f));
	return spr;
}

void GameSprite::incPosition(float x, float y)
{
	m_positionX += x;
	m_positionY += y;
	CCSprite::setPosition(CommonUtils::convertPosition(m_positionX, m_positionY));
}

float GameSprite::getWidth()
{
	return getContentSize().width * getScaleX();
}

float GameSprite::getTop()
{
	return getPosition().y - ((getContentSize().height * getScaleY()) / 2);
}

float GameSprite::getPositionY()
{
	return cocos2d::CCDirector::sharedDirector()->convertToUI(getPosition()).y;
}

float GameSprite::getPositionX()
{
	return cocos2d::CCDirector::sharedDirector()->convertToUI(getPosition()).x;
}

float GameSprite::getLeft()
{
	return getPosition().x - ((getContentSize().width * getScaleX()) / 2);
}

float GameSprite::getHeight()
{
	return getContentSize().height * getScaleY();
}

void GameSprite::clearMask()
{
	setColor(cocos2d::ccColor3B{ 0xff, 0xff, 0xff });
}

void GameSprite::clearColor()
{
	clearMask();
}
