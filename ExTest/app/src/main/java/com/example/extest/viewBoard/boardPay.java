package com.example.extest.viewBoard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.extest.R;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.URISyntaxException;

public class boardPay extends AppCompatActivity {
    WebView webView;
    private final String APP_SCHEME = "boardpay";
    private static final String TAG = "lecture";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_pay);

        webView = findViewById(R.id.webviewPay);

        webView.clearCache(true);                                      // 캐쉬 지우기
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE); // 캐쉬 사용하지 않기
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.setWebViewClient(new myWebView());
        webView.loadUrl(getString(R.string.server_addr) + "/android/androidpay");

        webView.setHorizontalScrollBarEnabled(false); // 세로 scroll 제거
        webView.setVerticalScrollBarEnabled(false);    // 가로 scroll 제거
        webView.setWebChromeClient(new myWebChromeClient());

        Glide.with(this);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String url = intent.toString();

        if (url.startsWith(APP_SCHEME)) {
            // "iamportapp://https://pgcompany.com/foo/bar"와 같은 형태로 들어옴
            String redirectURL = url.substring(APP_SCHEME.length() + "://".length());
            Log.d(TAG, "redirectURL :  " + redirectURL);
            webView.loadUrl(redirectURL);
        }
    }


    // 안드로이드 자체 WebView가 아닌 내가 만든 WebView 직접 사용한다고 명시
    private class myWebView extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
                Intent intent = null;

                try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME); //IntentURI처리
                    Uri uri = Uri.parse(intent.getDataString());

                    getApplicationContext().startActivity(new Intent(Intent.ACTION_VIEW, uri)); //해당되는 Activity 실행
                    return true;
                } catch (URISyntaxException ex) {
                    return false;
                } catch (ActivityNotFoundException e) {
                    if (intent == null) return false;

                    //if ( handleNotFoundPaymentScheme(intent.getScheme()) )  return true; //설치되지 않은 앱에 대해 사전 처리(Google Play이동 등 필요한 처리)

                    String packageName = intent.getPackage();
                    if (packageName != null) { //packageName이 있는 경우에는 Google Play에서 검색을 기본
                        getApplicationContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                        return true;
                    }

                    return false;
                }
            }

            return false;
        }
    }

    // 웹뷰에서 자바스크립트의 Alert/Confirm을 보여 줄 수 있도록 한다.
    private class myWebChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message,
                                 final android.webkit.JsResult result) {
            result.confirm();

            new AlertDialog.Builder(view.getContext())
                    //.setIcon(R.drawable.icon);
                    //.setTitle(R.string.title_activity_main)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new AlertDialog.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setCancelable(true)
                    .create()
                    .show();

            return true;
        }

        ;

        @Override
        public boolean onJsConfirm(WebView view, String url, String message,
                                   final android.webkit.JsResult result) {
            new AlertDialog.Builder(view.getContext())
                    //.setTitle(R.string.title_activity_main)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.confirm();
                                }
                            })
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    result.cancel();
                                }
                            })
                    .setCancelable(false)
                    .create()
                    .show();

            return true;
        }
        ;


    }
}
