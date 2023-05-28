package sg.gumi.bravefrontier;

final class GameHelper$GameHelperTask extends android.os.AsyncTask {
    final static int TASK_TYPE_SIGN_IN_FAILED = 2;
    final static int TASK_TYPE_SIGN_IN_SUCCEEDED = 1;
    final static int TASK_TYPE_SIGN_OUT_SUCCEEDED = 3;
    private int mAttempts;
    int mTaskType;
    final sg.gumi.bravefrontier.GameHelper this$0;
    
    private GameHelper$GameHelperTask(sg.gumi.bravefrontier.GameHelper a) {
        this.this$0 = a;
        super();
        this.mAttempts = 3;
    }
    
    GameHelper$GameHelperTask(sg.gumi.bravefrontier.GameHelper a, sg.gumi.bravefrontier.GameHelper$1 a0) {
        this(a);
    }
    
    protected Object doInBackground(Object[] a) {
        while(true) {
            if (!((android.os.AsyncTask)this).isCancelled() && this.mAttempts > 0) {
                try {
                    Thread.sleep(100L);
                } catch(Throwable ignoredException) {
                }
                ((android.os.AsyncTask)this).publishProgress((Object[])null);
                continue;
            }
            return null;
        }
    }
    
    protected void onProgressUpdate(Object[] a) {
        try {
            int i = this.mTaskType;
            if (i == 1) {
                sg.gumi.bravefrontier.GameHelper.access$200(this.this$0);
            } else if (i == 2) {
                sg.gumi.bravefrontier.GameHelper.access$300(this.this$0);
            } else if (i == 3) {
                sg.gumi.bravefrontier.GameHelper.access$400(this.this$0);
            }
            this.mAttempts = 0;
            sg.gumi.bravefrontier.GameHelper.access$500(this.this$0);
        } catch(Throwable ignoredException) {
            this.mAttempts = this.mAttempts - 1;
        }
    }
}
