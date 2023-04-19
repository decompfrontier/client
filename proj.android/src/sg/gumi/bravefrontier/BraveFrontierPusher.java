package sg.gumi.bravefrontier;

public class BraveFrontierPusher {
    final private static String API_KEY = "e772684d55b1f7b80fab";
    private static com.pusher.client.Authorizer authorizer;
    private static com.pusher.client.channel.PresenceChannel channel;
    final private static java.util.concurrent.ScheduledExecutorService connectionAttemptsWorker;
    private static com.pusher.client.Pusher pusher;
    
    static {
        connectionAttemptsWorker = java.util.concurrent.Executors.newSingleThreadScheduledExecutor();
    }
    
    public BraveFrontierPusher() {
    }
    
    static com.pusher.client.Pusher access$000() {
        return pusher;
    }
    
    static java.util.concurrent.ScheduledExecutorService access$100() {
        return connectionAttemptsWorker;
    }
    
    public static void connect() {
        pusher.connect();
    }
    
    public static void disconnect() {
        pusher.disconnect();
    }
    
    public static void initialize(String s, String s0, String s1, String s2, String s3) {
        com.pusher.client.util.HttpAuthorizer a = new com.pusher.client.util.HttpAuthorizer(s);
        java.util.HashMap a0 = new java.util.HashMap();
        a0.put((Object)"auth_key", (Object)s0);
        a0.put((Object)"user_id", (Object)s1);
        a0.put((Object)"user_name", (Object)s2);
        a.setQueryStringParameters(a0);
        com.pusher.client.PusherOptions a1 = new com.pusher.client.PusherOptions();
        a1.setAuthorizer((com.pusher.client.Authorizer)(Object)a);
        com.pusher.client.Pusher a2 = new com.pusher.client.Pusher(s3, a1);
        pusher = a2;
        a2.getConnection().bind(com.pusher.client.connection.ConnectionState.ALL, (com.pusher.client.connection.ConnectionEventListener)(Object)new sg.gumi.bravefrontier.BraveFrontierPusher$1());
        pusher.connect();
    }
    
    public static boolean isConnected() {
        com.pusher.client.Pusher a = pusher;
        if (a != null && a.getConnection().getState() == com.pusher.client.connection.ConnectionState.CONNECTED) {
            return true;
        }
        return false;
    }
    
    public static boolean isSubscribed() {
        System.out.println("Pusher isSubscribed");
        if (channel == null) {
            return false;
        }
        java.io.PrintStream a = System.out;
        StringBuilder a0 = new StringBuilder();
        a0.append("Pusher isSubscribed channel: ");
        a0.append(((Object)channel).toString());
        a.println(a0.toString());
        return ((com.pusher.client.channel.Channel)(Object)channel).isSubscribed();
    }
    
    native public static void responsePusher(String arg);
    
    
    native public static void responsePusherMemberAdded(String arg);
    
    
    native public static void responsePusherMemberRemoved(String arg);
    
    
    public static void subscribePresence(String s, String s0) {
        try {
            com.pusher.client.Pusher a = pusher;
            sg.gumi.bravefrontier.BraveFrontierPusher$2 a0 = new sg.gumi.bravefrontier.BraveFrontierPusher$2();
            String[] a1 = new String[1];
            a1[0] = s0;
            channel = a.subscribePresence(s, (com.pusher.client.channel.PresenceChannelEventListener)(Object)a0, a1);
        } catch(Exception a2) {
            System.out.println(a2.toString());
        }
    }
    
    public static void trigger(String s, String s0, String s1) {
        ((com.pusher.client.channel.PrivateChannel)(Object)channel).trigger(s0, s1);
    }
    
    public static void unsubscribeAll() {
        System.out.println("Pusher unsubscribeAll");
        if (channel != null) {
            java.io.PrintStream a = System.out;
            StringBuilder a0 = new StringBuilder();
            a0.append("Pusher unsubscribeAll channel: ");
            a0.append(((Object)channel).toString());
            a.println(a0.toString());
            pusher.unsubscribe(((com.pusher.client.channel.Channel)(Object)channel).getName());
            channel = null;
        }
    }
}
