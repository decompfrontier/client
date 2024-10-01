#pragma once

class VersionInfo : public cocos2d::CCObject
{
public:
	void evenVersion()
	{
		m_localVer = m_serverVer;
	}

	CC_SYNTHESIZE(bool, m_downloadFlg, DownloadFlg);
	CC_SYNTHESIZE(int, m_fileCount, FileCnt);
	CC_SYNTHESIZE(int, m_localVer, LocalVersion);
	CC_SYNTHESIZE(int, m_serverVer, ServerVersion);
	CC_SYNTHESIZE(int, m_recordNum, RecordNum);
	CC_SYNTHESIZE(std::string, m_target, Target);

	bool isVersionUp()
	{
		return m_localVer != m_serverVer;
	}

	bool isSendCommon();
};

class VersionInfoList : public MstList<VersionInfo*>
{
	SHARED_SINGLETON(VersionInfoList);

};