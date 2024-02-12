# Revisit TODO
Notes on what I should revisit later, this document will be updated everytime, do not keep track of changes here!

## UserInfo
- Unknown string (unk_str)
- Unknown string (unk_str2) used in UserInfoResponse
- 20 unknown bytes (p29)
- 4 unknown bytes (p241)
- 8 unknown bytes (p253)

## UserInfoResponse
- When unk_str2 in UserInfo is decoded, adjust this file
- Discover and update TutoChapterID

## FeatureGatingHandler
- Required for UserInfoResponse

## UserUnitInfoList
- Required for UserInfoResponse

## EarlyBird
- Required for UserInfoResponse

## UserSpecialScenarioInfoList
- Required for UserInfoResponse

## UserScenarioInfoList
- Required for UserInfoResponse

## AppDelegate
- getFightRestTimer shoud be an enum
- revisit applicationDidEnterBackground
- reverse applicationDidFinishLaunching
- discover if applicationWillTerminate is called by cocos2d runtime

## GuildUserGuildInfo
- Required for AppDelegate

## GuildContributionRestrictMst
- Required for AppDelegate

## ServerTimeInfo
- Required for AppDelegate

## SaveData
- Required everywhere, reverse
- Convert stuff to CC_SYNTHESIZE/CC_PROPERTY

## GuildRaidUserInfo
- Required for AppDelegate

## BaseRequest
- Finish this class

## BaseResponse
- Finish to document the parameters

## BaseScene
- Start this class

## CommonUtils
- Adjust vftable for convertPosition
- Finish this class
- Implement required stuff for ios/win32

## DailyTaskPrizeMstList
- Finish this class

## DefineMst
- Convert to CC_SYNTHESIZE or CC_PROPERTY

## FileLoader
- Start this class

## FirstDescMstList
- Finish this class

## GameLayer
- Start this class

## GameScene
- Start this class

## GameSprite
- Start this class

## GumiLiveManager
- NOTE: SaveData must be reversed first
- NOTE: NetworkManager has to be reversed first
- Finish this class
- If you discover some of the missing fields, check SavePreviousGumiLiveSession
- Check getLastLoginTypeFromUserDefault
- Check setLastLoginTypeToUserDefault after SaveData is reversed
- Check the flow on the userData for paymentVerified, this requires to better reverse the NetworkManager
- Unknown fields (q, unk5, unk6, unk8, unk9)

## GumiLiveNetworkManagement
- Finish this class (this is super boring and it's 99% just requests to NetworkManager)

## LocalNotificationManager
- Start this class

## NetworkManager
- Revising the protection of fields
- Unknown paramets (unk2, unk)
- Revisit the hostsUrl and verify if it's in the decomp
- forwardResponse
- NetworkRequest
- Revisit the flow of CCHttpRequest as nothing seems to match
- onNetworkRequestResponse

## NoticeListMstList
- Start this class

## SaveUtils
- Finish this class

## ScrLayer
- Finish this class

## ServiceRequestManager
- Start this class

## StringLabel
- Start this class

## StringLabelList
- Start this class

## TextManager
- Start this class

## UserConfigInfo
- Convert to CC_SYNTHESIZE/CC_PROPERTY
- Finish this class

## UserTeamInfo
- Convert to CC_SYNTHESIZE/CC_PROPERTY (this can be done later)

## Utils
- Finish this class
- Add missing stuff for ios/windows

## Utils::FpsTrakcer
- Start this class

## Windows main
- Actually reverse the entrypoint (which we only have the one for UWP sadly)

## DailyTaskPrizeMstList
- ISFIRST IS NOT CORRECT!!
- a3 and a2?
