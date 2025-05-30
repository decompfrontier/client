#pragma once

class BaseScene : public cocos2d::CCScene
{
public:
	BaseScene();
	void addChildNode(int, cocos2d::CCNode*);
	void addSprite(int, GameSprite*);
	void addSprite(int, GameSprite*, int, int);
	void addSprite(int, GameSprite*, int, int, int);
	void changeScene(BaseScene*);
	void changeScene(BaseScene*, bool);
	void changeScene(BaseScene*, bool, int);
	bool checkConnectResult();
	bool checkStartTouchLong(cocos2d::CCTouch*, cocos2d::CCEvent*);
	void clearLayer();
	void downloadFiles();
	void draw();
	void forceTouchTagDisable();
	void forceTouchTagEnable(int);
	void getColor(int, int, int);
	void getNode(int);
	void getNode(int, int);
	void getPopFlg();
	void getScreenHeight();
	void getScreenWidth();
	void getTextureForLocal(const std::string&);
	void getTextureForResult(const std::string&);
	void initConnect();
	void isChangeScene();
	void isConnectErrorHandling();
	void isExistFile(const std::string&);
	void isFlickDown();
	void isFlickLeft();
	void isFlickRight();
	void isFlickUp();
	void isInitialize();
	void isLayerFadeIn(int);
	void isLayerFadeOut(int);
	void isNeedConnect();
	void isNeedDownload();
	void isNeedLoading();
	void isPushedScene();
	void isShowiPhoneXWallpaper();
	void isStepScene();
	void isStopAnime();
	void isTouchObject(NodeStatus*, cocos2d::CCTouch*, int, int, int, int);
	void isTouchObject(int, GameSprite*, cocos2d::CCTouch*, int, int, int, int);
	void isTouchObject(int, cocos2d::CCTouch*);
	void isTouchObject(int, cocos2d::CCTouch*, int);
	void isTouchObject(int, cocos2d::CCTouch*, int, int);
	void isTouchObject(int, cocos2d::CCTouch*, int, int, int, int);
	void isTouchObject(int, int, cocos2d::CCTouch*);
	void loadFiles();
	void loadTexture(const std::string&);
	void loadTextureForLocal(const std::string&);
	void loadTextureForResource(const std::string&);
	void onBackPressedTutorial();
	void onConnectError();
	void onEnter();
	void onExit();
	void onResumeFromMultiInvateScheme();
	void onSceneChange();
	void onSceneInitialize();
	void onSceneInvisible();
	void onScenePush();
	void onSceneVisible();
	void popScene(bool);
	void pushScene(BaseScene*, bool);
	void removeTextureForLocal(const std::string&);
	void removeTextureForResource(const std::string&);


};
