package com.example.extest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class KakaoLogin extends Activity{
    private static  final String TAG = "lecture";
    String kakaoId;

    List userInfo = new ArrayList<>();




    public class KakaoSessionCallback implements ISessionCallback {
    Context mContext;
    MainActivity mainActivity;


        public KakaoSessionCallback(Context context, MainActivity activity) {
            this.mContext = context;
            this.mainActivity = activity;
        }

        @Override
        public void onSessionOpened() {
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
            Toast.makeText(mContext, "KaKao 로그인 오류가 발생했습니다. " + e.toString(), Toast.LENGTH_SHORT).show();
        }

        protected void requestMe() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {


                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    mainActivity.directToSecondActivity(false);
                }

                @Override
                public void onSuccess(MeV2Response result) {

                    userInfo.add(String.valueOf(result.getId()));
                    userInfo.add(result.getKakaoAccount().getProfile().getNickname());
                    GlobalHelper mGlobalHelper = new GlobalHelper();
                    mGlobalHelper.setGlobalUserLoginInfo(userInfo);

                    Log.d(TAG, "카카오 아이디 "+String.valueOf(userInfo.get(0)));
                    kakaoId = (String) userInfo.get(0);


                    String sUrl = getString(R.string.server_addr)+ "/security/checkid";

                    try {
                        ContentValues values = new ContentValues();
                        values.put("userID", kakaoId);


                        NetworkTask networkTask = new NetworkTask(sUrl, values);
                        networkTask.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


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
                    result = jobj.getString("desc");

                } catch (Exception e) {
                    e.printStackTrace();
                }



                if (result.equals("사용가능한 아이디입니다.")) {
                    mainActivity.kakaoIdCheck(userInfo, true);

                } else {
                    mainActivity.directToSecondActivity(true);
                }
            }
        }
    }




}

