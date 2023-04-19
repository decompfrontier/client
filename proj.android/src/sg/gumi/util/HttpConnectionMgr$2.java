package sg.gumi.util;

class HttpConnectionMgr$2 implements ch.boye.httpclientandroidlib.ConnectionReuseStrategy {
    final sg.gumi.util.HttpConnectionMgr this$0;
    
    HttpConnectionMgr$2(sg.gumi.util.HttpConnectionMgr a) {
        this.this$0 = a;
        super();
    }
    
    public boolean keepAlive(ch.boye.httpclientandroidlib.HttpResponse a, ch.boye.httpclientandroidlib.protocol.HttpContext a0) {
        return true;
    }
}
