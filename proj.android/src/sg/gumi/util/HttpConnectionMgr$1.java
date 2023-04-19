package sg.gumi.util;

class HttpConnectionMgr$1 implements ch.boye.httpclientandroidlib.conn.ConnectionKeepAliveStrategy {
    final sg.gumi.util.HttpConnectionMgr this$0;
    
    HttpConnectionMgr$1(sg.gumi.util.HttpConnectionMgr a) {
        this.this$0 = a;
        super();
    }
    
    public long getKeepAliveDuration(ch.boye.httpclientandroidlib.HttpResponse a, ch.boye.httpclientandroidlib.protocol.HttpContext a0) {
        return 30000L;
    }
}
