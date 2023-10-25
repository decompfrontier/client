#pragma once

class StringLabel : public cocos2d::CCLabelTTF
{
public:
	StringLabel();
	~StringLabel();

	unk changeString(const char*);
	unk getAlign();
	unk getAlignPosition(float, float);
	unk getFontName();
	unk getMngID();
	unk getOffsetX();
	unk getOffsetY();
	unk getX();
	unk getY();
	unk init(const char*, cocos2d::ccColor3B, int);
	unk init(const char*, cocos2d::ccColor3B, int, int);
	unk init(const char*, float, float, cocos2d::ccColor3B, int, int);
	unk init(const char*, float, float, float, float, cocos2d::ccColor3B, const char*, int);
	unk init(const char*, float, float, float, float, cocos2d::ccColor3B, int, int);
	unk init(int, const char*, float, float, cocos2d::ccColor3B, int, int);
	unk init(int, const char*, float, float, float, float, cocos2d::ccColor3B, int, int);
	unk set(int, const char*, float, float, cocos2d::ccColor3B, int, int);
	unk set(int, const char*, float, float, cocos2d::ccColor3B, int, int, const char*);
	unk set(int, const char*, float, float, float, float, cocos2d::ccColor3B, int, int);
	unk set(int, const char*, float, float, float, float, cocos2d::ccColor3B, int, int, const char*);
	unk setAlign(int);
	unk setFontSize(int);
	unk setMngID(std::string);
	unk setOffsetX(float);
	unk setOffsetY(float);
	unk setPosition(float, float);
	unk setPosition(int, float, float);

};
