package sg.gumi.bravefrontier;

public class AccelerometerManager {
    private static android.content.Context aContext;
    private static int interval = 200;
    private static sg.gumi.bravefrontier.AccelerometerListener listener;
    private static boolean running = false;
    private static android.hardware.Sensor sensor;
    private static android.hardware.SensorEventListener sensorEventListener;
    private static android.hardware.SensorManager sensorManager;
    private static Boolean supported;
    private static float threshold = 20f;
    
    static {
        sensorEventListener = (android.hardware.SensorEventListener)(Object)new sg.gumi.bravefrontier.AccelerometerManager$1();
    }
    
    public AccelerometerManager() {
    }
    
    static float access$000() {
        return threshold;
    }
    
    static int access$100() {
        return interval;
    }
    
    static sg.gumi.bravefrontier.AccelerometerListener access$200() {
        return listener;
    }
    
    public static void configure(int i, int i0) {
        threshold = (float)i;
        interval = i0;
    }
    
    public static boolean isListening() {
        return running;
    }
    
    public static boolean isSupported(android.content.Context a) {
        aContext = a;
        if (supported == null) {
            if (a == null) {
                supported = Boolean.FALSE;
            } else {
                android.hardware.SensorManager a0 = (android.hardware.SensorManager)a.getSystemService("sensor");
                sensorManager = a0;
                supported = new Boolean(a0.getSensorList(1).size() > 0);
            }
        }
        return supported.booleanValue();
    }
    
    public static void startListening(sg.gumi.bravefrontier.AccelerometerListener a) {
        android.hardware.SensorManager a0 = (android.hardware.SensorManager)aContext.getSystemService("sensor");
        sensorManager = a0;
        java.util.List a1 = a0.getSensorList(1);
        if (a1.size() > 0) {
            android.hardware.Sensor a2 = (android.hardware.Sensor)a1.get(0);
            sensor = a2;
            running = sensorManager.registerListener(sensorEventListener, a2, 1);
            listener = a;
        }
    }
    
    public static void startListening(sg.gumi.bravefrontier.AccelerometerListener a, int i, int i0) {
        sg.gumi.bravefrontier.AccelerometerManager.configure(i, i0);
        sg.gumi.bravefrontier.AccelerometerManager.startListening(a);
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
