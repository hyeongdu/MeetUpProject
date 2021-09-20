package com.example.extest.mainpage;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.extest.Join;
import com.example.extest.R;
import com.example.extest.RequestHttpURLConnection;
import com.example.extest.categoryList.CategoryAdapter;
import com.example.extest.categoryList.CategoryItem;
import com.example.extest.list.BoardList;
import com.example.extest.list.BoardListItem;
import com.example.extest.viewBoard.viewBoard;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainPage extends Fragment{

    CategoryAdapter cAdapter;
    SingerAdapter adapter;
    SingerAdapter2 adapter2;
    RecyclerView mRecyclerView;
    RecyclerView nRecyclerView;
    RecyclerView cRecyclerView;
    ViewFlipper viewFlipper = null;
    Toolbar toolbar ;
    pHomePage activity ;
    ImageView image01, image02, image03;





        private static final String TAG = "lecture";
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            setHasOptionsMenu(true);
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_main_page, container, false);


            //인기, 신규 클래스 모음
            mRecyclerView = rootView.findViewById(R.id.recyclerView);
            nRecyclerView = rootView.findViewById(R.id.recyclerView2);
            cRecyclerView = rootView.findViewById(R.id.recyclerCategory);
            image01 = rootView.findViewById(R.id.img01);
            image02 = rootView.findViewById(R.id.img02);
            image03 = rootView.findViewById(R.id.img03);



            String src1="https://cfile1.onoffmix.com/attach/x9zrIVdy4eASnqaRCXYpLWEhogPMGiZU";

            String src2="https://cfile1.onoffmix.com/attach/73OktDlEIRWqjgZz1SfoHdnVhYuv8CTB";

            String src3="https://cfile1.onoffmix.com/attach/V0POg8H9yLcNBWXuqvlahEwStrTCeUxK";

            Picasso.with(getActivity()).load(src1).into(image01);
            Picasso.with(getActivity()).load(src2).into(image02);
            Picasso.with(getActivity()).load(src3).into(image03);


            //이미지 슬라이드
            viewFlipper =  rootView.findViewById(R.id.viewFlipper);
            viewFlipper.startFlipping();
            viewFlipper.setFlipInterval(2000);
            viewFlipper.setInAnimation(getActivity(),android.R.anim.slide_in_left);
            //viewFlipper.setInAnimation(showIn);
            viewFlipper.setOutAnimation(getActivity(), android.R.anim.slide_out_right);

            String sUrl = getString(R.string.server_addr) + "/android/descDateClass";
            try {
                ContentValues values = new ContentValues();

                NetworkTask networkTask = new NetworkTask(sUrl, values);
                networkTask.execute();
            } catch(Exception e) {
                e.printStackTrace();
            }


            cAdapter = new CategoryAdapter(getContext());
            CategoryItem item11 = new CategoryItem("교육",R.drawable.category1);
            cAdapter.addItem(item11);
            CategoryItem item12 = new CategoryItem("세미나/컨퍼런스",R.drawable.category2);
            cAdapter.addItem(item12);
            CategoryItem item13 = new CategoryItem("취미/소모임",R.drawable.category3);
            cAdapter.addItem(item13);
            CategoryItem item14 = new CategoryItem("문화/예술",R.drawable.category4);
            cAdapter.addItem(item14);
            CategoryItem item15 = new CategoryItem("공모전",R.drawable.category5);
            cAdapter.addItem(item15);

            cRecyclerView.setAdapter(cAdapter);
            cRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            cRecyclerView.setItemAnimator(new DefaultItemAnimator());
            cAdapter.setItemClick(new CategoryAdapter.ItemClick() {
                @Override
                public void onClick(View view, int position) {
                    CategoryItem CategoryItem =  (CategoryItem) cAdapter.getItem(position);


                    Intent intent = new Intent(getActivity(), BoardList.class);
                    intent.putExtra("categoryName", CategoryItem.getCategoryName());
                    startActivity(intent);

                }
            });
            //false와 true의 차이점 !
            cRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            cRecyclerView.scrollToPosition(cAdapter.getItemCount() - 1);


//            //인기 클래스
            adapter2 = new SingerAdapter2(getActivity());
//            adapter = new SingerAdapter(getActivity());
//            SingerItem item1 = new SingerItem("아메리카노", "좋아요 : 10","평점 : 10",R.drawable.image1);
//            adapter.addItem(item1);
//            SingerItem item2 = new SingerItem("아메리카노", "좋아요 : 10","평점 : 10",R.drawable.image2);
//            adapter.addItem(item2);
//            SingerItem item3 = new SingerItem("아메리카노", "좋아요 : 10","평점 : 10",R.drawable.image3);
//            adapter.addItem(item3);
//            SingerItem item4 = new SingerItem("아메리카노", "좋아요 : 10","평점 : 10",R.drawable.image1);
//            adapter.addItem(item4);
//            SingerItem item5 = new SingerItem("아메리카노", "좋아요 : 10","평점 : 10",R.drawable.image2);
//            adapter.addItem(item5);
//
//            mRecyclerView.setAdapter(adapter);
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//            adapter.setItemClick(new SingerAdapter.ItemClick() {
//                @Override
//                public void onClick(View view, int position) {
//                    SingerItem item = (SingerItem) adapter.getItem(position);
//                    Toast.makeText(getActivity(), "selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();
//                }
//            });
//
//
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
//
//            mRecyclerView.scrollToPosition(adapter.getItemCount() - 1);

            //최신 클래스


            nRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            nRecyclerView.setItemAnimator(new DefaultItemAnimator());
            adapter2.setItemClick(new SingerAdapter2.ItemClick() {
                @Override
                public void onClick(View view, int position) {
                    SingerItem item = (SingerItem) adapter2.getItem(position);
                    Toast.makeText(getActivity(), "selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                }
            });

            nRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));

            nRecyclerView.scrollToPosition(adapter2.getItemCount() - 1);



            return  rootView;

        }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menulist, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.account:  {
                // navigate to settings screen
                return true;
            }
            case R.id.logout: {

                Intent intent = new Intent(getActivity(), MainPage.class);
                startActivity(intent);

                return true;
            }
            default: 
                return super.onOptionsItemSelected(item);
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
            Log.d(TAG, s);
            try {
                JSONObject jobj = new JSONObject(s);
                JSONObject fJsonObject;
                JSONArray jsonArray = jobj.getJSONArray("board");

                Log.d(TAG, String.valueOf(jsonArray.length()));
                for (int i = 0; i < jsonArray.length(); i++) {
                    fJsonObject = jsonArray.getJSONObject(i);

                    String tfile1 = fJsonObject.getString("tfile");
                    int j = tfile1.indexOf(".");
                    String tfile2 = tfile1.substring(0, j);

                    int z = getResources().getIdentifier(tfile2, "drawable", getContext().getPackageName());

                    SingerItem item6 = new SingerItem(fJsonObject.getString("title"), fJsonObject.getString("startdate"), fJsonObject.getString("enddate"), z);
                    adapter2.addItem(item6);


                }
                Log.d(TAG,"nRecyclerView");
                nRecyclerView.setAdapter(adapter2);
                nRecyclerView.invalidate();
                nRecyclerView.getAdapter().notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


}





