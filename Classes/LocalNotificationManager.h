#pragma once

class LocalNotificationManager
{
public:
	/* to fix */
	static void cancelAllNotifications();
	static void sendNotification(int, const char*, const char*, bool);
};
