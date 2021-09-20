package com.example.extest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class AgreeCheck extends AppCompatActivity {
    private  static final String TAG = "lecture";
    CheckBox checkbox1;
    CheckBox checkbox2;
    CheckBox checkbox3;
    CheckBox checkbox4;

    TextView sView;
    TextView tView;
    TextView nCheck;

    String check;
    String processCheck;
    String kakaoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree_check);

        sView = findViewById(R.id.sView);
        tView = findViewById(R.id.tView);
        nCheck = findViewById(R.id.nCheck);


        checkbox1 = (CheckBox)findViewById(R.id.checkAll);
        checkbox2 = (CheckBox)findViewById(R.id.checkFirst);
        checkbox3 = (CheckBox)findViewById(R.id.checkSecond);
        checkbox4 = (CheckBox)findViewById(R.id.checkThird);

        Intent intent = getIntent();
        processCheck = intent.getStringExtra("processCheck");
        Log.d(TAG,"ProcessCheck" + processCheck);




        sView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"체크1");
                Intent intent = new Intent(AgreeCheck.this, AgreeContent.class);
                startActivity(intent);
                finish();
            }
        });

        tView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"체크2");
                Intent intent = new Intent(AgreeCheck.this, AgreeContent.class);
                startActivity(intent);
                finish();
            }
        });

        checkbox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkbox1.isChecked()==true) {
                    checkbox2.setChecked(true);
                    checkbox3.setChecked(true);
                    checkbox4.setChecked(true);
                }

                if(checkbox1.isChecked()==false) {
                    checkbox2.setChecked(false);
                    checkbox3.setChecked(false);
                    checkbox4.setChecked(false);
                }
            }
        });

        checkbox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkbox2.isChecked()==false){
                    allAgreecheckBox();

                }
                allAgreecheckBox2();

            }
        });

        checkbox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox3.isChecked()==false){
                    allAgreecheckBox();

                }
                allAgreecheckBox2();
            }
        });

        checkbox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox4.isChecked()==false){
                    allAgreecheckBox();

                }
                allAgreecheckBox2();
            }
        });


        sView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
        if(checkbox2.isChecked() && checkbox3.isChecked())
            {
                nCheck.setText("");
            }

    }

    public void onBtnJoin(View v){
        if(checkbox2.isChecked() && checkbox3.isChecked())
        {
            if(checkbox4.isChecked()){
                check = "Agree";
            } else {
                check = "nAgree";
            }
            if(processCheck.equals("0")) {
                Intent intent = new Intent(AgreeCheck.this, Join.class);
                intent.putExtra("marketcheck", check);
                startActivity(intent);
                finish();
            } else if(processCheck.equals("1")) {
                Intent intent = new Intent(AgreeCheck.this, KaKaoJoin.class);
                intent.putExtra("marketcheck", check);
                startActivity(intent);
                finish();
            }
        } else {
            Log.d(TAG, String.valueOf(nCheck.length()));
            nCheck.setText("필수 체크 사항을 체크해주세요.");
            Log.d(TAG, String.valueOf(nCheck.length()));
        }

    }
//전체 동의 체크박스 취소
    public void allAgreecheckBox(){
        checkbox1.setChecked(false);
    }
    public void allAgreecheckBox2(){
        if(checkbox2.isChecked() && checkbox3.isChecked() && checkbox4.isChecked())
        {
            checkbox1.setChecked(true);
        }
    }

    @Override
    public void onBackPressed(){

        Intent intent = new Intent(AgreeCheck.this, MainActivity.class);

        startActivity(intent);
        finish();
    }

}