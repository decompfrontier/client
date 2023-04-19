package sg.gumi.bravefrontier;

public class AlarmReceiver extends android.content.BroadcastReceiver {
    public AlarmReceiver() {
    }
    
    public void onReceive(android.content.Context a, android.content.Intent a0) {
        android.util.Log.i("AlarmReceiver", "Notification received");
        try {
            android.content.pm.ApplicationInfo a1 = null;
            String s = a0.getExtras().getString("notification");
            android.content.Intent a2 = new android.content.Intent(a, sg.gumi.bravefrontier.BraveFrontier.class);
            a2.setFlags(268435456);
            android.app.PendingIntent a3 = android.app.PendingIntent.getActivity(a, 0, a2, 0);
            android.content.pm.PackageManager a4 = a.getPackageManager();
            try {
                a1 = a4.getApplicationInfo(a.getPackageName(), 0);
            } catch(android.content.pm.PackageManager$NameNotFoundException ignoredException) {
                a1 = null;
            }
            Object a5 = (a1 == null) ? "Puzzle Trooper" : a4.getApplicationLabel(a1);
            String s0 = (String)a5;
            android.app.NotificationManager a6 = (android.app.NotificationManager)a.getSystemService("notification");
            if (android.os.Build$VERSION.SDK_INT < 26) {
                android.app.Notification a7 = new android.app.Notification$BigTextStyle(new android.app.Notification$Builder(a).setContentText((CharSequence)(Object)s).setContentTitle((CharSequence)(Object)s0).setSmallIcon(2131099786).setAutoCancel(true).setContentIntent(a3).setWhen(System.currentTimeMillis())).bigText((CharSequence)(Object)s).build();
                int i = a7.defaults | 1;
                a7.defaults = i;
                a7.defaults = i | 2;
                a7.ledARGB = -397242;
                a7.ledOnMS = 300;
                a7.ledOffMS = 3000;
                a7.flags = a7.flags | 1;
                a6.notify(s, 0, a7);
            } else {
                if (a6.getNotificationChannel("default_channel_id") == null) {
                    android.app.NotificationChannel a8 = new android.app.NotificationChannel("default_channel_id", (CharSequence)(Object)"Default Channel", 2);
                    a8.setLightColor(-397242);
                    a8.enableVibration(true);
                    a6.createNotificationChannel(a8);
                }
                androidx.core.app.NotificationCompat$Builder a9 = new androidx.core.app.NotificationCompat$Builder(a, "default_channel_id");
                a9.setContentTitle((CharSequence)(Object)s0);
                a9.setContentText((CharSequence)(Object)s);
                a9.setDefaults(3);
                a9.setSmallIcon(2131099786);
                a9.setAutoCancel(true);
                a9.setContentIntent(a3);
                a9.setLights(-397242, 300, 3000);
                a9.setWhen(System.currentTimeMillis());
                a6.notify(s, 0, a9.build());
            }
            android.app.PendingIntent a10 = android.app.PendingIntent.getBroadcast(a, 0, a0, 536870912);
            ((android.app.AlarmManager)a.getSystemService("alarm")).cancel(a10);
            a10.cancel();
        } catch(Exception a11) {
            a11.printStackTrace();
        }
    }
}
