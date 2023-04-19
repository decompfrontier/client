package sg.gumi.bravefrontier;

final public class NotificationService {
    final public static String GCM_SENDER_ID = "821991734423";
    private static sg.gumi.bravefrontier.NotificationService instance;
    
    static {
    }
    
    public NotificationService() {
    }
    
    public static sg.gumi.bravefrontier.NotificationService getInstance() {
        if (instance == null) {
            instance = new sg.gumi.bravefrontier.NotificationService();
        }
        return instance;
    }
    
    public void onCreate(sg.gumi.bravefrontier.BraveFrontier a) {
        sg.gumi.util.BFConfig$Platform dummy = sg.gumi.util.BFConfig.PLATFORM;
        sg.gumi.util.BFConfig$Platform dummy0 = sg.gumi.util.BFConfig.PLATFORM_GOOGLE;
    }
    
    public void setRemoteNotificationsEnable(sg.gumi.bravefrontier.BraveFrontier a, boolean b) {
    }
}
