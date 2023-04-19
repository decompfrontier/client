package sg.gumi.util;

final public class BFConfig {
    final public static sg.gumi.util.BFConfig$Platform PLATFORM;
    final public static sg.gumi.util.BFConfig$Platform PLATFORM_AMAZON;
    final public static sg.gumi.util.BFConfig$Platform PLATFORM_GOOGLE;
    final public static sg.gumi.util.BFConfig$Platform PLATFORM_SAMSUNG;
    
    static {
        PLATFORM_AMAZON = sg.gumi.util.BFConfig$Platform.PLATFORM_AMAZON;
        PLATFORM_GOOGLE = sg.gumi.util.BFConfig$Platform.PLATFORM_GOOGLE;
        PLATFORM_SAMSUNG = sg.gumi.util.BFConfig$Platform.PLATFORM_SAMSUNG;
        PLATFORM = sg.gumi.util.BFConfig$Platform.PLATFORM_GOOGLE;
    }
    
    public BFConfig() {
    }
}
