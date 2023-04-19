#include "pch.h"
#include "AppDelegate.h"
#include "UserConfigInfo.h"
#include "UserTeamInfo.h"
#include "TextManager.h"
#include "LocalNotificationManager.h"

USING_NS_CC;
using namespace std;

AppDelegate::AppDelegate() {

}

AppDelegate::~AppDelegate() 
{
}

bool AppDelegate::applicationDidFinishLaunching() {
    // initialize director
    CCDirector* pDirector = CCDirector::sharedDirector();
    pDirector->setOpenGLView(CCEGLView::sharedOpenGLView());

    return true;
}

// This function will be called when the app is inactive. When comes a phone call,it's be invoked too
void AppDelegate::applicationDidEnterBackground() {

    CLOG("\nstart");

    CCDirector::sharedDirector()->stopAnimation();

    auto config = UserConfigInfo::shared();
    auto team = UserTeamInfo::shared();

    if (config->getEnergyNotification())
    {
        if (team->getActionPoint() < team->getMaxActionPoint())
        {
            LocalNotificationManager::sendNotification(
                team->getActionRestTimer(),
                TextManager::shared()->getText("LOCALNOTIF_ENERGY").getCString(),
                "EnergyNotif",
                false
            );
        }
    }

    if (config->getArenaOrbNotification())
    {
        if (team->getFightPoint() < team->getMaxFightPoint())
        {
            LocalNotificationManager::sendNotification(
                team->getFightRestTimer(),
                TextManager::shared()->getText("LOCALNOTIF_ARENA").getCString(),
                "ArenaNotif",
                false
            );
        }
    }


    // if you use SimpleAudioEngine, it must be pause
    SimpleAudioEngine::sharedEngine()->pauseBackgroundMusic();

    CLOG("dne");
}

// this function will be called when the app is active again
void AppDelegate::applicationWillEnterForeground() {
    CCDirector::sharedDirector()->startAnimation();

    // if you use SimpleAudioEngine, it must resume here
    SimpleAudioEngine::sharedEngine()->resumeBackgroundMusic();
}
