#pragma once

class AsyncFileLoad : public cocos2d::CCObject
{
public:
	bool isConnect() { return m_isConnected; }
	bool isError() { return m_isInError; }
	bool isFinished() { return m_isFinished; }
	bool isImage();
	void loadFile(std::string url, void* user);

	ALWAYS_INLINE void setError(bool err) { m_isInError = err; }
	ALWAYS_INLINE void finish() { m_isFinished = true; }
	ALWAYS_INLINE cocos2d::CCObject* getUserObj() { return m_userObj; }

	
	virtual void connectionDidFinishLoading(cocos2d::CCNode*, void* userData) {}
	
public:
	bool m_isInError;
	bool m_isFinished;
	bool m_isConnected;
	cocos2d::CCObject* m_userObj;
	std::string m_url;
};
