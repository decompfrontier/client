package sg.gumi.util;

import android.os.Environment;

import java.io.File;
import java.io.RandomAccessFile;

final public class BFUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    final private Thread.UncaughtExceptionHandler defaultUEH;
    
    public BFUncaughtExceptionHandler() {
        defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
    }
    
    public void uncaughtException(Thread thread, Throwable exception) {
        RandomAccessFile file = null;
        try {
            File fileName = new File(Environment.getExternalStorageDirectory(), "bfcrash.txt");
            file = new RandomAccessFile(fileName, "rw");
            try {
                file.setLength(0L);
                file.close();
                file = null;
                Runtime logcat = Runtime.getRuntime();
                logcat.exec("logcat -d -v time -f " +
                        fileName.getAbsolutePath());
            } catch(Throwable ignoredException) {
            }
            if (file != null) {
                try {
                    file.close();
                } catch(Throwable ignoredException1) {
                }
            }
            defaultUEH.uncaughtException(thread, exception);
        } catch(Throwable ignoredException0) {
            if (file != null) {
                try {
                    file.close();
                } catch(Throwable ignoredException1) {
                }
            }
        }
        defaultUEH.uncaughtException(thread, exception);
    }
}
