package sg.gumi.bravefrontier;

class BraveFrontierPusher$1 implements com.pusher.client.connection.ConnectionEventListener {
    BraveFrontierPusher$1() {
    }
    
    public void onConnectionStateChange(com.pusher.client.connection.ConnectionStateChange a) {
        Object[] a0 = new Object[2];
        a0[0] = a.getPreviousState();
        a0[1] = a.getCurrentState();
        String s = String.format("Connection state changed from [%s] to [%s]", a0);
        java.io.PrintStream a1 = System.out;
        StringBuilder a2 = new StringBuilder();
        a2.append("BraveFrontierPusher: ");
        a2.append(s);
        a1.println(a2.toString());
        if (a.getCurrentState() == com.pusher.client.connection.ConnectionState.DISCONNECTED) {
            sg.gumi.bravefrontier.BraveFrontierPusher$1$1 a3 = new sg.gumi.bravefrontier.BraveFrontierPusher$1$1(this);
            sg.gumi.bravefrontier.BraveFrontierPusher.access$100().schedule((Runnable)(Object)a3, 5L, java.util.concurrent.TimeUnit.SECONDS);
        }
    }
    
    public void onError(String s, String s0, Exception a) {
    }
}
