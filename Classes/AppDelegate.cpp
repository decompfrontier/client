#include "pch.h"
#include "AppDelegate.h"
#include "UserConfigInfo.h"
#include "UserTeamInfo.h"
#include "TextManager.h"
#include "LocalNotificationManager.h"
#include "CrashlyticsUtil.h"
#include "DailyTaskPrizeMst.h"
#include "GuildRaidUserInfo.h"
#include "GuildContributionRestrictMst.h"

USING_NS_CC;
using namespace CocosDenshion;

AppDelegate::AppDelegate() {

}

AppDelegate::~AppDelegate() 
{
}

bool AppDelegate::applicationDidFinishLaunching() {
    // initialize director
    CLOG("start");

    CCDirector* pDirector = CCDirector::sharedDirector();
    pDirector->setOpenGLView(CCEGLView::sharedOpenGLView());

    return true;
}

// This function will be called when the app is inactive. When comes a phone call,it's be invoked too
void AppDelegate::applicationDidEnterBackground() {

    CLOG("start");

    CCDirector::sharedDirector()->pause();
    CCDirector::sharedDirector()->stopAnimation();

    auto config = UserConfigInfo::shared();
    auto team = UserTeamInfo::shared();
    auto txt = TextManager::shared();

    if (config->getEnergyNotification())
    {
        if (team->getActionPoint() < team->getMaxActionPoint())
        {
            LocalNotificationManager::sendNotification(
                team->getActionRestTimer(),
                txt->getText("LOCALNOTIF_ENERGY").c_str(),
                "EnergyNotif",
                false
            );
        }
    }

    if (config->getArenaOrbNotification())
    {
        if (team->getFightPoint() < team->getMaxFightPoint())
        {
            if (UserTeamInfo::shared()->getFightRestTimer() >= 2) // TODO: convert to enum!
            {
                LocalNotificationManager::sendNotification(
                    team->getFightRestTimer(),
                    txt->getText("LOCALNOTIF_ARENA").c_str(),
                    "ArenaNotif",
                    false
                );
            }
        }
    }

    if (config->getDailytaskNotification())
    {
        auto prize = DailyTaskPrizeMstList::shared();
        auto teaminfo = UserTeamInfo::shared();

        auto pt = teaminfo->getBravePointsTotal();

        if (prize->isNextMileStoneNotificationAvailable(pt))
        {
            LocalNotificationManager::sendNotification(
                prize->getLocalNotificationTimer(),
                prize->getLocalNotificationText(),
                "DailyTaskNotif",
                false
            );
        }
    }

    LocalNotificationManager::sendNotification(? , txt->getText("LOCALNOTIF_NOLOGIN").c_str(), "NoLoginNotif", false);

    auto guild = GuildUserGuildInfo::shared()->getGuildInfo();
    if (guild->getGuildId())
    {
        if (config->getGuildContribNotification())
        {
            ServerTimeInfo::shared()->updateTime();
            time_t servTime = ServerTimeInfo::shared()->getCurrentServerTimeEpoch();
            auto guildContribMst = GuildContributionRestrictionMstList::shared();
            if (guildContribMst->getCount() > 0)
            {
                bool sendNotif = false;
                GuildContributionRestrictMst* obj;
                auto tm = gmtime(&servTime);

                for (int id = 0; id < guildContribMst->getCount(); id++)
                {
                    obj = guildContribMst->getObjectByIndex(id);
                   

                    if (tm->tm_hour >= obj->getStartHour() && tm->tm_hour < (obj->getStartHour() + obj->getHour()))
                    {
                        sendNotif = true;
                        break;
                    }
                }

                if (sendNotif)
                {
                    auto notifTime = 3600 * ((obj->getHour() - tm->tm_hour) + obj->getStartHour()) + -60 * tm->tm_min - tm->tm_sec;
                    LocalNotificationManager::sendNotification(notifTime, txt->getText("LOCALNOTIF_GUILDCONTRIB").c_str(), "GuildContribNotif", false);
                }
            }
        }
    }

    // if you use SimpleAudioEngine, it must be pause
    SimpleAudioEngine::sharedEngine()->pauseBackgroundMusic();

    CLOG("dne");
}

// this function will be called when the app is active again
void AppDelegate::applicationWillEnterForeground() {
    CCDirector::sharedDirector()->startAnimation();
    CCDirector::sharedDirector()->resume();

    LocalNotificationManager::cancelAllNotifications();

    // if you use SimpleAudioEngine, it must resume here
    SimpleAudioEngine::sharedEngine()->resumeBackgroundMusic();

    GuidRaidUserInfo::shared()->setHasAppSuspended(true);

    CLOG("dne");
}

void AppDelegate::applicationWillTerminate()
{
    CLOG("start");
}
