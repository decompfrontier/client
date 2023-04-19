package sg.gumi.util;

class AsyncFileLoad$1 implements Runnable {
    final long val$iobj;
    final String val$iurl;
    
    AsyncFileLoad$1(String s, long j) {
        this.val$iurl = s;
        this.val$iobj = j;
        super();
    }
    
    public void run() {
        ((Thread)new sg.gumi.util.AsyncFileLoad(this.val$iurl, this.val$iobj)).start();
    }
}
