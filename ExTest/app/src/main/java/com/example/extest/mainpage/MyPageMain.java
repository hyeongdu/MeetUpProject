package com.example.extest.mainpage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.extest.MainActivity;
import com.example.extest.R;

public class MyPageMain extends Fragment {
    pHomePage activity;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        activity = (pHomePage) getActivity();

    }

    @Override
    public void onDetach(){
        super.onDetach();
        activity =  null;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_my_page, container, false);
         ViewGroup viewGroup = (ViewGroup)rootView.findViewById(R.id.myPageLayout);


         viewGroup.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 activity.onFragmentChange(0);
             }
         });


       return rootView;

    }


}