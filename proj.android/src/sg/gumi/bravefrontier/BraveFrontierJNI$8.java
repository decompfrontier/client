package sg.gumi.bravefrontier;

class BraveFrontierJNI$8 implements Runnable {
    final String val$_videoId;
    final android.content.Context val$mContext;
    
    BraveFrontierJNI$8(String s, android.content.Context a) {
        this.val$_videoId = s;
        this.val$mContext = a;
        super();
    }
    
    public void run() {
        sg.gumi.bravefrontier.YoutubeActivity.VIDEO_ID = this.val$_videoId;
        android.content.Intent a = new android.content.Intent(this.val$mContext, sg.gumi.bravefrontier.YoutubeActivity.class);
        ((android.app.Activity)sg.gumi.bravefrontier.BraveFrontierJNI.access$000()).startActivity(a);
    }
}
