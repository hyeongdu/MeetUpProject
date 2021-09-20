package com.example.extest.mainpage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.extest.MainActivity;
import com.example.extest.R;
import com.example.extest.list.BoardList;
import com.google.android.material.tabs.TabLayout;

import net.daum.mf.map.api.MapView;

public class pHomePage extends AppCompatActivity {
    private  static final String TAG = "lecture";
    public static Context mContext;
    //액션바
    MainActivity mainActivity;
    // 마지막으로 뒤로가기 버튼을 눌렀던 시간 저장
    private long backKeyPressedTime = 0;
    // 첫 번째 뒤로가기 버튼을 누를때 표시
    Toast toast;
    Fragment fragment;

    PageAdapter adapter;
    //TabBar
    TabLayout tabLayout;
    ViewPager viewPager;
    BoardList boardList = new BoardList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_home_page);

        tabLayout = findViewById(R.id.tabMenu);
        viewPager = findViewById(R.id.container);

        // TabBar

        adapter = new PageAdapter(getSupportFragmentManager());
        Log.d(TAG,"숫자 : "+ tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




    }






    //뒤로가기
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
    public void onFragmentChange(int index){
        if(index == 0){

            viewPager.setCurrentItem(3);

        }

    }
    public void onFragmentChangeToMain(int index){
        if(index == 0){

            viewPager.setCurrentItem(0);

        }

    }

    public void kakaoMap(){
        MapView mapView = new MapView(this);
    }

//    public void onBoardList(Bundle bundle){
//        adapter = new PageAdapter(getSupportFragmentManager(),bundle);
//        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(3);
//
//    }
}