#pragma once

class AsyncFileLoad : public cocos2d::CCObject
{
public:
	bool isConnect() const { return m_isConnected; }
	ALWAYS_INLINE bool isError() const { return m_isInError; }
	bool isFinished() const { return m_isFinished; }
	bool isImage();
	void loadFile(std::string url, void* user);

	ALWAYS_INLINE void setError(bool err) { m_isInError = err; }
	ALWAYS_INLINE void finish() { m_isFinished = true; }
	ALWAYS_INLINE cocos2d::CCObject* getUserObj() { return m_userObj; }
	ALWAYS_INLINE void releaseObj() { m_userObj->release(); m_userObj = NULL; }

	
	virtual void connectionDidFinishLoading(cocos2d::CCNode*, void* userData) {}
	
public:
	bool m_isInError;
	bool m_isFinished;
	bool m_isConnected;
	cocos2d::CCObject* m_userObj;
	std::string m_url;
};
