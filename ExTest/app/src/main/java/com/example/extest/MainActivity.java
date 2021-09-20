package com.example.extest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.extest.mainpage.pHomePage;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로가기 버튼을 누를때 표시
    Toast toast;

    private  static final String TAG = "lecture";
    Button btnGetAct;
    EditText userid;
    EditText userpwd;
    Dialog dialog;
    String a;
    final static String STANDARD_JOIN = "0";
    final static String KAKAO_JOIN = "1";

    private LoginButton mKakaoLoginBtn;
    private LoginButton mKakaoLoginBtnBasic;
    private KakaoLogin.KakaoSessionCallback sessionCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnGetAct = findViewById(R.id.button);
        userid = findViewById(R.id.userid);
        userpwd = findViewById(R.id.userpwd);

        mKakaoLoginBtn = findViewById(R.id.btn_kakao_login);


        a = userpwd.getText().toString();

        mKakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mKakaoLoginBtnBasic.performClick();
            }
        });


    //잘 모르겠넹//
        if (HasKakaoSession()) {
            sessionCallback = new KakaoLogin().new KakaoSessionCallback(getApplicationContext(), MainActivity.this);
            Session.getCurrentSession().addCallback(sessionCallback);
        } else if (HasKakaoSession()) {
            sessionCallback = new KakaoLogin(). new KakaoSessionCallback(getApplicationContext(), MainActivity.this);
            Session.getCurrentSession().addCallback(sessionCallback);
            Session.getCurrentSession().checkAndImplicitOpen();
        }

//        getHashKey();


    }@Override
    protected void onDestroy() {
        super.onDestroy();

        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    private boolean HasKakaoSession() {
        if (!Session.getCurrentSession().checkAndImplicitOpen()) {
            return false;
        }
        return true;
    }

    public void directToSecondActivity(Boolean result) {
        Intent intent = new Intent(MainActivity.this, pHomePage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (result) {
            Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
            startActivity(intent);;
            finish();
        }
    }


//
//    private void getHashKey(){
//        PackageInfo packageInfo = null;
//        try {
//            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
//        if (packageInfo == null)
//            Log.e("lecture", "KeyHash:null");
//
//        for (Signature signature : packageInfo.signatures) {
//            try {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("lecture", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            } catch (NoSuchAlgorithmException e) {
//                Log.e("lecture", "Unable to get MessageDigest. signature=" + signature, e);
//            }
//        }
//    }
    public void onBtnJoin(View v){
        Intent intent = new Intent(MainActivity.this, Join.class);
        startActivity(intent);
        finish();
    }

    public void onBtnAgree(View v){
        Intent intent = new Intent(MainActivity.this, AgreeCheck.class);
        intent.putExtra("processCheck",STANDARD_JOIN);
        startActivity(intent);
        finish();
    }


    public void onBtnGetAct(View v) {

        String sUrl = getString(R.string.server_addr) + "/android/userlist";

        try {
            ContentValues values = new ContentValues();
            values.put("userid", userid.getText().toString());
            values.put("userpwd", userpwd.getText().toString());
            NetworkTask networkTask = new NetworkTask(sUrl, values);
            networkTask.execute();
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class NetworkTask extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();

            result = requestHttpURLConnection.request(url, values);

            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Intent intent = new Intent(MainActivity.this, pHomePage.class);
            startActivity(intent);
            finish();
        }
    }

    public void kakaoIdCheck(List userInfo, boolean result){

        Intent intent = new Intent(MainActivity.this, AgreeCheck.class);
            Log.d(TAG,"kakaoIdCheck");
            Toast.makeText(getApplicationContext(), "카카오 회원가입!", Toast.LENGTH_SHORT).show();
            intent.putExtra("processCheck",KAKAO_JOIN);
            startActivity(intent);
            finish();
        }

    @Override
    public void onBackPressed() {

        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지났으면 Toast Show
        // 2000 milliseconds = 2 seconds
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간에 2초를 더해 현재시간과 비교 후
        // 마지막으로 뒤로가기 버튼을 눌렀던 시간이 2초가 지나지 않았으면 종료
        // 현재 표시된 Toast 취소
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }



}