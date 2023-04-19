package sg.gumi.util;

final class AsyncFileLoad$DownloadCallbackEvent implements Runnable {
    final byte[] data;
    final String error;
    final long obj;
    
    AsyncFileLoad$DownloadCallbackEvent(long j, byte[] a, String s) {
        this.obj = j;
        this.data = a;
        if (s != null) {
            String s0 = s.trim();
            s = (s0.isEmpty()) ? null : s0;
        }
        this.error = s;
    }
    
    public void run() {
        try {
            sg.gumi.bravefrontier.BraveFrontierJNI.nativeDownloadCallback(this.obj, this.data, this.error);
        } catch(Throwable ignoredException) {
        }
    }
}
