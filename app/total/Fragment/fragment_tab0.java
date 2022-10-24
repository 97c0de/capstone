package com.han.total.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.han.total.Activity.MainActivity;
import com.han.total.Adapter.NewAdapter;
import com.han.total.Adapter.TemplateAdapter;
import com.han.total.R;
import com.han.total.Service.GpsTracker;
import com.han.total.Util.Global;
import com.han.total.Util.Logg;
import com.han.total.data;
import com.han.total.dialog.CustomDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

public class fragment_tab0 extends Fragment  implements NewAdapter.AdapterCallback{


    private Context mContext;

    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.template_recycler)
    RecyclerView template_recycler;


    @BindView(R.id.tv_main_temperture)  TextView tv_main_temperture;
    @BindView(R.id.iv_main_weather) ImageView iv_main_weather;

    @BindView(R.id.tv_time0)  TextView tv_time0;
    @BindView(R.id.tv_time1)  TextView tv_time1;
    @BindView(R.id.tv_time2)  TextView tv_time2;
    @BindView(R.id.tv_time3)  TextView tv_time3;
    @BindView(R.id.tv_weather0)  ImageView tv_weather0;
    @BindView(R.id.tv_weather1)  ImageView tv_weather1;
    @BindView(R.id.tv_weather2)  ImageView tv_weather2;
    @BindView(R.id.tv_weather3)  ImageView tv_weather3;

    @BindView(R.id.tv_temperature0)  TextView tv_temperature0;
    @BindView(R.id.tv_temperature1)  TextView tv_temperature1;
    @BindView(R.id.tv_temperature2)  TextView tv_temperature2;
    @BindView(R.id.tv_temperature3)  TextView tv_temperature3;


    int temp=20;

    private GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int CAMEARA_REQUEST_CODE = 101;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.CAMERA};


    public int Check=0;
    public int average=0;
    String[] arr = new String[4];
    public fragment_tab0(Context context) {
        mContext = context;
        // Required empty public constructor
    }

    public fragment_tab0() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tab0, container, false);
        ButterKnife.bind(this, view);
        InitRecylerView();
        tv_time.setText(Time());
        GpsInit();

        DownloadFilesTask mDownloadFilesTask = new DownloadFilesTask(0);
        mDownloadFilesTask.execute();
        mHandler = new MainHandler();
