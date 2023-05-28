package sg.gumi.bravefrontier;

class BraveFrontierJNI$7 implements Runnable {
    final String val$body;
    final String val$btn1Text;
    final String val$btn2Text;
    final String val$title;
    
    BraveFrontierJNI$7(String s, String s0, String s1, String s2) {
        this.val$body = s;
        this.val$title = s0;
        this.val$btn1Text = s1;
        this.val$btn2Text = s2;
        super();
    }
    
    public void run() {
        android.content.Context a = ((android.app.Activity)sg.gumi.bravefrontier.BraveFrontierJNI.getActivity()).getApplicationContext();
        android.app.AlertDialog$Builder a0 = new android.app.AlertDialog$Builder((android.content.Context)sg.gumi.bravefrontier.BraveFrontierJNI.getActivity());
        android.widget.TextView a1 = new android.widget.TextView(a);
        a1.setPadding(10, 10, 10, 10);
        a1.setGravity(17);
        a1.setTextColor(-1);
        a1.setTextSize(20f);
        a0.setCustomTitle((android.view.View)a1);
        if (sg.gumi.util.BFConfig.PLATFORM != sg.gumi.util.BFConfig.PLATFORM_AMAZON) {
            a0.setMessage((CharSequence)(Object)this.val$body);
        } else {
            a0.setMessage((CharSequence)(Object)sg.gumi.bravefrontier.BraveFrontierJNI.replaceGooglePlayStoreName(this.val$body, "Amazon Appstore", "Amazon"));
        }
        a1.setText((CharSequence)(Object)this.val$title);
        a0.setPositiveButton((CharSequence)(Object)this.val$btn1Text, (android.content.DialogInterface$OnClickListener)(Object)new sg.gumi.bravefrontier.BraveFrontierJNI$7$1(this, a));
        a0.setNegativeButton((CharSequence)(Object)this.val$btn2Text, (android.content.DialogInterface$OnClickListener)(Object)new sg.gumi.bravefrontier.BraveFrontierJNI$7$2(this));
        android.app.AlertDialog a2 = a0.create();
        a2.show();
        try {
            ((android.widget.TextView)a2.findViewById(16908299)).setGravity(17);
        } catch(Exception a3) {
            a3.printStackTrace();
        }
    }
}
