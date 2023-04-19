package sg.gumi.bravefrontier;

public class GCMIntentService extends com.google.android.gcm.GCMBaseIntentService {
    static int notifId;
    
    public GCMIntentService() {
        String[] a = new String[1];
        a[0] = "821991734423";
        super(a);
    }
    
    protected String[] getSenderIds(android.content.Context a) {
        String[] a0 = new String[1];
        a0[0] = "821991734423";
        return a0;
    }
    
    protected void onError(android.content.Context a, String s) {
        android.util.Log.e("GCMIntentService: on register error ", s);
    }
    
    protected void onMessage(android.content.Context a, android.content.Intent a0) {
        android.os.Bundle a1 = a0.getExtras();
        label1: {
            android.content.pm.ApplicationInfo a2 = null;
            Throwable a3 = null;
            if (a1 == null) {
                break label1;
            }
            if (android.os.Build$VERSION.SDK_INT < 14) {
                break label1;
            }
            String s = a1.getString("message");
            label2: {
                label3: {
                    if (s == null) {
                        break label3;
                    }
                    if (!s.equals((Object)"")) {
                        break label2;
                    }
                }
                s = a1.getString("msg");
            }
            if (s == null) {
                break label1;
            }
            if (s.equals((Object)"")) {
                break label1;
            }
            android.content.Intent a4 = new android.content.Intent(a, sg.gumi.bravefrontier.BraveFrontier.class);
            a4.setFlags(268435456);
            android.app.PendingIntent a5 = android.app.PendingIntent.getActivity(a, 0, a4, 0);
            android.content.pm.PackageManager a6 = ((android.app.IntentService)this).getApplicationContext().getPackageManager();
            try {
                a2 = a6.getApplicationInfo(((android.app.IntentService)this).getPackageName(), 0);
            } catch(android.content.pm.PackageManager$NameNotFoundException ignoredException) {
                a2 = null;
            }
            Object a7 = (a2 == null) ? sg.gumi.bravefrontier.BraveFrontier.getAppName() : a6.getApplicationLabel(a2);
            String s0 = (String)a7;
            label0: {
                Exception a8 = null;
                try {
                    try {
                        if (android.os.Build$VERSION.SDK_INT < 26) {
                            androidx.core.app.NotificationCompat$Builder a9 = new androidx.core.app.NotificationCompat$Builder(a);
                            a9.setContentText((CharSequence)(Object)s);
                            a9.setContentTitle((CharSequence)(Object)s0);
                            a9.setSmallIcon((android.os.Build$VERSION.SDK_INT < 11) ? 17301659 : 2131427328);
                            a9.setAutoCancel(true);
                            a9.setContentIntent(a5);
                            a9.setWhen(System.currentTimeMillis());
                            a9.setLights(-16711681, 2000, 1000);
                            android.app.Notification a10 = a9.build();
                            int i = a10.defaults | 1;
                            a10.defaults = i;
                            a10.defaults = i | 2;
                            a10.ledARGB = -397242;
                            a10.ledOnMS = 300;
                            a10.ledOffMS = 3000;
                            a10.flags = a10.flags | 1;
                            android.app.NotificationManager a11 = (android.app.NotificationManager)a.getSystemService("notification");
                            int i0 = notifId + 1;
                            notifId = i0;
                            a11.notify(i0, a10);
                            break label1;
                        } else {
                            android.app.NotificationManager a12 = (android.app.NotificationManager)a.getSystemService("notification");
                            if (a12.getNotificationChannel("default_channel_id") == null) {
                                android.app.NotificationChannel a13 = new android.app.NotificationChannel("default_channel_id", (CharSequence)(Object)"Default Channel", 2);
                                a13.enableLights(true);
                                a13.setLightColor(-397242);
                                a13.enableVibration(true);
                                a12.createNotificationChannel(a13);
                            }
                            androidx.core.app.NotificationCompat$Builder a14 = new androidx.core.app.NotificationCompat$Builder(a, "default_channel_id");
                            a14.setContentTitle((CharSequence)(Object)s0);
                            a14.setContentText((CharSequence)(Object)s);
                            a14.setSmallIcon(2131427328);
                            a14.setAutoCancel(true);
                            a14.setContentIntent(a5);
                            a14.setLights(-397242, 2000, 1000);
                            a14.setWhen(System.currentTimeMillis());
                            android.app.Notification a15 = a14.build();
                            int i1 = notifId + 1;
                            notifId = i1;
                            a12.notify(i1, a15);
                            break label1;
                        }
                    } catch(Exception a16) {
                        a8 = a16;
                    }
                } catch(Throwable a17) {
                    a3 = a17;
                    break label0;
                }
                a8.printStackTrace();
                break label1;
            }
            a3.printStackTrace();
        }
    }
    
    protected void onRegistered(android.content.Context a, String s) {
        if (sg.gumi.bravefrontier.BraveFrontier.getAppContext() != null) {
            ((android.opengl.GLSurfaceView)((org.cocos2dx.lib.Cocos2dxActivity)sg.gumi.bravefrontier.BraveFrontier.getActivity()).getGLView()).queueEvent((Runnable)(Object)new sg.gumi.bravefrontier.GCMIntentService$1(this, s));
            android.util.Log.v("onRegistered", s);
            return;
        }
        try {
            sg.gumi.bravefrontier.BraveFrontierJNI.nativeSetDeviceRegistrationId(s);
        } catch(Throwable ignoredException) {
        }
    }
    
    protected void onUnregistered(android.content.Context a, String s) {
    }
}
