package sg.gumi.bravefrontier;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import org.cocos2dx.lib.Cocos2dxHelper;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public class Facebook {

    final static public class FacebookNativeCallback implements Runnable {
        final public static int GET_FRIEND_LIST_FAIL = 4;
        final public static int GET_FRIEND_LIST_PERMISSION = 7;
        final public static int GET_FRIEND_LIST_RETRY = 8;
        final public static int GET_FRIEND_LIST_SUCCESS = 3;
        final public static int INVITE_FRIEND_FAIL = 6;
        final public static int INVITE_FRIEND_SUCCESS = 5;
        final public static int LOGIN_FAIL = 2;
        final public static int LOGIN_SUCCESS = 1;
        final int callback;
        final Object param;

        public FacebookNativeCallback(int i) {
            this.callback = i;
            this.param = null;
        }

        public FacebookNativeCallback(int status, Object param) {
            this.callback = status;
            this.param = param;
        }

        public void run() {
            if (!Cocos2dxHelper.isNativeLibraryLoaded()) {
                return;
            }
            if (callback == LOGIN_SUCCESS) {
                Facebook.onLoginSuccess();
            } else if (callback == LOGIN_FAIL) {
                Facebook.onLoginFail((param == null) ? "" : param.toString());
            }
        }

        public void startCall() {
            BraveFrontier.getActivity().getGLView().queueEvent(this);
        }
    }

    static class RegisterCallback implements FacebookCallback {
        final Facebook fb;

        RegisterCallback(Facebook fb) {
            super();
            this.fb = fb;
        }

        public void onCancel() {
            new FacebookNativeCallback(2, "Canceled").startCall();
        }

        public void onError(FacebookException ex) {
            new FacebookNativeCallback(2, ex.toString()).startCall();
        }

        public void onSuccess(LoginResult res) {
            new FacebookNativeCallback(1).startCall();
        }

        public void onSuccess(Object res) {
            onSuccess((LoginResult)res);
        }
    }

    private static CallbackManager callbackManager;
    private static BraveFrontier mActivity;
    private static LoginManager mLoginManager;
    private static int offset;
    public static Facebook sFacebook;
    private AppEventsLogger appEventsLogger;
    private Bundle feedParams;
    private java.util.HashMap[] m_openGraphObjectsArr;

    public Facebook(BraveFrontier activity) {
        feedParams = null;
        sFacebook = this;
        mActivity = activity;
        appEventsLogger = AppEventsLogger.newLogger(activity, activity.getResources().getString(R.string.fb_app_id));
        mLoginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();
        mLoginManager.registerCallback(callbackManager, new RegisterCallback(this));
    }
    
    public static boolean callbackmanagerOnActivityResult(int requestCode, int resultCode, Intent data) {
        return callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    
    public static boolean checkForPermission(List<String> a) {
        return false;
    }
    
    public static String getAccessToken() {
        AccessToken a = AccessToken.getCurrentAccessToken();
        if (a == null) {
            return "";
        }
        return a.getToken();
    }
    
    public static String getUserEmail() {
        return "";
    }
    
    public static String getUserId() {
        AccessToken a = AccessToken.getCurrentAccessToken();
        if (a == null) {
            return "";
        }
        return a.getUserId();
    }
    
    public static boolean isLoggedIn() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return false;
        }
        return true;
    }
    
    public static void login() {
        mLoginManager.logInWithReadPermissions(mActivity, null);
    }
    
    public static void logout() {
        Log.d("ALEX_TEST", "FB LOG OUT");
        mLoginManager.logOut();
    }
    
    native public static void onError(String error);
    
    
    native public static void onFacebookOperationCancelledException();
    
    
    native public static void onLoginFail(String error);
    
    
    native public static void onLoginSuccess();
    
    
    native public static void onUserLogin();
    
    
    public static void trackPurchase(float price, String countryCode) {
        try {
            sFacebook.appEventsLogger.logPurchase(BigDecimal.valueOf(price), Currency.getInstance(countryCode));
        } catch(Throwable ignoredException) {
        }
    }
}
