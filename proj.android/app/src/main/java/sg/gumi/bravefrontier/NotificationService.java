package sg.gumi.bravefrontier;


final public class NotificationService {
    final public static String GCM_SENDER_ID = "821991734423";
    private static NotificationService instance;

    
    public NotificationService() {
    }
    
    public static NotificationService getInstance() {
        if (instance == null) { // singleton
            instance = new NotificationService();
        }
        return instance;
    }
    
    public void onCreate(BraveFrontier activity) {

    }
    
    public void setRemoteNotificationsEnable(BraveFrontier activity, boolean enable) {
    }
}
