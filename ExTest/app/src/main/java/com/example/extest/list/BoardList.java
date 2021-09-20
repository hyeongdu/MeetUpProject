package com.example.extest.list;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.extest.Join;
import com.example.extest.MainActivity;
import com.example.extest.R;
import com.example.extest.mainpage.pHomePage;
import com.example.extest.viewBoard.viewBoard;

import org.json.JSONArray;
import org.json.JSONObject;

public class BoardList extends AppCompatActivity {
    private  static final String TAG = "lecture";
    BoardListAdapter adapter;
    JSONObject fJsonObject;
    JSONArray jsonArray;
    RecyclerView recyclerView;
    String categoryName;
    BoardListItem boardListItem;
    String data1;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);


        Intent intent = getIntent();
        data1 = intent.getStringExtra("categoryName");

        Log.d(TAG, "보드프래그넌트" + data1);
        String sUrl = getString(R.string.server_addr) + "/android/boardList";


        try {
            ContentValues values = new ContentValues();
//            if(categoryName.equals("교육"))
            values.put("category",data1);

            Log.d(TAG,"시작1");
            NetworkTask networkTask = new NetworkTask(sUrl, values);
            networkTask.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.BoradLRV);


        Log.d(TAG,data1+data1+data1);
        adapter = new BoardListAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setItemClick(new BoardListAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                BoardListItem item = (BoardListItem) adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "selected : " + item.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(BoardList.this, viewBoard.class);
                intent.putExtra("tid",item.getTid());
                startActivity(intent);
                finish();
            }
        });
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
                jsonArray = jsonObject.getJSONArray("board");

                Log.d(TAG, String.valueOf(jsonArray.length()));
                for (int i = 0; i < jsonArray.length(); i++) {
                    fJsonObject = jsonArray.getJSONObject(i);

                    String tfile1 = fJsonObject.getString("tfile");
                    int j = tfile1.indexOf(".");
                    String tfile2 = tfile1.substring(0, j);

                    int z =  getResources().getIdentifier(tfile2,"drawable",getPackageName());

                    BoardListItem bLi = new BoardListItem(fJsonObject.getString("title"), fJsonObject.getString("startdate"), fJsonObject.getString("enddate"), z, fJsonObject.getString("tid"));
                    adapter.addItem(bLi);
                }

                recyclerView.invalidate();
                recyclerView.getAdapter().notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}