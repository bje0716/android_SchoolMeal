package com.grapefruit.schoolmeal.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.grapefruit.schoolmeal.callbacks.OnDoneCookieCallback;

public class Util {

    private static boolean doneCook = true;

    public static boolean isNetwork(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public static void getCookie(Context context, String location, OnDoneCookieCallback callback) {
        doneCook = false;
        WebView webView = new WebView(context);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (!doneCook) {
                    String cookie = CookieManager.getInstance().getCookie(url);
                    callback.onDone(cookie);
                    doneCook = true;
                }
            }
        });
        webView.loadUrl(Constants.DEFAULT_URL + location + Constants.DEFAULT_JSP);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
