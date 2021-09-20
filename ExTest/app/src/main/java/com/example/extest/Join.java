package com.example.extest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
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

import java.util.regex.Pattern;

public class Join extends AppCompatActivity {
    private  static final String TAG = "lecture";
    EditText userid;
    EditText userpwd;
    EditText userpwdCheck1;
    EditText userpwdCheck2;
    EditText username;
    EditText useremail;
    TextView pCheckMessage;
    TextView pCheckMessage2;
    TextView textExistId;
    TextView submitCheck;

    String marketcheck;
    int checkId = 0;
    int checkpwd= 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        userid = findViewById(R.id.textId);
        userpwdCheck1 = (EditText)findViewById(R.id.textPassword);
        userpwdCheck2 = (EditText)findViewById(R.id.textPasswordCheck);
        pCheckMessage = findViewById(R.id.pCheckMessage);
        pCheckMessage2 =findViewById(R.id.pCheckMessage2);
        textExistId = findViewById(R.id.textExistId);
        submitCheck = findViewById(R.id.submitCheck);

        username = findViewById(R.id.textName);
        useremail = findViewById(R.id.textEmail);

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
                    submitCheck.setText("");
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
    public void onBtnJoin(View v){

        if(pCheckMessage.length()!=0||checkpwd==0){
            submitCheck.setText("비밀번호가 맞지않습니다.");

        }
        else if(checkId == 0){
            submitCheck.setText("아이디 중복체크를 해주세요.");

        }
        else
            {


           String sUrl = getString(R.string.server_addr) + "/security/join";
            try {
                ContentValues values = new ContentValues();

                values.put("userID", userid.getText().toString());
               values.put("userPassword1", userpwdCheck1.getText().toString());
                values.put("userName", username.getText().toString());
                values.put("usereMail", useremail.getText().toString());
                values.put("marketCheck", marketcheck);
                values.put("authority","ROLE_USER");



               NetworkTask networkTask = new NetworkTask(sUrl, values);
               networkTask.execute();
           } catch(Exception e) {
                e.printStackTrace();
            }
             }
    }

    //아이디 중복 체크
    public void onBtnIdCheck(View v){


        String sUrl = getString(R.string.server_addr) + "/security/checkid";

        try {
            ContentValues values = new ContentValues();
            values.put("userID", userid.getText().toString());


            NetworkTask networkTask = new NetworkTask(sUrl, values);
            networkTask.execute();
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
        String result = "";
            Log.d(TAG,s);
            try{
                JSONObject jobj = new JSONObject(s);
                 result = jobj.getString("desc");

            } catch(Exception e){
                e.printStackTrace();
            }

            if(result.equals("회원가입에 성공하였습니다.")){
                Intent intent = new Intent(Join.this, pHomePage.class);
                startActivity(intent);
                finish();
            }

            if(result.equals("사용가능한 아이디입니다.")){
                checkId = 1;
                textExistId.setTextColor(Color.parseColor("#3F51B5"));
            } else {
                checkId = 0;
                textExistId.setTextColor(Color.parseColor("#F44336"));
            }


                textExistId.setText(result);




        }
    }

    @Override
    public void onBackPressed(){

        Intent intent = new Intent(Join.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}