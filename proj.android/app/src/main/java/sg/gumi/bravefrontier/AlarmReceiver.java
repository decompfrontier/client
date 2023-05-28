package sg.gumi.bravefrontier;

import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import static android.os.Build.*;
import androidx.core.app.not


public class AlarmReceiver extends BroadcastReceiver {
    public AlarmReceiver() {
    }
    
    public void onReceive(Context ctx, android.content.Intent intent) {
        Log.i("AlarmReceiver", "Notification received");
        try {
            ApplicationInfo applicationInfo = null;
            String notification = intent.getExtras().getString("notification");
            android.content.Intent intent1 = new Intent(ctx, BraveFrontier.class);
            intent1.setFlags(268435456);
            PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent1, 0);
            PackageManager pm = ctx.getPackageManager();
            try {
                applicationInfo = pm.getApplicationInfo(ctx.getPackageName(), 0);
            } catch(PackageManager.NameNotFoundException ignoredException) {
                applicationInfo = null;
            }
            String appName = (applicationInfo == null) ? "Puzzle Trooper" : (String)pm.getApplicationLabel(applicationInfo);
            NotificationManager notificationMngr = (NotificationManager)ctx.getSystemService("notification");
            if (VERSION.SDK_INT < 26) {
                Notification notif = new BigTextStyle(new Builder(ctx).setContentText(notification).setContentTitle(appName).setSmallIcon(2131099786).setAutoCancel(true).setContentIntent(pendingIntent).setWhen(System.currentTimeMillis())).bigText(notification).build();
                int i = notif.defaults | 1;
                notif.defaults = i;
                notif.defaults = i | 2;
                notif.ledARGB = -397242;
                notif.ledOnMS = 300;
                notif.ledOffMS = 3000;
                notif.flags = notif.flags | 1;
                notificationMngr.notify(notification, 0, notif);
            } else {
                if (notificationMngr.getNotificationChannel("default_channel_id") == null) {
                    android.app.NotificationChannel a8 = new android.app.NotificationChannel("default_channel_id", (CharSequence)(Object)"Default Channel", 2);
                    a8.setLightColor(-397242);
                    a8.enableVibration(true);
                    notificationMngr.createNotificationChannel(a8);
                }
                androidx.core.app.NotificationCompat$Builder a9 = new androidx.core.app.NotificationCompat$Builder(ctx, "default_channel_id");
                a9.setContentTitle((CharSequence)(Object)appName);
                a9.setContentText((CharSequence)(Object)notification);
                a9.setDefaults(3);
                a9.setSmallIcon(2131099786);
                a9.setAutoCancel(true);
                a9.setContentIntent(pendingIntent);
                a9.setLights(-397242, 300, 3000);
                a9.setWhen(System.currentTimeMillis());
                notificationMngr.notify(notification, 0, a9.build());
            }
            android.app.PendingIntent a10 = android.app.PendingIntent.getBroadcast(ctx, 0, intent, 536870912);
            ((android.app.AlarmManager)ctx.getSystemService("alarm")).cancel(a10);
            a10.cancel();
        } catch(Exception a11) {
            a11.printStackTrace();
        }
    }
}
