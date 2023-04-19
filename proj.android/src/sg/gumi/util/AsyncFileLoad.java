package sg.gumi.util;

public class AsyncFileLoad extends Thread {
    static sg.gumi.util.HttpConnectionMgr connMgr;
    ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient client;
    int contentLength;
    byte[] data;
    int downloadedLen;
    String downloadurl;
    String error;
    ch.boye.httpclientandroidlib.protocol.BasicHttpContext httpcontext;
    long obj;
    
    static {
        connMgr = new sg.gumi.util.HttpConnectionMgr();
    }
    
    AsyncFileLoad(String s, long j) {
        this.data = null;
        this.error = null;
        this.client = null;
        this.httpcontext = new ch.boye.httpclientandroidlib.protocol.BasicHttpContext();
        this.obj = j;
        this.downloadurl = s;
    }
    
    static void startDownload(long j, String s) {
        ((android.app.Activity)sg.gumi.bravefrontier.BraveFrontier.getActivity()).runOnUiThread((Runnable)(Object)new sg.gumi.util.AsyncFileLoad$1(s, j));
    }
    
    public void run() {
        org.cocos2dx.lib.Cocos2dxGLSurfaceView a = null;
        sg.gumi.util.AsyncFileLoad$DownloadCallbackEvent a0 = null;
        label4: {
            label1: {
                label3: {
                    label2: try {
                        Exception a1 = null;
                        label0: {
                            try {
                                try {
                                    ch.boye.httpclientandroidlib.client.methods.HttpGet a2 = new ch.boye.httpclientandroidlib.client.methods.HttpGet(this.downloadurl);
                                    ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient a3 = connMgr.getConnection();
                                    this.client = a3;
                                    connMgr.downloadStarted(a3);
                                    ch.boye.httpclientandroidlib.HttpResponse a4 = ((ch.boye.httpclientandroidlib.impl.client.AbstractHttpClient)this.client).execute((ch.boye.httpclientandroidlib.client.methods.HttpUriRequest)(Object)a2, (ch.boye.httpclientandroidlib.protocol.HttpContext)(Object)this.httpcontext);
                                    int i = a4.getStatusLine().getStatusCode();
                                    if (i != 200) {
                                        ch.boye.httpclientandroidlib.util.EntityUtils.consume(a4.getEntity());
                                        StringBuilder a5 = new StringBuilder();
                                        a5.append("Invalid server response. Code:");
                                        a5.append(i);
                                        throw new Exception(a5.toString());
                                    }
                                    ch.boye.httpclientandroidlib.HttpEntity a6 = a4.getEntity();
                                    byte[] a7 = ch.boye.httpclientandroidlib.util.EntityUtils.toByteArray(a6);
                                    this.data = a7;
                                    this.downloadedLen = a7.length;
                                    int i0 = (int)a6.getContentLength();
                                    this.contentLength = i0;
                                    if (this.downloadedLen < i0) {
                                        StringBuilder a8 = new StringBuilder();
                                        a8.append("Invalid file. Expected len:");
                                        a8.append(this.contentLength);
                                        a8.append("  received:");
                                        a8.append(this.downloadedLen);
                                        throw new Exception(a8.toString());
                                    }
                                    break label1;
                                } catch(Exception a9) {
                                    a1 = a9;
                                    break label0;
                                }
                            } catch(Throwable ignoredException) {
                            }
                            this.data = null;
                            this.downloadedLen = 0;
                            this.contentLength = 0;
                            this.error = "Failed to allocate memory.";
                            break label2;
                        }
                        this.data = null;
                        this.downloadedLen = 0;
                        this.contentLength = 0;
                        String s = a1.getLocalizedMessage();
                        this.error = s;
                        if (s == null) {
                            this.error = ((Object)a1).getClass().getName();
                        }
                        if (this.error == null) {
                            this.error = "unknown exception";
                        }
                        a1.printStackTrace();
                        break label3;
                    } catch(Throwable a10) {
                        ((android.opengl.GLSurfaceView)((org.cocos2dx.lib.Cocos2dxActivity)sg.gumi.bravefrontier.BraveFrontier.getActivity()).getGLView()).queueEvent((Runnable)(Object)new sg.gumi.util.AsyncFileLoad$DownloadCallbackEvent(this.obj, this.data, this.error));
                        this.data = null;
                        connMgr.downloadFinished(this.client);
                        throw a10;
                    }
                    a = ((org.cocos2dx.lib.Cocos2dxActivity)sg.gumi.bravefrontier.BraveFrontier.getActivity()).getGLView();
                    a0 = new sg.gumi.util.AsyncFileLoad$DownloadCallbackEvent(this.obj, this.data, this.error);
                    break label4;
                }
                a = ((org.cocos2dx.lib.Cocos2dxActivity)sg.gumi.bravefrontier.BraveFrontier.getActivity()).getGLView();
                a0 = new sg.gumi.util.AsyncFileLoad$DownloadCallbackEvent(this.obj, this.data, this.error);
                break label4;
            }
            a = ((org.cocos2dx.lib.Cocos2dxActivity)sg.gumi.bravefrontier.BraveFrontier.getActivity()).getGLView();
            a0 = new sg.gumi.util.AsyncFileLoad$DownloadCallbackEvent(this.obj, this.data, this.error);
        }
        ((android.opengl.GLSurfaceView)a).queueEvent((Runnable)(Object)a0);
        this.data = null;
        connMgr.downloadFinished(this.client);
    }
}
