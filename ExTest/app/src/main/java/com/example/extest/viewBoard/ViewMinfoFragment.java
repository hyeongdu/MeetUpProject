package com.example.extest.viewBoard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.extest.R;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

public class ViewMinfoFragment extends Fragment {
    private static final String TAG = "lecture";
    String sName, stelNum , sEmail;
    TextView tName, telNum, tEmail;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_view_minfo_fragment, container, false);

        tName = (TextView)rootView.findViewById(R.id.tName);
        telNum = (TextView)rootView.findViewById(R.id.telNum);
        tEmail = (TextView)rootView.findViewById(R.id.tEmail);

        Bundle bundle = getArguments();
        sName = bundle.getString("sName","에러");
        stelNum = bundle.getString("stelNum","안나옴");
        sEmail = bundle.getString("sEmail","안나옴");

        tName.setText(sName);
        telNum.setText(stelNum);
        tEmail.setText(sEmail);

        Log.d(TAG,"ViewMinfoFragment" );
        return rootView;
    }
}
