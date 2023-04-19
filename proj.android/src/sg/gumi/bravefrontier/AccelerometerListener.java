package sg.gumi.bravefrontier;

abstract public interface AccelerometerListener {
    abstract public void onAccelerationChanged(float arg, float arg0, float arg1);
    
    
    abstract public void onShake(float arg);
}
