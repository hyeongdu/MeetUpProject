package com.example.extest.mainpage;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.extest.R;

public class MyPageAccount extends Fragment {



    private static final String TAG = "lecture";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_my_page_account, container, false);
            Log.d(TAG, "MyPageAccount");
        return rootView;
    }
}