package sg.gumi.util;

import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.client.methods.HttpGet;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import ch.boye.httpclientandroidlib.protocol.BasicHttpContext;
import ch.boye.httpclientandroidlib.util.EntityUtils;
import sg.gumi.bravefrontier.BraveFrontier;
import sg.gumi.bravefrontier.BraveFrontierJNI;

public class AsyncFileLoad extends Thread {

    final static class DownloadCallbackEvent implements Runnable {
        final byte[] data;
        final String error;
        final long obj;

        DownloadCallbackEvent(long obj, byte[] data, String error) {
            this.obj = obj;
            this.data = data;
            if (error != null) {
                String trimError = error.trim();
                error = (trimError.isEmpty()) ? null : trimError;
            }
            this.error = error;
        }

        public void run() {
            try {
                BraveFrontierJNI.nativeDownloadCallback(obj, data, error);
            } catch (Throwable ignoredException) {
            }
        }
    }

    static class AsyncRun implements Runnable {
        final long obj;
        final String url;

        AsyncRun(String url, long obj) {
            super();
            this.url = url;
            this.obj = obj;
        }

        public void run() {
            new AsyncFileLoad(url, obj).start();
        }
    }


    static HttpConnectionMgr connMgr = new HttpConnectionMgr();
    DefaultHttpClient client;
    int contentLength;

    /** Data downloaded from the HTTP website */
    byte[] data;
    int downloadedLen;
    String downloadurl;
    String error;
    BasicHttpContext httpcontext;
    long obj;

    AsyncFileLoad(String url, long j) {
        data = null;
        error = null;
        client = null;
        httpcontext = new BasicHttpContext();
        obj = j;
        downloadurl = url;
    }
    
    static void startDownload(long obj, String url) {
        BraveFrontier.getActivity().runOnUiThread(new AsyncRun(url, obj));
    }

    @Override
    public void run() {
        try {
            try {
                try {
                    HttpGet request = new HttpGet(downloadurl);
                    client = connMgr.getConnection();
                    connMgr.downloadStarted(client);
                    HttpResponse response = client.execute(request, httpcontext);
                    int httpCode = response.getStatusLine().getStatusCode();
                    if (httpCode != 200) {
                        EntityUtils.consume(response.getEntity());
                        throw new Exception("Invalid server response. Code:" +
                                httpCode);
                    }
                    HttpEntity entity = response.getEntity();
                    byte[] downloadedData = EntityUtils.toByteArray(entity);
                    data = downloadedData;
                    downloadedLen = downloadedData.length;
                    contentLength = (int)entity.getContentLength();

                    if (downloadedLen < contentLength) {
                        throw new Exception("Invalid file. Expected len:" +
                                contentLength +
                                "  received:" +
                                downloadedLen);
                    }

                    BraveFrontier.getActivity().getGLView().queueEvent(new DownloadCallbackEvent(obj, data, error));
                    data = null;
                    connMgr.downloadFinished(client);
                    return;
                } catch(Exception ex) {
                    data = null;
                    downloadedLen = 0;
                    contentLength = 0;
                    error = ex.getLocalizedMessage();

                    if (error == null) {
                        error = ex.getClass().getName();
                    }

                    if (error == null) {
                        error = "unknown exception";
                    }
                    ex.printStackTrace();
                    BraveFrontier.getActivity().getGLView().queueEvent(new DownloadCallbackEvent(obj, data, error));
                    data = null;
                    connMgr.downloadFinished(client);
                    return;
                }
            } catch(Throwable ignoredException) {
            }

            data = null;
            downloadedLen = 0;
            contentLength = 0;
            error = "Failed to allocate memory.";

            BraveFrontier.getActivity().getGLView().queueEvent(new DownloadCallbackEvent(obj, data, error));
            data = null;
            connMgr.downloadFinished(this.client);

        } catch(Throwable ex) {
            BraveFrontier.getActivity().getGLView().queueEvent(new DownloadCallbackEvent(obj, data, error));
            data = null;
            connMgr.downloadFinished(client);
            try {
                throw ex;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }
}
