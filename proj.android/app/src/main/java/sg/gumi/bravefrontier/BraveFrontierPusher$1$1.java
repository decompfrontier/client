package sg.gumi.bravefrontier;

class BraveFrontierPusher$1$1 implements Runnable {
    final sg.gumi.bravefrontier.BraveFrontierPusher$1 this$0;
    
    BraveFrontierPusher$1$1(sg.gumi.bravefrontier.BraveFrontierPusher$1 a) {
        this.this$0 = a;
        super();
    }
    
    public void run() {
        sg.gumi.bravefrontier.BraveFrontierPusher.access$000().connect();
    }
}
