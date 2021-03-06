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
    //???????????? ??????
    String[] categoryList = {"??????", "?????????/????????????", "??????/?????????","??????/??????", "?????????"};
    //???????????? ??????
    TextView categoryName;

    pHomePage activity;

    //????????? ??????
    TextView cStartDate;
    TextView cEndDate;
    //????????? ?????? ??????
    TextView rStartDate;
    TextView rEndDate;;
    //????????? ??????
    EditText classMemNum;

    //????????? ??????
    EditText adminName;
    EditText adminPhone;
    EditText adminEmail;

    //??????, ??????
    EditText boardTitle;
    EditText boardContent;

    //?????? ?????????
    String filePath1;
    ImageView ivPicture;
    TextView imageUploadName;

    Button submit;

    SupportMapFragment mapFragment;
    GoogleMap map;

    //??? ?????? ??????

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

        //?????? ?????? ??? ???????????? ?????? ?????????????????? ?????? ??????
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
            //???????????? ???????????? ????????? ??? ??????
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                categoryName.setText("");
            }
        });

        //?????? ??????
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[] { Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        }



        //????????? ??????
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



        //????????? ?????? ??????
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


        //????????? ??????
        classMemNum = rootView.findViewById(R.id.classMemNum);

        //????????? ??????
        adminName = rootView.findViewById(R.id.adminName);
        adminPhone = rootView.findViewById(R.id.adminPhoneNum);
        adminEmail = rootView.findViewById(R.id.adminEmail);

        //?????? ??????
        boardTitle =  rootView.findViewById(R.id.boardTitle);
        boardContent =  rootView.findViewById(R.id.boardContent);

        //?????? ?????????
        imageUploadName = rootView.findViewById(R.id.imageUploadName);

        imageUploadName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnGetPicture();
            }
        });

        //??????
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

        // ????????? ?????? + ??? ?????? ??????

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
                    //AsyncTAsck??? ?????? HttpURLConnection ??????
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

    //??????
    public void onBtnUpload(){


    }


    //?????? ????????? ??????
    public void onBtnGetPicture(){
        //????????? ???????????? ??????
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        //images on the SD card
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //????????? ???????????? activity ??????
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
            //??????
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
        // ??????????????? ????????????!!! ??????~
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

        //????????? ?????? ??????????????? ??????
        imageUploadName.setText(filePath1);
    }

    // ????????? ???????????? ???????????? ????????? ????????? ?????? ???????????? ??????????????? ???????????? ?????????.
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


    // ????????? ??????????????? ????????????
    private Bitmap rotate(Bitmap src, float degree) {
        // Matrix ?????? ??????
        Matrix matrix = new Matrix();
        // ?????? ?????? ??????
        matrix.postRotate(degree);
        // ???????????? Matrix ??? ???????????? Bitmap ?????? ??????
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(),
                src.getHeight(), matrix, true);
    }

    // ????????? ???????????? ?????????
    private String getRealPathFromURI(Uri contentUri) {
        int column_index=0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }


    // ????????? ??????????????? ????????? ???????????? ?????? ??????????????? ?????????
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
                        Toast.makeText(mContext, "?????? ????????? ??????!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mContext, "?????? ????????? ??????!", Toast.LENGTH_SHORT).show();
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

