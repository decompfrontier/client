#pragma once

class PWFacebookUser
{
	enum class STATUS
	{

	};

	CC_SYNTHESIZE(std::string, m_friendID, FriendID);
	CC_SYNTHESIZE(std::string, m_fullName, FullName);
	CC_SYNTHESIZE(std::string, m_handleName, HandleName);
	CC_SYNTHESIZE(std::string, m_level, Level);
	CC_SYNTHESIZE(long, m_reqTime, RequestTime);
	CC_SYNTHESIZE(PWFacebookUser::STATUS, m_status, Status);
	CC_SYNTHESIZE(std::string, m_unitID, UnitID);
	CC_SYNTHESIZE(std::string, m_userID, UserID);
};
