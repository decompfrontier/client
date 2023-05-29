package sg.gumi.bravefrontier;

import android.app.*;
import android.content.pm.PackageManager;
import android.content.pm.ApplicationInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import androidx.core.app.NotificationCompat;

import static android.os.Build.*;

public class AlarmReceiver extends BroadcastReceiver {

    public void onReceive(Context ctx, Intent intent) {
        Log.i("AlarmReceiver", "Notification received");
        try {
            ApplicationInfo applicationInfo = null;
            String notification = intent.getExtras().getString("notification");
            Intent intent1 = new Intent(ctx, BraveFrontier.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent1, 0);
            PackageManager pm = ctx.getPackageManager();
            try {
                applicationInfo = pm.getApplicationInfo(ctx.getPackageName(), 0);
            } catch(PackageManager.NameNotFoundException ignoredException) {
                applicationInfo = null;
            }
            String appName = (applicationInfo == null) ? "Puzzle Trooper" : (String)pm.getApplicationLabel(applicationInfo);
            NotificationManager notificationMngr = (NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
            if (VERSION.SDK_INT < 26) {
                Notification notif = new BigTextStyle(new Builder(ctx).setContentText(notification).setContentTitle(appName).setSmallIcon(2131099786).setAutoCancel(true).setContentIntent(pendingIntent).setWhen(System.currentTimeMillis())).bigText(notification).build();
                int i = notif.defaults | 1;
                notif.defaults = i | 2;
                notif.ledARGB = -397242;
                notif.ledOnMS = 300;
                notif.ledOffMS = 3000;
                notif.flags = notif.flags | 1;
                notificationMngr.notify(notification, 0, notif);
            } else {
                if (notificationMngr.getNotificationChannel("default_channel_id") == null) {
                    NotificationChannel channel = new NotificationChannel("default_channel_id", "Default Channel", NotificationManager.IMPORTANCE_LOW);
                    channel.setLightColor(-397242);
                    channel.enableVibration(true);
                    notificationMngr.createNotificationChannel(channel);
                }
                NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, "default_channel_id");
                builder.setContentTitle(appName);
                builder.setContentText(notification);
                builder.setDefaults(3);
                builder.setSmallIcon(2131099786);
                builder.setAutoCancel(true);
                builder.setContentIntent(pendingIntent);
                builder.setLights(-397242, 300, 3000);
                builder.setWhen(System.currentTimeMillis());
                notificationMngr.notify(notification, 0, builder.build());
            }
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(ctx, 0, intent, PendingIntent.FLAG_NO_CREATE);
            ((AlarmManager)ctx.getSystemService(Context.ALARM_SERVICE)).cancel(pendingIntent1);
            pendingIntent1.cancel();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
