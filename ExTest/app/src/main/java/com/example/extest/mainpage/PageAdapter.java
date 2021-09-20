package com.example.extest.mainpage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.extest.list.BoardList;
import com.example.extest.writeBoard.WriteBoardView;

public class PageAdapter extends FragmentPagerAdapter {

    int mNumOfTabs;
    Bundle bundle ;
    FragmentManager fm;


    public PageAdapter(FragmentManager fm){
        super(fm);
        this.fm = fm;
    }

    public PageAdapter(FragmentManager fm, Bundle bundle){
        super(fm);
        this.fm = fm;
       this.bundle = bundle;
    }



    @Override
    public Fragment getItem(int position){


        switch (position){
            case 0:
                return new MainPage();
            case 1:
                return new WriteBoardView();
            case 2:
                return  new MyPageMain();
            case 3:
               return new MyPageAccount();
//            case 4:
//                return new MyPageAccount();
//            case 5:
//                BoardList boardList = new BoardList();
//                boardList.setArguments(bundle);
//                return boardList;
            default:
                return null;
        }

    }


    @Override
    public int getCount(){
        return  4;
    }


}
