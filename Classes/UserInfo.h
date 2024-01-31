#pragma once

class UserInfo : public cocos2d::CCObject
{
	SHARED_SINGLETON(UserInfo);

public:
	CC_SYNTHESIZE(std::string, m_userId, UserID);
	CC_SYNTHESIZE(std::string, m_purchaseReceipt, purchaseReceipt);
	CC_SYNTHESIZE(std::string, m_purchaseSig, purchaseSignature);
	CC_SYNTHESIZE(std::string, m_accountID, AccountID);
	CC_SYNTHESIZE(std::string, m_admobPurchaseProductId, AdmobPurchaseProductId);
	CC_SYNTHESIZE(std::string, m_admobPurchaseReceipt, AdmobPurchaseReceipt);
	CC_SYNTHESIZE(std::string, m_admobPurchaseSig, AdmobPurchaseSignature);
	CC_SYNTHESIZE(int, m_admobPurchaseResult, AdmobPurchaseResultCode);
	CC_SYNTHESIZE(std::string, m_purchaseProductId, PurchaseProductId);
	CC_SYNTHESIZE(int, m_arenaTutoChapterId, ArenaTutoChapterID);
	CC_SYNTHESIZE(std::string, m_codeExpireDate, CodeExpireDate);
	CC_SYNTHESIZE(std::string, m_contactId, ContactID);
	CC_SYNTHESIZE(bool, m_devTransfer, DeviceTransferEnable);
	CC_SYNTHESIZE(std::string, m_encryptIV, EncryptIv);
	CC_SYNTHESIZE(std::string, m_encryptFriendID, EncryptedFriendID);
	CC_SYNTHESIZE(std::string, m_finishedEvtId, FinishedEventID);
	CC_SYNTHESIZE(std::string, m_friendID, FriendID);
	CC_SYNTHESIZE(int, m_friendInvFlag, FriendInivitationFlg);
	CC_SYNTHESIZE(std::string, m_handleName, HandleName);
	CC_SYNTHESIZE(int, m_lastTutoChapterID, LastTutoChapuerID);
	CC_SYNTHESIZE(int, m_playTutoChapterID, PlayTutoChapterID);
	CC_SYNTHESIZE(std::string, m_password, Password);
	CC_SYNTHESIZE(std::string, m_multiUserDisID, MultiUserDisID);
	CC_SYNTHESIZE(int, m_modelChangeCount, ModelChangeCnt);
	CC_SYNTHESIZE(int, m_purchaseFixFlag, PurchaseFixFlg);
	CC_SYNTHESIZE(int, m_purchaseModelStatus, PurchaseModelStatus);
	CC_SYNTHESIZE(std::string, m_purchaseProductName, PurchaseProductName);
	CC_SYNTHESIZE(std::string, m_purchaseReceipt, PurchaseReceipt);
	CC_SYNTHESIZE(int, m_purchaseResetFlag, PurchaseRstFlg);
	CC_SYNTHESIZE(std::string, m_signalKey, SignalKey);
	CC_SYNTHESIZE(bool, m_triggerTutorialStarted, TriggerableTutorialStarted);
	CC_SYNTHESIZE(int, m_tutoChapterID, TutoChapterID);
	CC_SYNTHESIZE(int, m_tutoEndFlag, TutoEndFlg);
	CC_SYNTHESIZE(bool, m_tutoEndMsgFlag, TutoEndMsgFlg);
	CC_SYNTHESIZE(std::string, m_tutoEventId, TutoEventID);
	CC_SYNTHESIZE(std::string, m_userID, UserID);
	CC_SYNTHESIZE(std::string, m_contactID, ContactID);
	CC_SYNTHESIZE(std::string, m_finishedEventType, FinishedEventType);
	CC_PROPERTY(bool, m_skipMainTutorialFlag, SkipMainTutorialFlag);
	CC_SYNTHESIZE(bool, m_triggerTutorialEnabled, TriggerableTutorialEnabled);

	void clearFirstDescID(const std::string& v);
	void clearFirstDescs();
	void clearScriptDesc(const std::string& v);
	bool existUser();
	const std::string& getFacebookId() const;
	void getFirstDescs();
	void getFriendInvitationFlg();
	const std::string& getGumiLiveToken() const;
	const std::string& getGumiLiveUserId() const;
	const std::string& getLobiHandleName() const;
	const std::string& getSignInWithAppleId() const;

	void incArenaTutoChapterID() { m_arenaTutoChapterId++; }
	void incTutoChapterID() { m_tutoChapterID++; }
	bool isArenaTutoPlaying() const;
	bool isFirstDesc(const std::string& v) const;
	bool isInAmazonControlGroup() const;
	bool isScriptDesc(const std::string& v) const;
	bool isTutoPlaying() const;
	void setFirstDesc(const std::string& v);
	void setScriptDesc(const std::string& v);
	void setServiceRequestEndpointParam(const std::string& v);
	std::string getServiceRequestEndpointParam() const;
	bool setTriggerableTutorial(bool) { m_triggerTutorialEnabled = false; }
};
