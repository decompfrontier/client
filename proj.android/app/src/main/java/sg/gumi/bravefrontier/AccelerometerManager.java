package sg.gumi.bravefrontier;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;

import java.util.List;

public class AccelerometerManager {

    static class Listener implements SensorEventListener {
        private float force;
        private long lastShake;
        private long lastUpdate;
        private float lastX;
        private float lastY;
        private float lastZ;
        private long now;
        private long timeDiff;
        private float x;
        private float y;
        private float z;

        Listener() {
            now = 0L;
            timeDiff = 0L;
            lastUpdate = 0L;
            lastShake = 0L;
            x = 0.0f;
            y = 0.0f;
            z = 0.0f;
            lastX = 0.0f;
            lastY = 0.0f;
            lastZ = 0.0f;
            force = 0.0f;
        }

        public void onAccuracyChanged(Sensor a, int i) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            try {
                now = sensorEvent.timestamp;
                x = sensorEvent.values[0];
                y = sensorEvent.values[1];
                z = sensorEvent.values[2];
                if (lastUpdate != 0L) {
                    long j0 = now - lastUpdate;
                    timeDiff = j0;
                    if (j0 > 0L) {
                        force = Math.abs(x + y + z - lastX - lastY - lastZ);
                        if (Float.compare(force, AccelerometerManager.getThreshold()) > 0) {
                            if (now - lastShake >= (long)AccelerometerManager.getInterval()) {
                                AccelerometerManager.getListener().onShake(force);
                            }
                            lastShake = now;
                        }
                        lastX = x;
                        lastY = y;
                        lastZ = z;
                        lastUpdate = now;
                    }
                } else {
                    lastUpdate = sensorEvent.timestamp;
                    lastShake = sensorEvent.timestamp;
                    lastX = sensorEvent.values[0];
                    lastY = sensorEvent.values[1];
                    lastZ = sensorEvent.values[2];
                }
                AccelerometerManager.getListener().onAccelerationChanged(x, y, z);
            } catch(Throwable ignoredException) {
            }
        }
    }

    private static Context aContext;
    private static int interval = 200;
    private static AccelerometerListener listener;
    private static boolean running = false;
    private static Sensor sensor;
    private static SensorEventListener sensorEventListener;
    private static SensorManager sensorManager;
    private static Boolean supported;
    private static float threshold = 20f;
    
    static {
        sensorEventListener = new Listener();
    }
    
    public AccelerometerManager() {
    }
    
    static float getThreshold() {
        return threshold;
    }
    
    static int getInterval() {
        return interval;
    }
    
    static AccelerometerListener getListener() {
        return listener;
    }
    
    public static void configure(int threshold, int interval) {
        AccelerometerManager.threshold = (float)threshold;
        AccelerometerManager.interval = interval;
    }
    
    public static boolean isListening() {
        return running;
    }
    
    public static boolean isSupported(Context context) {
        aContext = context;
        if (supported == null) {
            if (context == null) {
                supported = false;
            } else {
                sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
                supported = sensorManager.getSensorList(1).size() > 0;
            }
        }
        return supported;
    }
    
    public static void startListening(AccelerometerListener listener) {
        sensorManager = (SensorManager)aContext.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(1);
        if (sensors.size() > 0) {
            sensor = sensors.get(0);
            running = sensorManager.registerListener(sensorEventListener, sensor, 1);
            AccelerometerManager.listener = listener;
        }
    }
    
    public static void startListening(AccelerometerListener listener, int threshold, int interval) {
        AccelerometerManager.configure(threshold, interval);
        AccelerometerManager.startListening(listener);
    }
    
    public static void stopListening() {
        running = false;
        if (sensorManager != null && sensorEventListener != null) {
            try {
                sensorManager.unregisterListener(sensorEventListener);
            } catch(Exception ignoredException) {
            }
        }
    }
}
