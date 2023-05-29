package sg.gumi.bravefrontier;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.NotificationCompat;
//import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService /*extends GCMBaseIntentService*/ {

    class RegistredEvent implements Runnable {
        final GCMIntentService service;
        final String deviceId;

        RegistredEvent(GCMIntentService service, String deviceId) {
            super();
            this.service = service;
            this.deviceId = deviceId;
        }

        public void run() {
            try {
                BraveFrontierJNI.nativeSetDeviceRegistrationId(deviceId);
            } catch(Throwable ignoredException) {
            }
        }
    }


    static int notifId;
    
    public GCMIntentService() {
        //super(NotificationService.GCM_SENDER_ID);
    }
    
    protected String[] getSenderIds(Context context) {
        String[] ret = new String[1];
        ret[0] = NotificationService.GCM_SENDER_ID;
        return ret;
    }
    
    protected void onError(Context context, String error) {
        Log.e("GCMIntentService: on register error ", error);
    }
    
    protected void onMessage(Context context, Intent msgIntent) {
        Bundle extras = msgIntent.getExtras();
        ApplicationInfo packageInfo = null;

        if (extras == null) {
            return;
        }
        if (Build.VERSION.SDK_INT < 14) {
            return;
        }
        String message = extras.getString("message");
        if (message == null) {
            message = extras.getString("msg");
        }
        if (message.equals("")) {
            message = extras.getString("msg");
        }
        if (message == null) {
            return;
        }
        if (message.equals("")) {
            return;
        }
        Intent newTaskIntent = new Intent(context, BraveFrontier.class);
        newTaskIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, newTaskIntent, 0);
        PackageManager pm = null; /*= getApplicationContext().getPackageManager();
        try {
            packageInfo = pm.getApplicationInfo(getPackageName(), 0);
        } catch(PackageManager.NameNotFoundException ignoredException) {
            packageInfo = null;
        }*/
        String appName = (packageInfo == null) ? BraveFrontier.getAppName() : (String)pm.getApplicationLabel(packageInfo);
        try {
            try {
                if (Build.VERSION.SDK_INT < 26) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                    builder.setContentText(message);
                    builder.setContentTitle(appName);
                    builder.setSmallIcon((Build.VERSION.SDK_INT < 11) ? 17301659 : 2131427328);
                    builder.setAutoCancel(true);
                    builder.setContentIntent(pendingIntent);
                    builder.setWhen(System.currentTimeMillis());
                    builder.setLights(-16711681, 2000, 1000);
                    android.app.Notification notification = builder.build();
                    int i = notification.defaults | 1;
                    notification.defaults = i | 2;
                    notification.ledARGB = -397242;
                    notification.ledOnMS = 300;
                    notification.ledOffMS = 3000;
                    notification.flags = notification.flags | 1;
                    NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                    notifId++;
                    notificationManager.notify(notifId, notification);
                } else {
                    NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                    if (notificationManager.getNotificationChannel("default_channel_id") == null) {
                        NotificationChannel notification = new NotificationChannel("default_channel_id", "Default Channel", NotificationManager.IMPORTANCE_LOW);
                        notification.enableLights(true);
                        notification.setLightColor(-397242);
                        notification.enableVibration(true);
                        notificationManager.createNotificationChannel(notification);
                    }
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default_channel_id");
                    builder.setContentTitle(appName);
                    builder.setContentText(message);
                    builder.setSmallIcon(2131427328);
                    builder.setAutoCancel(true);
                    builder.setContentIntent(pendingIntent);
                    builder.setLights(-397242, 2000, 1000);
                    builder.setWhen(System.currentTimeMillis());
                    Notification notification = builder.build();
                    notifId++;
                    notificationManager.notify(notifId, notification);
                }
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        } catch(Throwable ex2) {
            ex2.printStackTrace();
        }
    }
    
    protected void onRegistered(Context context, String deviceId) {
        if (BraveFrontier.getAppContext() != null) {
            BraveFrontier.getActivity().getGLView().queueEvent(new RegistredEvent(this, deviceId));
            Log.v("onRegistered", deviceId);
            return;
        }
        try {
            BraveFrontierJNI.nativeSetDeviceRegistrationId(deviceId);
        } catch(Throwable ignoredException) {
        }
    }
    
    protected void onUnregistered(Context context, String deviceId) {
    }
}