//        DownloadFilesTask mDownloadFilesTask1 = new DownloadFilesTask(1);
//        mDownloadFilesTask1.execute();
//
//        DownloadFilesTask mDownloadFilesTask2 = new DownloadFilesTask(2);
//        mDownloadFilesTask2.execute();
//
//        DownloadFilesTask mDownloadFilesTask3 = new DownloadFilesTask(3);
//        mDownloadFilesTask3.execute();
//
//        DownloadFilesTask mDownloadFilesTask4 = new DownloadFilesTask(4);
//        mDownloadFilesTask3.execute();

        return view;
        //return inflater.inflate(R.layout.fragment_tab0, container, false);
    }

    //카메라 권한 받는 함수
    void PermissonCamera(){
        //권한 체크
        TedPermission.with(mContext)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("카메라 권한이 필요합니다.")
                .setDeniedMessage("카메라 권한을 거부하셨습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }


    //RecyclerView
    @Override
    public void DoSomeThing(int posionion){
        // 리스트에서 클릭 리스너
        ((MainActivity)getActivity()).FragmentSwitch(3,mContext);
       //Toast.makeText(mContext,"posion: "+posionion,Toast.LENGTH_SHORT).show();
    }


    //리스트 초기함수
    public void InitRecylerView(){

        String[] weather=new String[3];
        weather[0]="sf";
        weather[1]="su";
        weather[2]="wi";

        String[] type=new String[3];
        type[0]="ou";
        type[1]="up";
        type[2]="do";
//        String weather = "sf";
//        String type = "ou";
        //fr_fragment.setVisibility(View.GONE);

        ArrayList<String> list = new ArrayList<>();
        ArrayList<Integer> index = new ArrayList();

        ArrayList<String> list0 = new ArrayList<>();
        ArrayList<Integer> index0 = new ArrayList();

        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<Integer> index1 = new ArrayList();

        ArrayList<String> list2 = new ArrayList<>();
        ArrayList<Integer> index2 = new ArrayList();

        int len0=0,len1=0,len2=0;
        // 10 ~ 21 봄가을 , 22부터 여름 , 10도 아래는 겨울
        if(temp<10){
            weather[0]="wi";
            Log.e("HANTAEKJIN","겨울: ");
        }else if(temp>22){
            weather[0]="su";
            Log.e("HANTAEKJIN","여름: ");
        }else{
            weather[0]="sf";
            Log.e("HANTAEKJIN","봄가을: ");
        }

        for (int j=0;j<1;j++) {
            for (int k=0;k<3;k++) {
                for (int i = 1; i < data.getInstance(mContext).NewgetNumber(weather[j] + type[k]) + 1; i++) {
                    Log.e("HANTAEKJIN",type[k] + " size : "+data.getInstance(mContext).NewgetNumber(weather[j] + type[k]));
                    Log.e("HANTAEKJIN","파일이름: "+data.getInstance(mContext).NewgetCloth(weather[k]+type[k],i));
                    Log.e("HANTAEKJIN","k: "+k);
                    if(k==0){
                        if (!data.getInstance(mContext).NewgetCloth(weather[j] + type[k], i).equals("null")) {
                            list0.add(data.getInstance(mContext).NewgetCloth(weather[j] + type[k], i));
                            index0.add(0);
                        }
                    }else if(k==1){
                        if (!data.getInstance(mContext).NewgetCloth(weather[j] + type[k], i).equals("null")) {
                            list1.add(data.getInstance(mContext).NewgetCloth(weather[j] + type[k], i));
                            index1.add(0);
                        }
                    }else{
                        if (!data.getInstance(mContext).NewgetCloth(weather[j] + type[k], i).equals("null")) {
                            list2.add(data.getInstance(mContext).NewgetCloth(weather[j] + type[k], i));
                            index2.add(0);
                        }
                    }
                }
            }
        }


        double randomValue = Math.random();
        int size0 = (int)(randomValue * list0.size()) ;
        randomValue = Math.random();
        int size1 = (int)(randomValue * list1.size()) ;
        randomValue = Math.random();
        int size2 = (int)(randomValue * list2.size()) ;


        Log.e("HANTAEKJIN","size0: "+size0);
        Log.e("HANTAEKJIN","list0.size: "+list0.size());
        Log.e("HANTAEKJIN","size1: "+size1);
        Log.e("HANTAEKJIN","list1.size: "+list1.size());
        Log.e("HANTAEKJIN","size2: "+size2);
        Log.e("HANTAEKJIN","list2.size: "+list2.size());

        if(list0.size()>0){
            list.add(list0.get(size0));
            index.add(0);
            Log.e("HANTAEKJIN","list0 ADD");
        }
        if(list1.size()>0){
            list.add(list1.get(size1));
            index.add(0);
            Log.e("HANTAEKJIN","list1 ADD");
        }
        if(list2.size()>0){
            list.add(list2.get(size2));
            index.add(0);
            Log.e("HANTAEKJIN","list2 ADD");
        }

        template_recycler.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false)) ;
        NewAdapter adapter = new NewAdapter(list,index,this,mContext) ;
        template_recycler.setAdapter(adapter) ;
    }

    // 자동 분류, 수동 분류 다이얼로그
    @OnClick({R.id.tv_add}) void Add(){
        ((MainActivity)getActivity()).FragmentSwitch(1,mContext);
//        PermissonCamera();
        CustomDialog dialog = new CustomDialog(mContext);
        dialog.setDialogListener(new CustomDialog.CustomDialogListener() {
            @Override
            public void onClicked0() {
                Camera();
            }
            @Override
            public void onClicked1() {

                ((MainActivity)getActivity()).FragmentSwitch(1,mContext);
            }

            @Override
            public void onClicked2() {

            }
        });
//        dialog.show();
    }

    // 퍼미션 결과
    PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            //Toast.makeText(mContext, "권한이 허용됨", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            //Toast.makeText(mContext, "권한이 거부됨", Toast.LENGTH_SHORT).show();
        }
    };


    // 카메라 띄우는 함수
    public void Camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }



    // GPS 리프레시
    public void Refresh(){
        //시간
        //Check = 0;
        //tv_time.setText(Time());
        GpsInit();
    }

    @OnClick(R.id.iv_refresh) void RefreshBtn(){
        Refresh();
    }

    // 타임 나타내는 함수
    String Time(){
        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("MM월 dd일 EE요일", Locale.getDefault()).format(currentTime);
        long lNow; Date dt;
        SimpleDateFormat sdfFormat = new SimpleDateFormat("hh:mm");
        lNow = System.currentTimeMillis(); dt = new Date(lNow);
        return date_text + " "+sdfFormat.format(dt);
    }

    // GPS 초기화
    void GpsInit(){
        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }
        gpsTracker = new GpsTracker((Activity) mContext);
        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();
        String address = getCurrentAddress(latitude, longitude);
        address=address.replace("대한민국","");
        address=address.replace("경기도 고양시 일산서구","");
        address=address.replace("서울","");

        address=address.replaceAll("[0-9]", "");
        tv_address.setText(address);
        //Toast.makeText((Activity) mContext, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onRequestPermissionsResult(int permsRequestCode,@NonNull String[] permissions, @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;
            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if ( check_result ) {
                //위치 값을 가져올 수 있음
                ;
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, REQUIRED_PERMISSIONS[1])) {
                    Toast.makeText((Activity) mContext, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    //finish();
                }else {
                    Toast.makeText((Activity) mContext, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission((Activity) mContext,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission((Activity) mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(mContext, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions((Activity) mContext, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions((Activity) mContext, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    // 위도 경도를 글씨로 나타냄
    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(mContext, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(mContext, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }



        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(mContext, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;

            case CAMEARA_REQUEST_CODE:
                if (resultCode == RESULT_OK && data.hasExtra("data")) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    if (bitmap != null) {
                        //iv_0.setImageBitmap(bitmap);
                    }

                }
                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }



    // 인증키 :
    // wiKBDAlOK9alXdz6utoeOBEGiQb44KoCl5cwW9pOq4D6PuwmMwKWGyQwyGDFUxTTzTnC8fc8LbplwrXKH5rX9w%3D%3D
    private class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {

        int isStart = 0;
        DownloadFilesTask(int stage){
            this.isStart = stage;
        }
        protected Long doInBackground(URL... urls) {
             GetData(isStart);

            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            //setProgressPercent(progress[0]);
        }

        protected void onPostExecute(Long result) {
            //showDialog("Downloaded " + result + " bytes");
        }
    }

    boolean CheckTime(String time){
        if(Integer.parseInt(time)<2){
            return false;
        }else{
            return true;
        }
    }

    // 어제 날씨 계산하는 함수
    String YesterDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);  // 오늘 날짜에서 하루를 뺌.
        String date = sdf.format(calendar.getTime());
        return date;
    }

    // 날씨 API 받는 함수
    void GetData(int stage){
        String servicekey = "wiKBDAlOK9alXdz6utoeOBEGiQb44KoCl5cwW9pOq4D6PuwmMwKWGyQwyGDFUxTTzTnC8fc8LbplwrXKH5rX9w%3D%3D";
        //String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=wiKBDAlOK9alXdz6utoeOBEGiQb44KoCl5cwW9pOq4D6PuwmMwKWGyQwyGDFUxTTzTnC8fc8LbplwrXKH5rX9w%3D%3D&pageNo=1&numOfRows=10&dataType=JSON&base_date=20210419&base_time=0500&nx=1&ny=1";
        //String url = "&pageNo=1&numOfRows=10&dataType=JSON&base_date=20210419&base_time=0500&nx=1&ny=1";
        //getUltraSrtFcst
        //String total_url = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=";
        String total_url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?serviceKey=";
       // String url1 = "&pageNo=1&numOfRows=10&dataType=JSON&base_date=";
        String url1 = "&pageNo="+Integer.toString(1)+"&numOfRows=100&dataType=JSON&base_date=";

        String date;
        String time;
        if(stage==0) {
            if (CheckTime(GetTime(stage))) {
                //2시 넘었으면
                date = Today();
                time = Integer.toString(Integer.parseInt(GetTime(stage)) - 3);
            } else {
                date = YesterDay();
                time = "10";
            }
        }else{
            if (Integer.parseInt(GetTime(0)) > Integer.parseInt(GetTime(stage))) { //하루 지난것
                //2시 넘었으면
                date = Today();
                time = Integer.toString(Integer.parseInt(GetTime(stage)));
            } else {
                date = YesterDay();
                time = GetTime(stage);
            }
        }

        //date = Today();
        //time = "0500";


//         if(stage==1){
//             Logg.e(Global.USER_HTJ,"오후 "+(Integer.parseInt(time)-12)+":00");
//            if(Integer.parseInt(time)>12){
//                tv_time3.setText("오후 "+(Integer.parseInt(time)-12)+":00");
//            }else{
//                tv_time3.setText("오전 "+(Integer.parseInt(time))+":00");
//            }
//        }else if(stage==2){
//            if(Integer.parseInt(time)>12){
//                tv_time2.setText("오후 "+(Integer.parseInt(time)-12)+":00");
//            }else{
//                tv_time2.setText("오전 "+(Integer.parseInt(time))+":00");
//            }
//        }else if(stage==3){
//            if(Integer.parseInt(time)>12){
//                tv_time1.setText("오후 "+(Integer.parseInt(time)-12)+":00");
//            }else{
//                tv_time1.setText("오전 "+(Integer.parseInt(time))+":00");
//            }
//        }else if(stage==4){
//            if(Integer.parseInt(time)>12){
//                tv_time0.setText("오후 "+(Integer.parseInt(time)-12)+":00");
//            }else{
//                tv_time0.setText("오전 "+(Integer.parseInt(time))+":00");
//            }
//        }
        if(time.length()==1)
            time = "&base_time="+"0"+time+"00";
        else
            time = "&base_time="+time+"00";
        //time = "&base_time="+"0500";


        total_url = total_url +servicekey +url1 + date + time+"&nx=60&ny=121";
        //GetTime();
        Logg.e(Global.USER_HTJ,"date: "+date);
        Logg.e(Global.USER_HTJ,"time: "+time);
        Logg.e(Global.USER_HTJ,"total_url: "+total_url);
        //url = url+servicekey;
        //StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst?serviceKey=wiKBDAlOK9alXdz6utoeOBEGiQb44KoCl5cwW9pOq4D6PuwmMwKWGyQwyGDFUxTTzTnC8fc8LbplwrXKH5rX9w%3D%3D&pageNo=1&numOfRows=10&dataType=JSON&base_date=20210419&base_time=0500&nx=1&ny=1"); /*URL*/
        StringBuilder urlBuilder = new StringBuilder(total_url); /*URL*/

        try {
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            System.out.println("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            String Responseline="";
            while ((line = rd.readLine()) != null) {
                sb.append(line);
                Responseline += line;
                Logg.e(Global.USER_HTJ,line+"");
            }
            //InitData(Responseline);
            ReadData(Responseline,stage);

            rd.close();
            conn.disconnect();

            Logg.e(Global.USER_HTJ,"stage: "+stage);

                Check++;
                Message msg = Message.obtain();
                msg.what = APP_NORMAL_START_MSG;
                msg.arg1 = stage+1;
                sendDataMessage(msg);


            //tv_main_temperture.setText((int)(average/4)+"º");
        }catch (Exception e){
            Logg.e(Global.USER_HTJ,"exceptuon"+e);
        }
    }


    private static MainHandler mHandler = null;
    public static void sendDataMessage(Message msg) {
        if(mHandler != null) {
            mHandler.sendMessage(msg);
        } else {

        }
    }

    public static final int APP_NORMAL_START_MSG = 102;
    public class MainHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == APP_NORMAL_START_MSG) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        temp = (int)(average/4);
                        tv_main_temperture.setText((int)(average/4)+"º");
                        tv_temperature0.setText(arr[0]+"º");
                        tv_temperature1.setText(arr[1]+"º");
                        tv_temperature2.setText(arr[2]+"º");
                        tv_temperature3.setText(arr[3]+"º");
                        InitRecylerView();
//                        DownloadFilesTask mDownloadFilesTask = new DownloadFilesTask(Check);
//                        mDownloadFilesTask.execute();
                    }
                }, 200); // 1sec ->  200
            }
        }
    }

    // 오늘 날짜 확인
    String Today(){
        //String today;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date today = Calendar.getInstance().getTime();

        return dateFormat.format(today);

    }

    String GetTime(int stage){
        Date today = new Date();
        SimpleDateFormat  format1= new SimpleDateFormat("HH");
        if(stage==0) {
            return format1.format(today);
        }else if(stage==1){
            int time = Integer.parseInt(format1.format(today));
            if(time>3){
                return Integer.toString(time-3);
            }else{
                return Integer.toString(24+time-3);
            }
        }else if(stage==2){
            int time = Integer.parseInt(format1.format(today));
            if(time>6){
                return Integer.toString(time-6);
            }else{
                return Integer.toString(24+time-6);
            }
        }else if(stage==3){
            int time = Integer.parseInt(format1.format(today));
            if(time>9){
                return Integer.toString(time-9);
            }else{
                return Integer.toString(24+time-9);
            }
        }else{
            int time = Integer.parseInt(format1.format(today));
            if(time>12){
                return Integer.toString(time-12);
            }else{
                return Integer.toString(24+time-12);
            }
        }
    }

    void ReadData(String allmsg,int stage){
        Logg.e(Global.USER_HTJ,"toda: "+Today());
        JSONObject jsonResult;

        try {
            jsonResult = new JSONObject(allmsg);

            Logg.e(Global.USER_HTJ,"jsonResult: "+jsonResult+"");
            //JSONArray data = new JSONArray(jsonResult.getString("row"));

            //Log.e("HAN: "," jsonResult.getString(\"Corona19Status\"): "+ jsonResult.getString("Corona19Status")+"");

            //jsonResult = new JSONObject(jsonResult.getString("Corona19Status"));
            //Log.e("HAN: "," jsonResult.getString(rona19Status): "+ jsonResult.getString("row")+"");

            jsonResult = new JSONObject(jsonResult.getString("response"));
            jsonResult = new JSONObject(jsonResult.getString("body"));
            jsonResult = new JSONObject(jsonResult.getString("items"));

            JSONArray data = new JSONArray(jsonResult.getString("item"));
//            Log.e("HAN: ","data.length(): "+ data.length());
//            //JSONArray list= jsonResult.getJSONArray("Corona19Status");
            int skyindex=0;
            int ptyindex=0;
            int p1hindex=0;
            for (int i = 0; i < data.length(); i++) {
                JSONObject jsonPopupObject = data.getJSONObject(i);
                Logg.e(Global.USER_HTJ,"index"+ i);
                if(jsonPopupObject.get("category").equals("SKY")){  //하늘상태  1맑음, 3 구름 많음 4 흐림
                    Logg.e(Global.USER_HTJ,"SKY"+jsonPopupObject.get("fcstValue"));
                    Logg.e(Global.USER_HTJ,"skyindex"+ skyindex);
                    if(skyindex==0){
                        if(jsonPopupObject.get("fcstValue").equals("1")){
                            iv_main_weather.setImageResource(R.drawable.sunny);
                        }else if (jsonPopupObject.get("fcstValue").equals("3")){
                            iv_main_weather.setImageResource(R.drawable.cloud);
                        }else {
                            iv_main_weather.setImageResource(R.drawable.cloud);
                        }
                    }else if(skyindex==1){
                        if(jsonPopupObject.get("fcstValue").equals("1")){
                            tv_weather3.setImageResource(R.drawable.sunny);
                        }else if (jsonPopupObject.get("fcstValue").equals("3")){
                            tv_weather3.setImageResource(R.drawable.cloud);
                        }else {
                            tv_weather3.setImageResource(R.drawable.cloud);
                        }
                    }else if(skyindex==2){
                        if(jsonPopupObject.get("fcstValue").equals("1")){
                            tv_weather2.setImageResource(R.drawable.sunny);
                        }else if (jsonPopupObject.get("fcstValue").equals("3")){
                            tv_weather2.setImageResource(R.drawable.cloud);
                        }else {
                            tv_weather2.setImageResource(R.drawable.cloud);
                        }
                    }else if(skyindex==3){
                        if(jsonPopupObject.get("fcstValue").equals("1")){
                            tv_weather1.setImageResource(R.drawable.sunny);
                        }else if (jsonPopupObject.get("fcstValue").equals("3")){
                            tv_weather1.setImageResource(R.drawable.cloud);
                        }else {
                            tv_weather1.setImageResource(R.drawable.cloud);
                        }
                    }else if(skyindex==4){
                        if(jsonPopupObject.get("fcstValue").equals("1")){
                            tv_weather0.setImageResource(R.drawable.sunny);
                        }else if (jsonPopupObject.get("fcstValue").equals("3")){
                            tv_weather0.setImageResource(R.drawable.cloud);
                        }else {
                            tv_weather0.setImageResource(R.drawable.cloud);
                        }
                    }
                    skyindex++;
                }

                if(jsonPopupObject.get("category").equals("PTY")){  //0아니면 비이다.
                    //Logg.e(Global.USER_HTJ,"RAIN"+jsonPopupObject.get("fcstValue"));
                    //Logg.e(Global.USER_HTJ,"ptyindex"+ ptyindex);
                    if(ptyindex==0){
                        //Logg.e(Global.USER_HTJ,"jsonPopupObject.get(\"fcstValue\"):"+jsonPopupObject.get("fcstValue"));
                        if(!jsonPopupObject.get("fcstValue").equals("0")){
                            iv_main_weather.setImageResource(R.drawable.rain);
                        }
                    }else if(ptyindex==1){
                        if(!jsonPopupObject.get("fcstValue").equals("0")){
                            tv_weather3.setImageResource(R.drawable.rain);
                        }
                    }else if(ptyindex==2){
                        if(!jsonPopupObject.get("fcstValue").equals("0")){
                            tv_weather2.setImageResource(R.drawable.rain);
                        }
                    }else if(ptyindex==3){
                        if(!jsonPopupObject.get("fcstValue").equals("0")){
                            tv_weather1.setImageResource(R.drawable.rain);
                        }
                    }else if(ptyindex==4){
                        if(!jsonPopupObject.get("fcstValue").equals("0")){
                            tv_weather0.setImageResource(R.drawable.rain);
                        }
                    }
                    ptyindex++;
                }

                if(jsonPopupObject.get("category").equals("T1H")){  //기온
                    //Logg.e(Global.USER_HTJ," jsonPopupObject.get(\"fcstValue\"); "+ jsonPopupObject.get("fcstValue"));
                    Logg.e(Global.USER_HTJ,"기온"+ jsonPopupObject.get("fcstValue"));
                    Logg.e(Global.USER_HTJ,"기온 p1hindex"+ p1hindex);
                    average+=Integer.parseInt(jsonPopupObject.get("fcstValue").toString());
                    if(p1hindex==0){
                        arr[0] = jsonPopupObject.get("fcstValue").toString();
                        //tv_temperature3.setText(jsonPopupObject.get("fcstValue")+"º");
                    }else if(p1hindex==1){
                        arr[1] = jsonPopupObject.get("fcstValue").toString();
                        //tv_temperature2.setText(jsonPopupObject.get("fcstValue")+"º");
                    }else if(p1hindex==2){
                        arr[2] = jsonPopupObject.get("fcstValue").toString();
                        //tv_temperature1.setText(jsonPopupObject.get("fcstValue")+"º");
                    }else if(p1hindex==3){
                        arr[3] = jsonPopupObject.get("fcstValue").toString();
                        //tv_temperature0.setText(jsonPopupObject.get("fcstValue")+"º");
                    }
                    p1hindex++;
                }
             }
        }
        catch (Exception e){
            Logg.e(Global.USER_HTJ," exception"+ e);
        }
    }


}