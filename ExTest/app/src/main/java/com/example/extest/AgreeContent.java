package com.example.extest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;


public class AgreeContent extends AppCompatActivity {
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree_content);

        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        //타이틀 안보이게 하기
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



    }



    @Override
    public void onBackPressed(){

        Intent intent = new Intent(AgreeContent.this, AgreeCheck.class);
        startActivity(intent);
        finish();
    }
}