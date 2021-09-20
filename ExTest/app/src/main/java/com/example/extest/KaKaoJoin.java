package com.example.extest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.extest.mainpage.pHomePage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class KaKaoJoin extends AppCompatActivity {
    private  static final String TAG = "lecture";

    TextView userid;

    EditText userpwdCheck1;
    EditText userpwdCheck2;
    EditText username;
    EditText useremail;
    TextView pCheckMessage;
    TextView pCheckMessage2;
    TextView textExistId;
    TextView submitCheck;

    String userid2;

    String marketcheck;
    int checkId = 0;
    int checkpwd= 0;
    List userInfo = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ka_kao_join);

        userid = findViewById(R.id.textId);
        userpwdCheck1 = (EditText)findViewById(R.id.textPassword);
        userpwdCheck2 = (EditText)findViewById(R.id.textPasswordCheck);
        pCheckMessage = findViewById(R.id.submitCheck);
        pCheckMessage2 =findViewById(R.id.pCheckMessage2);
        textExistId = findViewById(R.id.textExistId);
        submitCheck = findViewById(R.id.submitCheck);

        username = findViewById(R.id.textName);
        useremail = findViewById(R.id.textEmail);

        GlobalHelper mGlobalHelper;
        mGlobalHelper = new GlobalHelper();
        userInfo = mGlobalHelper.getGlobalUserLoginInfo();

        Log.d(TAG, "카카오 아이디 "+String.valueOf(userInfo.get(0)));
        userid.setText((String)userInfo.get(0));
        userid2 = (String)userInfo.get(0);

        //마케팅 정보 동의 체크값
        Intent intent = getIntent();
        marketcheck = intent.getStringExtra("marketcheck");


        userpwdCheck1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!Pattern.matches("^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,20}$", userpwdCheck1.getText().toString()))
                {
                    pCheckMessage2.setText("특수문자, 숫자, 대소문자를 넣어 8~20자 로 만들어주세요.");
                    checkpwd =0;
                } else {
                    pCheckMessage2.setText("");
                    checkpwd =1;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        userpwdCheck2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(userpwdCheck1.getText().toString().equals(userpwdCheck2.getText().toString()))
                {
                    pCheckMessage.setText("");
                } else {
                    pCheckMessage.setText("비밀번호가 일치하지 않습니다");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    // 회원가입
    public void onBtnKakaoJoin(View v){


        Log.d(TAG, String.valueOf(pCheckMessage.length()));

        if(pCheckMessage.length()!=0||checkpwd==0){
            Log.d(TAG,"dddddddddddd");
            submitCheck.setText("비밀번호가 맞지않습니다.");

        }

        else
        {

            Log.d(TAG,"else");
            String sUrl = getString(R.string.server_addr) + "/security/join";
            try {
                ContentValues values = new ContentValues();

                Log.d(TAG,"1");

                values.put("userID", userid2);
                Log.d(TAG,"2");
                values.put("userPassword1", userpwdCheck1.getText().toString());
                Log.d(TAG,"3");
                values.put("userName", username.getText().toString());
                Log.d(TAG,"4");
                values.put("usereMail", useremail.getText().toString());
                Log.d(TAG,"5");
                values.put("marketCheck", marketcheck);
                Log.d(TAG,"6");
                values.put("authority","ROLE_USER");
                Log.d(TAG,userid2);




                NetworkTask networkTask = new NetworkTask(sUrl, values);
                Log.d(TAG,"222222222222222222");
                networkTask.execute();
            } catch(Exception e) {
                e.printStackTrace();
            }
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
            Log.d(TAG,"33333333333333333333");
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();

            result = requestHttpURLConnection.request(url, values);

            return result;

        }

        @Override
        protected void onPostExecute(String s) {
            String result = "";
            Log.d(TAG, s);
            try {
                JSONObject jobj = new JSONObject(s);
                result = jobj.getString("desc");

            } catch (Exception e) {
                e.printStackTrace();
            }

            if (result.equals("회원가입에 성공하였습니다.")) {
                Intent intent = new Intent(KaKaoJoin.this, pHomePage.class);
                startActivity(intent);
                finish();
            }

            if (result.equals("사용가능한 아이디입니다.")) {
                checkId = 1;
            } else {
                checkId = 0;
            }


        }

    }
}