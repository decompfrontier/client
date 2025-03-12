package sg.gumi.util;

import ch.boye.httpclientandroidlib.ConnectionReuseStrategy;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.conn.ConnectionKeepAliveStrategy;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import ch.boye.httpclientandroidlib.impl.conn.PoolingClientConnectionManager;
import ch.boye.httpclientandroidlib.params.HttpConnectionParams;
import ch.boye.httpclientandroidlib.params.HttpParams;
import ch.boye.httpclientandroidlib.protocol.HttpContext;

import java.util.ArrayList;
import java.util.List;

class HttpConnectionMgr {

    static class ReuseStrategy implements ConnectionReuseStrategy {
        final HttpConnectionMgr mgr;

        ReuseStrategy(HttpConnectionMgr mgr) {
            super();
            this.mgr = mgr;
        }

        public boolean keepAlive(HttpResponse response, HttpContext context) {
            return true;
        }
    }

    static class KeepAliveStrategy implements ConnectionKeepAliveStrategy {
        final HttpConnectionMgr mgr;

        KeepAliveStrategy(sg.gumi.util.HttpConnectionMgr mgr) {
            super();
            this.mgr = mgr;
        }

        public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
            return 30000;
        }
    }

    List<DefaultHttpClient> connections;
    DefaultHttpClient defaultClient;
    PoolingClientConnectionManager mgr;
    List<Boolean> status;
    
    HttpConnectionMgr() {
        defaultClient = new DefaultHttpClient();
        connections = new ArrayList<>();
        status = new ArrayList<>();
        mgr = null;
    }
    
    private DefaultHttpClient createConnection() {
        if (mgr == null) {
            mgr = new PoolingClientConnectionManager(defaultClient.getConnectionManager().getSchemeRegistry());
            mgr.setMaxTotal(10);
            mgr.setDefaultMaxPerRoute(10);
        }

        HttpParams params = defaultClient.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 20000);
        HttpConnectionParams.setSoTimeout(params, 15000);
        DefaultHttpClient client = new DefaultHttpClient(mgr, params);
        client.setKeepAliveStrategy(new KeepAliveStrategy(this));
        client.setReuseStrategy(new ReuseStrategy(this));
        return client;
    }
    
    public synchronized void downloadFinished(DefaultHttpClient a) {
        for (int i = 0; i < connections.size(); i++)
        {
            if (connections.get(i) == a)
                status.set(i, true);
        }
    }
    
    public synchronized void downloadStarted(DefaultHttpClient a) {
        for (int i = 0; i < connections.size(); i++)
        {
            if (connections.get(i) == a)
                status.set(i, false);
        }
    }
    
    public synchronized DefaultHttpClient getConnection() {
        for (int i = 0; i < status.size(); i++)
        {
            if (status.get(i)) {
                return connections.get(i);
            }
        }

        DefaultHttpClient client = createConnection();
        connections.add(client);
        status.add(true);
        return client;
    }
}
