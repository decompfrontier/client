package sg.gumi.util;

final public class BFConfig$Platform extends Enum {
    final private static sg.gumi.util.BFConfig$Platform[] $VALUES;
    final public static sg.gumi.util.BFConfig$Platform PLATFORM_AMAZON;
    final public static sg.gumi.util.BFConfig$Platform PLATFORM_GOOGLE;
    final public static sg.gumi.util.BFConfig$Platform PLATFORM_SAMSUNG;
    
    static {
        PLATFORM_GOOGLE = new sg.gumi.util.BFConfig$Platform("PLATFORM_GOOGLE", 0);
        PLATFORM_AMAZON = new sg.gumi.util.BFConfig$Platform("PLATFORM_AMAZON", 1);
        sg.gumi.util.BFConfig$Platform a = new sg.gumi.util.BFConfig$Platform("PLATFORM_SAMSUNG", 2);
        PLATFORM_SAMSUNG = a;
        sg.gumi.util.BFConfig$Platform[] a0 = new sg.gumi.util.BFConfig$Platform[3];
        a0[0] = PLATFORM_GOOGLE;
        a0[1] = PLATFORM_AMAZON;
        a0[2] = a;
        $VALUES = a0;
    }
    
    private BFConfig$Platform(String s, int i) {
        super(s, i);
    }
    
    public static sg.gumi.util.BFConfig$Platform valueOf(String s) {
        return (sg.gumi.util.BFConfig$Platform)Enum.valueOf(sg.gumi.util.BFConfig$Platform.class, s);
    }
    
    public static sg.gumi.util.BFConfig$Platform[] values() {
        return $VALUES.clone();
    }
}
