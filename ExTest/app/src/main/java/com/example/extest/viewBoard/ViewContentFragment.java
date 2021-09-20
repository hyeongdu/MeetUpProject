package com.example.extest.viewBoard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.extest.R;

import org.jetbrains.annotations.NotNull;

public class ViewContentFragment extends Fragment {
    private  static final String TAG = "lecture";
    String tContent;
    TextView Content;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_view_content_fragment, container, false);
        Content = (TextView)rootView.findViewById(R.id.bcontent);
        Bundle bundle = getArguments();
        tContent = bundle.getString("tContent","안녕");

        Content.setText(tContent);
        Log.d(TAG,"tContent :  " + tContent );
        return rootView;
    }

}