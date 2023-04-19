package sg.gumi.bravefrontier;

class BraveFrontierJNI$5 implements Runnable {
    final String val$_fileName;
    
    BraveFrontierJNI$5(String s) {
        this.val$_fileName = s;
        super();
    }
    
    public void run() {
        sg.gumi.bravefrontier.video.BFVideoView.getInstance(sg.gumi.bravefrontier.BraveFrontierJNI.access$000()).playVideo(this.val$_fileName, false);
    }
}
