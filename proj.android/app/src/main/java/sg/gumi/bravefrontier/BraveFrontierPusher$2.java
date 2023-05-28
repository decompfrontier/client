package sg.gumi.bravefrontier;

class BraveFrontierPusher$2 implements com.pusher.client.channel.PresenceChannelEventListener {
    BraveFrontierPusher$2() {
    }
    
    public void onAuthenticationFailure(String s, Exception a) {
        java.io.PrintStream a0 = System.out;
        Object[] a1 = new Object[2];
        a1[0] = s;
        a1[1] = a.getMessage();
        a0.println(String.format("PUSHER_FAIL: %s %s", a1));
    }
    
    public void onEvent(String s, String s0, String s1) {
        java.io.PrintStream a = System.out;
        Object[] a0 = new Object[1];
        a0[0] = s0;
        a.println(String.format("Pusher onEvent %s", a0));
        System.out.println(s1);
        sg.gumi.bravefrontier.BraveFrontierPusher.responsePusher(s1);
    }
    
    public void onSubscriptionSucceeded(String s) {
    }
    
    public void onUsersInformationReceived(String s, java.util.Set a) {
        Object a0 = a.iterator();
        while(((java.util.Iterator)a0).hasNext()) {
            this.userSubscribed(s, (com.pusher.client.channel.User)((java.util.Iterator)a0).next());
        }
    }
    
    public void userSubscribed(String s, com.pusher.client.channel.User a) {
        System.out.println("pusher userSubscribed");
        System.out.println(a.toString());
        StringBuilder a0 = new StringBuilder();
        a0.append("{\"user_id\":\"");
        a0.append(a.getId());
        a0.append("\",\"user_info\":");
        a0.append(a.getInfo());
        a0.append("}");
        sg.gumi.bravefrontier.BraveFrontierPusher.responsePusherMemberAdded(a0.toString());
    }
    
    public void userUnsubscribed(String s, com.pusher.client.channel.User a) {
        System.out.println("pusher userUnsubscribed");
        System.out.println(a.toString());
        StringBuilder a0 = new StringBuilder();
        a0.append("{\"user_id\":\"");
        a0.append(a.getId());
        a0.append("\"}");
        sg.gumi.bravefrontier.BraveFrontierPusher.responsePusherMemberRemoved(a0.toString());
    }
}
