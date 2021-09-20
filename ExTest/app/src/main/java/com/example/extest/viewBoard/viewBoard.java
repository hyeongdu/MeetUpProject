package com.example.extest.viewBoard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.extest.KakaoLogin;
import com.example.extest.R;
import com.example.extest.list.BoardList;
import com.example.extest.list.BoardListItem;
import com.example.extest.list.RequestHttpURLConnection;
import com.example.extest.mainpage.pHomePage;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.daum.mf.map.api.MapView;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class viewBoard extends AppCompatActivity {
    private static final String TAG = "lecture";
    String tid, likecheck, availnum, cenddate, tSpace, title, tfile, likecount,cstartdate,rstartdate, mEmail,tIntro,renddate, mName, mTel, tContent;
    TextView L_title, L_availmemNum, L_likeCount, L_cStartDate, L_cEndDate, L_rStartDate, L_rEndDate, L_tSpace;
    ImageView imageView41;
    SupportMapFragment mapFragment;
    GoogleMap map;
    List<Address> list = null;
    Geocoder coder;
    Bundle bundle;
    Bundle bundle1;
    TabLayout tabLayout;
    ImageView likeButton;
    Button KakaoPay;

    String tfile1;


    ViewContentFragment viewContentFragment;
    ViewMinfoFragment viewMinfoFragment;



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
            Log.d(TAG, "시작3");
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();

            result = requestHttpURLConnection.request(url, values);

            return result;

        }


        @Override
        protected void onPostExecute(String s) {

            Log.d(TAG, "boardlist " + s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject jsonObject1 = jsonObject.getJSONObject("viewBoard");
                Log.d(TAG,jsonObject1.toString());
                cenddate = jsonObject1.getString("cenddate");
                int i = cenddate.indexOf(" ");
                cenddate = cenddate.substring(0,i);
                Log.d(TAG,"1111111111111111");
                cstartdate = jsonObject1.getString("cstartdate");
                cstartdate = cstartdate.substring(0,i);
                Log.d(TAG,"1111111111111111");
                renddate = jsonObject1.getString("renddate");
                renddate = renddate.substring(0,i);
                rstartdate = jsonObject1.getString("rstartdate");
                rstartdate = rstartdate.substring(0,i);
                Log.d(TAG,"1111111111111111");
                title = jsonObject1.getString("title");
                likecheck = jsonObject1.getString("likecheck");
                availnum = jsonObject1.getString("availnum");
                Log.d(TAG,"1111111111111111");
                tSpace = jsonObject1.getString("tSpace");
                likecheck = jsonObject1.getString("likecheck");
                likecount = jsonObject1.getString("likecount");
                Log.d(TAG,"1111111111111111");
                mEmail = jsonObject1.getString("mEmail");
                tIntro = jsonObject1.getString("tIntro");
                tContent = jsonObject1.getString("tContent");
                mTel = jsonObject1.getString("mTel");
                tfile = jsonObject1.getString("tfile");

                int j = tfile.indexOf(".");
                tfile1 = tfile.substring(0,j);
                Log.d(TAG,"tfile : " + tfile1);

                mName = jsonObject1.getString("mName");
                Log.d(TAG,"mName  " + mName);
                L_title.setText(title);
                L_availmemNum.setText(availnum);
                L_likeCount.setText(likecount);
                L_cStartDate.setText(cstartdate);
                L_cEndDate.setText(cenddate);
                L_rStartDate.setText(rstartdate);
                L_rEndDate.setText(renddate);
                L_tSpace.setText(tSpace);
                list = coder.getFromLocationName(tSpace, 10);

                bundle.putString("tContent",tContent);
                Log.d(TAG,"tContent ;   " + tContent);

                bundle1.putString("sName", mName);
                bundle1.putString("sEmail", mEmail);
                bundle1.putString("stelNum", mTel);

                Log.d(TAG,"sName ;   " + mName);
                Log.d(TAG,"sEmail ;   " + mEmail);
                Log.d(TAG,"stelNum ;   " + mTel);

                if(likecheck.equals("1"))
                    likeButton.setImageResource(R.drawable.checkedlike);



              int z =  getResources().getIdentifier(tfile1,"drawable",getPackageName());

              imageView41.setImageResource(z);


                mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        Log.d(TAG, "GOoleMap is ready");
                        Log.d(TAG, String.valueOf(list.get(0).getLatitude()));
                        Log.d(TAG, String.valueOf(list.get(0).getLongitude()));
                        map = googleMap;
                        LatLng startingPoint = new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude());
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(startingPoint, 17));

                        googleMap.addMarker(new MarkerOptions()
                                .position(startingPoint)
                                .title(String.valueOf(list.get(0).getAddressLine(0))));
                    }
                });

                try {
                    MapsInitializer.initialize(getApplicationContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d(TAG, String.valueOf(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_board);
        viewContentFragment = new ViewContentFragment();
        viewMinfoFragment = new ViewMinfoFragment();
        bundle = new Bundle();
        bundle1 = new Bundle();
        likeButton=  (ImageView) findViewById(R.id.likeButton);
        imageView41 = (ImageView)findViewById(R.id.imageView4);
        Intent intent = getIntent();
        tid = intent.getStringExtra("tid");
        String sUrl = getString(R.string.server_addr) + "/android/classview";

        try {
            ContentValues values = new ContentValues();
//            if(categoryName.equals("교육"))
            values.put("tid", tid);

            Log.d(TAG, "시작1");
            NetworkTask networkTask = new NetworkTask(sUrl, values);

            networkTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

        tabLayout = findViewById(R.id.tabMenu);
        L_title = (TextView) findViewById(R.id.title1);
        L_availmemNum = (TextView) findViewById(R.id.availmemNum1);
        L_likeCount = (TextView) findViewById(R.id.likeCount1);
        L_cStartDate = (TextView) findViewById(R.id.cStartDate1);
        L_cEndDate = (TextView) findViewById(R.id.cEndDate1);
        L_rStartDate = (TextView) findViewById(R.id.rStartDate1);
        L_rEndDate = (TextView) findViewById(R.id.rEndDate1);
        L_tSpace = (TextView) findViewById(R.id.tSpace1);
//        likeButton=  (ImageView) findViewById(R.id.likeButton);
        KakaoPay = (Button)findViewById(R.id.kakaopay);

        coder = new Geocoder(this);

        viewContentFragment.setArguments(bundle);
        viewMinfoFragment.setArguments(bundle1);
        //기본으로 시작하는 프래그먼트
        getSupportFragmentManager().beginTransaction().replace(R.id.container1, viewContentFragment).commit();



        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container1, viewContentFragment).commit();


                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container1, viewMinfoFragment).commit();
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        KakaoPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(viewBoard.this,boardPay.class);
                startActivity(intent);


            }
        });

        //좋아요!!!
        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sUrl = getString(R.string.server_addr) + "/android/like";

                try {
                    ContentValues values = new ContentValues();

                    values.put("tid", tid);
                    values.put("title", title);

                    Log.d(TAG, "좋아요 시작" + sUrl);
                    NetworkTaskLike networkTaskLike = new NetworkTaskLike(sUrl, values);
                    networkTaskLike.execute();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public class NetworkTaskLike extends AsyncTask<Void, Void, String> {
        private String url;
        private ContentValues values;

        public NetworkTaskLike(String url, ContentValues values) {
            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result;
            Log.d(TAG, "시작3");
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();

            result = requestHttpURLConnection.request(url, values);

            return result;

        }


        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "likeCheck :  " + s);
            if(s.equals("1")){
                likeButton.setImageResource(R.drawable.checkedlike );
                Toast.makeText(getApplicationContext(), "좋아요", Toast.LENGTH_SHORT).show();
            } else{
                likeButton.setImageResource(R.drawable.unchecklike );
                Toast.makeText(getApplicationContext(), "좋아요 취소", Toast.LENGTH_SHORT).show();

            }


        }

    }


}