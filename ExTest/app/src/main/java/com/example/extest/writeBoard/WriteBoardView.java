package com.example.extest.writeBoard;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.extest.R;
import com.example.extest.mainpage.pHomePage;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class  WriteBoardView extends Fragment {
    private  static final String TAG = "lecture";
    //카테고리 목록
    String[] categoryList = {"교육", "세미나/컨퍼런스", "취미/소모임","문화/예술", "공모전"};
    //카테고리 이름
    TextView categoryName;

    pHomePage activity;

    //클래스 기간
    TextView cStartDate;
    TextView cEndDate;
    //클랙스 등록 기간
    TextView rStartDate;
    TextView rEndDate;;
    //클래스 정원
    EditText classMemNum;

    //담당자 정보
    EditText adminName;
    EditText adminPhone;
    EditText adminEmail;

    //제목, 내용
    EditText boardTitle;
    EditText boardContent;

    //파일 업로드
    String filePath1;
    ImageView ivPicture;
    TextView imageUploadName;

    Button submit;

    SupportMapFragment mapFragment;
    GoogleMap map;

    //맵 관련 변수

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    List<Address> list = null;
    Geocoder coder;
    EditText et_address;
    LinearLayout addressLayout;
    LinearLayout mapLayout;
    RadioButton radio0;
    RadioButton radio1;

    RadioGroup radioGroup;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_write_board_view, container, false);

        //권한 체크 후 사용자에 의해 취소되었다면 다시 요청
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }


        }




        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_item, categoryList);
                categoryName = rootView.findViewById(R.id.categoryName);
        Spinner spinner = rootView.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                categoryName.setText(categoryList[position]);

            }
            //아무것도 선택되지 않았을 때 호출
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                categoryName.setText("");
            }
        });

        //권한 체크
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }



        //클래스 기간
         cStartDate = rootView.findViewById(R.id.cStartDate);
         cEndDate = rootView.findViewById(R.id.cEndDate);
        final Calendar c = Calendar.getInstance();
        int cYear = c.get(Calendar.YEAR);
        int cMonth = c.get(Calendar.MONTH);
        int cDay = c.get(Calendar.DAY_OF_MONTH);

        cStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        AlertDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth){
                                cStartDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, cYear, cMonth, cDay);

                datePickerDialog.show();

            }
        });
        cEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        AlertDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth){
                                cEndDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, cYear, cMonth, cDay);

                datePickerDialog.show();

            }
        });



        //클래스 등록 기간
        rStartDate = rootView.findViewById(R.id.rStartDate);
        rEndDate = rootView.findViewById(R.id.rEndDate);
        final Calendar r = Calendar.getInstance();
        int rYear = r.get(Calendar.YEAR);
        int rMonth = r.get(Calendar.MONTH);
        int rDay = r.get(Calendar.DAY_OF_MONTH);

        rStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        AlertDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth){
                                rStartDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, rYear, rMonth, rDay);

                datePickerDialog.show();

            }
        });

        rEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        AlertDialog.THEME_HOLO_LIGHT,
                        new DatePickerDialog.OnDateSetListener(){
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth){
                                rEndDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, rYear, rMonth, rDay);

                datePickerDialog.show();

            }
        });


        //클래스 정원
        classMemNum = rootView.findViewById(R.id.classMemNum);

        //담당자 정보
        adminName = rootView.findViewById(R.id.adminName);
        adminPhone = rootView.findViewById(R.id.adminPhoneNum);
        adminEmail = rootView.findViewById(R.id.adminEmail);

        //제목 내용
        boardTitle =  rootView.findViewById(R.id.boardTitle);
        boardContent =  rootView.findViewById(R.id.boardContent);

        //파일 업로드
        imageUploadName = rootView.findViewById(R.id.imageUploadName);

        imageUploadName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnGetPicture();
            }
        });

        //제출
        submit = rootView.findViewById(R.id.button5);

        coder = new Geocoder(getActivity());
        et_address = (EditText) rootView.findViewById(R.id.et_address);

        addressLayout = (LinearLayout)rootView.findViewById(R.id.addressLayout);
        mapLayout = (LinearLayout) rootView.findViewById(R.id.mapLayout1);
        radio0 = rootView.findViewById(R.id.radio0);
        radio1 = rootView.findViewById(R.id.radio1);
        radioGroup = rootView.findViewById(R.id.radioGroup);
        et_address.setEnabled(false);

        radio1.setChecked(true);

        mapFragment = (SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.map);
        // java code

        // 중심점 변경 + 줌 레벨 변경

        initProgram();


        Button btn_search = (Button) rootView.findViewById(R.id.button);
        if (btn_search != null) {
            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(getActivity(), adressDaum.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HashMap<String, String> param1 = new HashMap<>();

                    param1.put("tCategory",categoryName.getText().toString());
                    param1.put("regStartDate",rStartDate.getText().toString());
                    param1.put("regEndDate",rEndDate.getText().toString());
                    param1.put("classStartDate",cStartDate.getText().toString());
                    param1.put("classEndDate",cEndDate.getText().toString());
                    param1.put("tMemnum",classMemNum.getText().toString());
                    param1.put("mName",adminName.getText().toString());
                    param1.put("mTel",adminPhone.getText().toString());
                    param1.put("mEmail",adminEmail.getText().toString());
                    param1.put("title",boardTitle.getText().toString());
                    param1.put("tContent",boardContent.getText().toString());
                    param1.put("tSpace",et_address.getText().toString());



                    HashMap<String, String> param2 = new HashMap<>();
                    param2.put("filename",filePath1);
                    Log.d(TAG,"111111111111111");
                    //AsyncTAsck를 통해 HttpURLConnection 수행
                    UploadAsync networkTask = new UploadAsync(getActivity(), param1, param2);
                    networkTask.execute();
                    Log.d(TAG,"22222222222222222222");
                    activity = (pHomePage)getActivity();
                    activity.onFragmentChangeToMain(0);

                }
            });
        }
        return rootView;

    }

    //제출
    public void onBtnUpload(){


    }


    //파일 업로드 호출
    public void onBtnGetPicture(){
        //갤러리 리스트뷰 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        //images on the SD card
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //결과를 리턴하는 activity 호출
        startActivityForResult(intent, 1);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == -1) {
                Uri selPhotoURi = intent.getData();
                showCapturedImage(selPhotoURi);
                Log.d(TAG,"3333333333333333333333");
            }
            Log.d(TAG,"444444444444444444444444444");
        }
            //지도
        if(radio1.isChecked()) {
            Log.d(TAG,"radio1isChecked()" + radio1.isChecked());

            switch (requestCode) {
                case SEARCH_ADDRESS_ACTIVITY:


                    if (resultCode == -1) {
                        String data = intent.getExtras().getString("data");
                        if (data != null) {
                            Log.d(TAG,"111111111111");
                            try {
                                list = coder.getFromLocationName(data, 10);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            et_address.setText(String.valueOf(list.get(0).getAddressLine(0)));


                            mapFragment.getMapAsync(new OnMapReadyCallback() {
                                @Override
                                public void onMapReady(GoogleMap googleMap) {
                                    Log.d(TAG, "GOoleMap is ready");

                                    map = googleMap;
                                    LatLng startingPoint = new LatLng(list.get(0).getLatitude(), list.get(0).getLongitude());
                                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(startingPoint, 17));

                                    googleMap.addMarker(new MarkerOptions()
                                            .position(startingPoint)
                                            .title(String.valueOf(list.get(0).getAddressLine(0))));
                                }
                            });

                            try {
                                MapsInitializer.initialize(getActivity());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }




                        }
                    }
                    break;
            }
        }
    }


    private void showCapturedImage(Uri imageUri) {
        // 절대경로를 획득한다!!! 중요~
        filePath1 = getRealPathFromURI(imageUri);
        Log.d(TAG, "path1:"+filePath1);
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(filePath1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);
        Bitmap bitmap = BitmapFactory.decodeFile(filePath1);
        Bitmap rotatedBitmap = rotate(bitmap, exifDegree);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(rotatedBitmap, 800, 800, false);
        bitmap.recycle();

        //경로를 통해 비트맵으로 전환
        imageUploadName.setText(filePath1);
    }

    // 사진의 회전값을 처리하지 않으면 사진을 찍은 방향대로 이미지뷰에 처리되지 않는다.
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }


    // 사진을 정방향대로 회전하기
    private Bitmap rotate(Bitmap src, float degree) {
        // Matrix 객체 생성
        Matrix matrix = new Matrix();
        // 회전 각도 셋팅
        matrix.postRotate(degree);
        // 이미지와 Matrix 를 셋팅해서 Bitmap 객체 생성
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    // 사진의 절대경로 구하기
    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }


    // 네트웍 처리결과를 화면에 반영하기 위한 안드로이드 핸들러
    public class UploadAsync extends AsyncTask<Object, Integer, JSONObject> {
        private Context mContext;
        private HashMap<String, String> param;
        private HashMap<String, String> files;

        public UploadAsync(Context context,
                           HashMap<String, String> param,
                           HashMap<String, String> files)
        {
            mContext = context;
            this.param = param;
            this.files = files;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(Object... params) {
            JSONObject rtn = null;
            try {
                String sUrl = getString(R.string.server_addr) + "/android/meetupwrite";
                FileUpload multipartUpload = new FileUpload(sUrl, "UTF-8");
                rtn = multipartUpload.upload(param, files);
                Log.d(TAG, rtn.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return rtn;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {

                try {
                    if (result.getInt("success") == 1) {
                        Toast.makeText(mContext, "파일 업로드 성공!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mContext, "파일 업로드 실패!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void initProgram() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId) {
                    default:
                    case R.id.radio0:
                        addressLayout.setVisibility(View.GONE);
                        mapLayout.setVisibility(View.GONE);

                        break;
                    case R.id.radio1:
                        addressLayout.setVisibility(View.VISIBLE);
                        mapLayout.setVisibility(View.VISIBLE);



                        break;

                }

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();

    }
}

