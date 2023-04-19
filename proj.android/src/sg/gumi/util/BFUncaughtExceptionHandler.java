package sg.gumi.util;

final public class BFUncaughtExceptionHandler implements Thread$UncaughtExceptionHandler {
    final private Thread$UncaughtExceptionHandler defaultUEH;
    
    public BFUncaughtExceptionHandler() {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }
    
    public void uncaughtException(Thread a, Throwable a0) {
        label2: {
            java.io.RandomAccessFile a1 = null;
            label0: {
                try {
                    java.io.File a2 = new java.io.File(android.os.Environment.getExternalStorageDirectory(), "bfcrash.txt");
                    a1 = new java.io.RandomAccessFile(a2, "rw");
                    label1: {
                        try {
                            a1.setLength(0L);
                            a1.close();
                            break label1;
                        } catch(Throwable ignoredException) {
                        }
                        break label0;
                    }
                    Runtime a3 = Runtime.getRuntime();
                    StringBuilder a4 = new StringBuilder();
                    a4.append("logcat -d -v time -f ");
                    a4.append(a2.getAbsolutePath());
                    a3.exec(a4.toString());
                } catch(Throwable ignoredException0) {
                    a1 = null;
                    break label0;
                }
                break label2;
            }
            if (a1 != null) {
                try {
                    a1.close();
                } catch(Throwable ignoredException1) {
                }
            }
        }
        this.defaultUEH.uncaughtException(a, a0);
    }
}
