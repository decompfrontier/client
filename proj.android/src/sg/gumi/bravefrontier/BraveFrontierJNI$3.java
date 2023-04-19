package sg.gumi.bravefrontier;

class BraveFrontierJNI$3 implements Runnable {
    final String val$_stringToCopy;
    
    BraveFrontierJNI$3(String s) {
        this.val$_stringToCopy = s;
        super();
    }
    
    public void run() {
        try {
            ((android.content.ClipboardManager)((android.app.Activity)sg.gumi.bravefrontier.BraveFrontierJNI.access$000()).getBaseContext().getSystemService("clipboard")).setPrimaryClip(android.content.ClipData.newPlainText((CharSequence)(Object)this.val$_stringToCopy, (CharSequence)(Object)this.val$_stringToCopy));
        } catch(Throwable a) {
            a.printStackTrace();
        }
    }
}
