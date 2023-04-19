package sg.gumi.util;

class HttpConnectionMgr {
    java.util.ArrayList connections;
    ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient defaultClient;
    ch.boye.httpclientandroidlib.impl.conn.PoolingClientConnectionManager mgr;
    java.util.ArrayList status;
    
    HttpConnectionMgr() {
        this.defaultClient = new ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient();
        this.connections = new java.util.ArrayList();
        this.status = new java.util.ArrayList();
        this.mgr = null;
    }
    
    private ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient createConnection() {
        if (this.mgr == null) {
            ch.boye.httpclientandroidlib.impl.conn.PoolingClientConnectionManager a = new ch.boye.httpclientandroidlib.impl.conn.PoolingClientConnectionManager(((ch.boye.httpclientandroidlib.impl.client.AbstractHttpClient)this.defaultClient).getConnectionManager().getSchemeRegistry());
            this.mgr = a;
            a.setMaxTotal(10);
            this.mgr.setDefaultMaxPerRoute(10);
        }
        ch.boye.httpclientandroidlib.params.HttpParams a0 = ((ch.boye.httpclientandroidlib.impl.client.AbstractHttpClient)this.defaultClient).getParams();
        ch.boye.httpclientandroidlib.params.HttpConnectionParams.setConnectionTimeout(a0, 20000);
        ch.boye.httpclientandroidlib.params.HttpConnectionParams.setSoTimeout(a0, 15000);
        ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient a1 = new ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient((ch.boye.httpclientandroidlib.conn.ClientConnectionManager)(Object)this.mgr, a0);
        ((ch.boye.httpclientandroidlib.impl.client.AbstractHttpClient)a1).setKeepAliveStrategy((ch.boye.httpclientandroidlib.conn.ConnectionKeepAliveStrategy)(Object)new sg.gumi.util.HttpConnectionMgr$1(this));
        ((ch.boye.httpclientandroidlib.impl.client.AbstractHttpClient)a1).setReuseStrategy((ch.boye.httpclientandroidlib.ConnectionReuseStrategy)(Object)new sg.gumi.util.HttpConnectionMgr$2(this));
        return a1;
    }
    
    public void downloadFinished(ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient a) {
        /*monenter(this)*/;
        int i = 0;
        try {
            while(i < this.connections.size()) {
                {
                    if (this.connections.get(i) != a) {
                        i = i + 1;
                        continue;
                    }
                    this.status.set(i, (Object)Boolean.TRUE);
                    break;
                }
            }
        } catch(Throwable a0) {
            /*monexit(this)*/;
            throw a0;
        }
        /*monexit(this)*/;
    }
    
    public void downloadStarted(ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient a) {
        /*monenter(this)*/;
        int i = 0;
        try {
            while(i < this.connections.size()) {
                {
                    if (this.connections.get(i) != a) {
                        i = i + 1;
                        continue;
                    }
                    this.status.set(i, (Object)Boolean.FALSE);
                    break;
                }
            }
        } catch(Throwable a0) {
            /*monexit(this)*/;
            throw a0;
        }
        /*monexit(this)*/;
    }
    
    public ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient getConnection() {
        ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient a = null;
        /*monenter(this)*/;
        int i = 0;
        try {
            while(true) {
                int i0 = this.status.size();
                a = null;
                if (i >= i0) {
                    break;
                }
                {
                    if (!((Boolean)this.status.get(i)).booleanValue()) {
                        i = i + 1;
                        continue;
                    }
                    a = (ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient)this.connections.get(i);
                    break;
                }
            }
            if (a == null) {
                a = this.createConnection();
                this.connections.add((Object)a);
                this.status.add((Object)Boolean.TRUE);
            }
        } catch(Throwable a0) {
            /*monexit(this)*/;
            throw a0;
        }
        /*monexit(this)*/;
        return a;
    }
}
