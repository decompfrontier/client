package sg.gumi.bravefrontier;

class GameHelper$2 implements Runnable {
    final sg.gumi.bravefrontier.GameHelper this$0;
    
    GameHelper$2(sg.gumi.bravefrontier.GameHelper a) {
        this.this$0 = a;
        super();
    }
    
    public void run() {
        this.this$0.notifyListener(false);
    }
}
