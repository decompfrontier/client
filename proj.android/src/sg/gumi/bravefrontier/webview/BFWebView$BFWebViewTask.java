package sg.gumi.bravefrontier.webview;

final class BFWebView$BFWebViewTask implements Runnable {
    final static int TASK_TYPE_CLOSE_BROWSER_BUTTON = 103;
    final static int TASK_TYPE_CLOSE_WEBVIEW_STEP_1 = 3;
    final static int TASK_TYPE_CLOSE_WEBVIEW_STEP_2 = 4;
    final static int TASK_TYPE_PLAY_YOUTUBE_VIDEO = 5;
    final static int TASK_TYPE_SET_BROWSER_BUTTON_VISIBILITY = 102;
    final static int TASK_TYPE_SET_WEBVIEW_VISIBILITY = 2;
    final static int TASK_TYPE_SHOW_BROWSER_BUTTON = 101;
    final static int TASK_TYPE_SHOW_WEBVIEW = 1;
    Object param;
    int taskType;
    final sg.gumi.bravefrontier.webview.BFWebView this$0;
    
    private BFWebView$BFWebViewTask(sg.gumi.bravefrontier.webview.BFWebView a) {
        this.this$0 = a;
        super();
        this.param = null;
    }
    
    BFWebView$BFWebViewTask(sg.gumi.bravefrontier.webview.BFWebView a, sg.gumi.bravefrontier.webview.BFWebView$1 a0) {
        this(a);
    }
    
    public void run() {
        int i = this.taskType;
        if (i == 1) {
            sg.gumi.bravefrontier.webview.BFWebView.access$100(this.this$0, this.param);
        } else if (i == 2) {
            sg.gumi.bravefrontier.webview.BFWebView.access$200(this.this$0, this.param);
        } else if (i == 3) {
            sg.gumi.bravefrontier.webview.BFWebView.access$300(this.this$0);
        } else if (i == 4) {
            sg.gumi.bravefrontier.webview.BFWebView.access$400(this.this$0);
        } else if (i == 5) {
            sg.gumi.bravefrontier.webview.BFWebView.access$500(this.this$0, this.param);
        } else {
            switch(i) {
                case 103: {
                    sg.gumi.bravefrontier.webview.BFWebView.access$800(this.this$0);
                    break;
                }
                case 102: {
                    sg.gumi.bravefrontier.webview.BFWebView.access$700(this.this$0, this.param);
                    break;
                }
                case 101: {
                    sg.gumi.bravefrontier.webview.BFWebView.access$600(this.this$0, this.param);
                    break;
                }
            }
        }
    }
}
