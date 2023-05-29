package sg.gumi.bravefrontier;

public interface AccelerometerListener {
    void onAccelerationChanged(float arg, float arg0, float arg1);
    
    
    void onShake(float value);
}
