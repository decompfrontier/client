package sg.gumi.bravefrontier;

import com.pusher.client.Authorizer;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.PresenceChannel;
import com.pusher.client.channel.PresenceChannelEventListener;
import com.pusher.client.channel.PusherEvent;
import com.pusher.client.channel.User;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BraveFrontierPusher {


    static class PresenceTask implements PresenceChannelEventListener {

        public void onAuthenticationFailure(String message, Exception e) {
            System.out.println(String.format("PUSHER_FAIL: %s %s", message, e.getMessage()));
        }

        public void onEvent(final PusherEvent event) {
            // code updated for newer SDK
            System.out.println(String.format("Pusher onEvent %s", event.getEventName()));
            System.out.println(event.getData());
            BraveFrontierPusher.responsePusher(event.getData());
        }

        public void onSubscriptionSucceeded(String channelName) {
        }

        public void onUsersInformationReceived(String channelName, Set<User> users) {

            for (User u: users)
            {
                userSubscribed(channelName, u);
            }
        }

        public void userSubscribed(String channelName, User user) {
            System.out.println("pusher userSubscribed");
            System.out.println(user.toString());
            String json = "{\"user_id\":\"" +
                    user.getId() +
                    "\",\"user_info\":" +
                    user.getInfo() +
                    "}";
            BraveFrontierPusher.responsePusherMemberAdded(json);
        }

        public void userUnsubscribed(String channelName, User user) {
            System.out.println("pusher userUnsubscribed");
            System.out.println(user.toString());
            String json = "{\"user_id\":\"" +
                    user.getId() +
                    "\"}";
            BraveFrontierPusher.responsePusherMemberRemoved(json);
        }
    }

    static class ConnectTask implements ConnectionEventListener {

        static class ConnectSchedule implements Runnable {
            final ConnectTask connectObj;

            ConnectSchedule(ConnectTask connect) {
                super();
                this.connectObj = connect;
            }

            public void run() {
                BraveFrontierPusher.getPusher().connect();
            }
        }

        public void onConnectionStateChange(ConnectionStateChange state) {
            System.out.println("BraveFrontierPusher: " +
                    String.format("Connection state changed from [%s] to [%s]", state.getPreviousState(), state.getCurrentState()));
            if (state.getCurrentState() == ConnectionState.DISCONNECTED) {
                BraveFrontierPusher.getConnectionAttemptWorker().schedule(new ConnectSchedule(this), 5L, TimeUnit.SECONDS);
            }
        }

        public void onError(String message, String code, Exception ex) {
        }
    }

    final private static String API_KEY = "e772684d55b1f7b80fab";
    private static Authorizer authorizer;
    private static PresenceChannel channel;
    final private static ScheduledExecutorService connectionAttemptsWorker;
    private static Pusher pusher;
    
    static {
        connectionAttemptsWorker = Executors.newSingleThreadScheduledExecutor();
    }
    
    public BraveFrontierPusher() {
    }
    
    static Pusher getPusher() {
        return pusher;
    }
    
    static ScheduledExecutorService getConnectionAttemptWorker() {
        return connectionAttemptsWorker;
    }
    
    public static void connect() {
        pusher.connect();
    }
    
    public static void disconnect() {
        pusher.disconnect();
    }
    
    public static void initialize(String endPoint, String authKey, String userId, String userName, String apiKey) {
        HttpAuthorizer auth = new HttpAuthorizer(endPoint);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("auth_key", authKey);
        headers.put("user_id", userId);
        headers.put("user_name", userName);
        auth.setHeaders(headers);
        PusherOptions options = new PusherOptions();
        options.setAuthorizer(auth);
        pusher = new Pusher(apiKey, options);
        pusher.getConnection().bind(ConnectionState.ALL, new ConnectTask());
        pusher.connect();
    }
    
    public static boolean isConnected() {
        if (pusher != null && pusher.getConnection().getState() == ConnectionState.CONNECTED) {
            return true;
        }
        return false;
    }
    
    public static boolean isSubscribed() {
        System.out.println("Pusher isSubscribed");
        if (channel == null) {
            return false;
        }
        System.out.println("Pusher isSubscribed channel: " + channel);
        return channel.isSubscribed();
    }
    
    native public static void responsePusher(String data);
    
    
    native public static void responsePusherMemberAdded(String json);
    
    
    native public static void responsePusherMemberRemoved(String json);
    
    
    public static void subscribePresence(String channelName, String eventName) {
        try {
            String[] eventNames = new String[1];
            eventNames[0] = eventName;
            channel = pusher.subscribePresence(channelName, new PresenceTask(), eventNames);
        } catch(Exception ex) {
            System.out.println(ex);
        }
    }
    
    public static void trigger(String channelName, String eventName, String data) {
        channel.trigger(eventName, data);
    }
    
    public static void unsubscribeAll() {
        System.out.println("Pusher unsubscribeAll");
        if (channel != null) {
            System.out.println("Pusher unsubscribeAll channel: " + channel);
            pusher.unsubscribe(channel.getName());
            channel = null;
        }
    }
}
