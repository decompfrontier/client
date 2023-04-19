package sg.gumi.bravefrontier;

class BraveFrontierJNI$4 implements Runnable {
    BraveFrontierJNI$4() {
    }
    
    public void run() {
        String s = sg.gumi.bravefrontier.BraveFrontierJNI.getWritablePath();
        label0: {
            java.io.InputStream a = null;
            label2: {
                label1: {
                    try {
                        a = null;
                        a = ((android.app.Activity)sg.gumi.bravefrontier.BraveFrontierJNI.access$000()).getResources().getAssets().open("NoDLCRes.zip");
                        java.util.zip.ZipInputStream a0 = new java.util.zip.ZipInputStream(a);
                        while(true) {
                            java.util.zip.ZipEntry a1 = a0.getNextEntry();
                            if (a1 == null) {
                                break;
                            }
                            if (!a1.isDirectory()) {
                                StringBuilder a2 = new StringBuilder();
                                a2.append(s);
                                a2.append("/");
                                a2.append(a1.getName().replace((CharSequence)(Object)"NoDLCRes", (CharSequence)(Object)""));
                                java.io.FileOutputStream a3 = new java.io.FileOutputStream(a2.toString());
                                byte[] a4 = new byte[4096];
                                while(true) {
                                    int i = a0.read(a4);
                                    if (i <= 0) {
                                        a0.closeEntry();
                                        a3.close();
                                        break;
                                    } else {
                                        a3.write(a4, 0, i);
                                    }
                                }
                            }
                        }
                        a0.close();
                        sg.gumi.bravefrontier.BraveFrontierJNI.s_UnzipStatus = 0;
                    } catch(Throwable ignoredException) {
                        break label1;
                    }
                    if (a == null) {
                        break label0;
                    }
                    break label2;
                }
                if (sg.gumi.bravefrontier.BraveFrontierJNI.s_UnzipStatus != 0) {
                    sg.gumi.bravefrontier.BraveFrontierJNI.s_UnzipStatus = 2;
                }
                if (a == null) {
                    break label0;
                }
            }
            try {
                a.close();
            } catch(Throwable ignoredException0) {
            }
        }
    }
}
