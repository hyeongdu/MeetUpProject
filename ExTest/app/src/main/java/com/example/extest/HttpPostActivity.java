package com.example.extest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PrivateKey;


public class HttpPostActivity extends AppCompatActivity {
    private  static final String TAG = "lecture";

    private String userid;
    private String userpwd;

    TextView tvHtml2;
    WebView webView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_get);
//
        tvHtml2 = findViewById(R.id.tvHtml2);
        webView = findViewById(R.id.webView2);

        Intent intent = getIntent();
       String s = intent.getStringExtra("result");


        tvHtml2.setText(s);
        webView.loadData(s, "text/html; charset=UTF-8", null);


    }


}