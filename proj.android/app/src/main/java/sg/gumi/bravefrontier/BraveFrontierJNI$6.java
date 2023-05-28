package sg.gumi.bravefrontier;

class BraveFrontierJNI$6 implements Runnable {
    final String val$_fileName;
    final boolean val$_toClose;
    
    BraveFrontierJNI$6(String s, boolean b) {
        this.val$_fileName = s;
        this.val$_toClose = b;
        super();
    }
    
    public void run() {
        sg.gumi.bravefrontier.video.BFVideoView.getInstance(sg.gumi.bravefrontier.BraveFrontierJNI.getActivity()).playVideo(this.val$_fileName, this.val$_toClose);
    }
}
