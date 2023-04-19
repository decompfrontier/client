package sg.gumi.bravefrontier;

class AccelerometerManager$1 implements android.hardware.SensorEventListener {
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
    
    AccelerometerManager$1() {
        this.now = 0L;
        this.timeDiff = 0L;
        this.lastUpdate = 0L;
        this.lastShake = 0L;
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.lastX = 0.0f;
        this.lastY = 0.0f;
        this.lastZ = 0.0f;
        this.force = 0.0f;
    }
    
    public void onAccuracyChanged(android.hardware.Sensor a, int i) {
    }
    
    public void onSensorChanged(android.hardware.SensorEvent a) {
        try {
            long j = a.timestamp;
            this.now = j;
            float f = a.values[0];
            this.x = f;
            float f0 = a.values[1];
            this.y = f0;
            float f1 = a.values[2];
            this.z = f1;
            if (this.lastUpdate != 0L) {
                long j0 = j - this.lastUpdate;
                this.timeDiff = j0;
                if (j0 > 0L) {
                    float f2 = Math.abs(f + f0 + f1 - this.lastX - this.lastY - this.lastZ);
                    this.force = f2;
                    if (Float.compare(f2, sg.gumi.bravefrontier.AccelerometerManager.access$000()) > 0) {
                        if (this.now - this.lastShake >= (long)sg.gumi.bravefrontier.AccelerometerManager.access$100()) {
                            sg.gumi.bravefrontier.AccelerometerManager.access$200().onShake(this.force);
                        }
                        this.lastShake = this.now;
                    }
                    this.lastX = this.x;
                    this.lastY = this.y;
                    this.lastZ = this.z;
                    this.lastUpdate = this.now;
                }
            } else {
                this.lastUpdate = j;
                this.lastShake = j;
                this.lastX = f;
                this.lastY = f0;
                this.lastZ = f1;
            }
            sg.gumi.bravefrontier.AccelerometerManager.access$200().onAccelerationChanged(this.x, this.y, this.z);
        } catch(Throwable ignoredException) {
        }
    }
}
