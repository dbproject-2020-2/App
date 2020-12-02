package com.example.databaseapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.AsyncTask;


import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {

    private static final String LOG_TAG = "MainActivity";
    private MapView mMapView;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private ImageButton imageButton1, imageButton2, imageButton3, imageButton4;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};
    List<Double> cctvList1 = new ArrayList<>();
    List<Double> cctvList2 = new ArrayList<>();
    List<Double> conList1 = new ArrayList<>();
    List<Double> conList2 = new ArrayList<>();
    List<Double> polList1 = new ArrayList<>();
    List<Double> polList2 = new ArrayList<>();

    Double[] cctvArray1 = new Double[3704];
    Double[] cctvArray2 = new Double[3704];
    Double[] conArray1 = new Double[370];
    Double[] conArray2 = new Double[370];
    Double[] polArray1 = new Double[23];
    Double[] polArray2 = new Double[23];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.databaseapp", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        mMapView = (MapView) findViewById(R.id.map_view);
        //mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mMapView.setCurrentLocationEventListener(this);


        //GPS 끄기 켜기
        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }

        try{
            InputStream raw1 = getResources().openRawResource(R.raw.cctvlatitude);
            BufferedReader bf1 = new BufferedReader(new InputStreamReader(raw1));

            String line1;
            while((line1 = bf1.readLine()) != null){
                cctvList1.add((Double.parseDouble(line1)));
            }
            InputStream raw2 = getResources().openRawResource(R.raw.cctvlongitude);
            BufferedReader bf2 = new BufferedReader(new InputStreamReader(raw2));
            String line2;
            while((line2 = bf2.readLine()) != null){
                cctvList2.add((Double.parseDouble(line2)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        int cctvSize1 =0;
       for(Double item1 : cctvList1) {
            cctvArray1[cctvSize1++] = item1;
        }
        int cctvSize2 =0;
        for(Double item2 : cctvList2) {
            cctvArray2[cctvSize2++] = item2;
        }


        try{
            InputStream raw3 = getResources().openRawResource(R.raw.conlatitude);
            BufferedReader bf3 = new BufferedReader(new InputStreamReader(raw3));

            String line3;
            while((line3 = bf3.readLine()) != null){
                conList1.add((Double.parseDouble(line3)));
            }
            InputStream raw4 = getResources().openRawResource(R.raw.conlongitude);
            BufferedReader bf4 = new BufferedReader(new InputStreamReader(raw4));
            String line4;
            while((line4 = bf4.readLine()) != null){
                conList2.add((Double.parseDouble(line4)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        int conSize1 =0;
        for(Double conitem1 : conList1) {
            conArray1[conSize1++] = conitem1;
        }
        int conSize2 =0;
        for(Double conitem2 : conList2) {
            conArray2[conSize2++] = conitem2;
        }



        try{
            InputStream raw5 = getResources().openRawResource(R.raw.pollatitude);
            BufferedReader bf5 = new BufferedReader(new InputStreamReader(raw5));

            String line5;
            while((line5 = bf5.readLine()) != null){
                polList1.add((Double.parseDouble(line5)));
            }
            InputStream raw6 = getResources().openRawResource(R.raw.pollongitude);
            BufferedReader bf6 = new BufferedReader(new InputStreamReader(raw6));
            String line6;
            while((line6 = bf6.readLine()) != null){
                polList2.add((Double.parseDouble(line6)));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        int polSize1 =0;
        for(Double politem1 : polList1) {
            polArray1[polSize1++] = politem1;
        }
        int polSize2 =0;
        for(Double politem2 : polList2) {
            polArray2[polSize2++] = politem2;
        }




        imageButton1 = findViewById(R.id.imageButton1);

            imageButton1.setOnClickListener(new View.OnClickListener() // 회원가입 버튼 클릭 시
            {

                @Override
                public void onClick(View v) {

                    try {
                        for (int i = 0; i < 1500; i++) {
                            MapPoint cctv = MapPoint.mapPointWithGeoCoord(cctvArray1[i], cctvArray2[i]);
                            MapPOIItem cctvMarker = new MapPOIItem();
                            cctvMarker.setItemName("CCTV"+ ", 위도: " +cctvArray1[i] + ", 경도: " +cctvArray2[i]);
                            cctvMarker.setTag(i);
                            cctvMarker.setMapPoint(cctv);
                            cctvMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
                            cctvMarker.setCustomImageResourceId(R.drawable.cctv); //마커 모양
                            cctvMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                            mMapView.addPOIItem(cctvMarker);

                        }
                    } catch (Exception e) {
                    }

                }
            });


        imageButton2 = findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() // 회원가입 버튼 클릭 시
        {
            @Override
            public void onClick(View v)
            {
                try {
                    for (int i = 0; i < polArray1.length; i++) {
                        MapPoint convenience = MapPoint.mapPointWithGeoCoord(polArray1[i], polArray2[i]);
                        MapPOIItem polMarker = new MapPOIItem();
                        polMarker.setItemName("경찰서"+ ", 위도: " +polArray1[i] + ", 경도: " +polArray2[i]);
                        polMarker.setTag(i);
                        polMarker.setMapPoint(convenience);
                        polMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
                        polMarker.setCustomImageResourceId(R.drawable.policeoffice); //마커 모양
                        polMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                        mMapView.addPOIItem(polMarker);
                    }
                } catch (Exception e) {
                }
            }
        });

        imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() // 회원가입 버튼 클릭 시
        {
            @Override
            public void onClick(View v)
            {
                try {
                    for (int i = 0; i < conArray1.length; i++) {
                        MapPoint convenience = MapPoint.mapPointWithGeoCoord(conArray1[i], conArray2[i]);
                        MapPOIItem conMarker = new MapPOIItem();
                        conMarker.setItemName("편의점"+ ", 위도: " +conArray1[i] + ", 경도: " +conArray2[i]);
                        conMarker.setTag(i);
                        conMarker.setMapPoint(convenience);
                        conMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
                        conMarker.setCustomImageResourceId(R.drawable.convenience); //마커 모양
                        conMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                        mMapView.addPOIItem(conMarker);
                    }
                } catch (Exception e) {
                }

            }
        });

        imageButton4 = findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener() // 회원가입 버튼 클릭 시
        {
            @Override
            public void onClick(View v)
            {
                try {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 14; j++) {
                            MapCircle circleGreen = new MapCircle(MapPoint.mapPointWithGeoCoord(37.319705322492554 - (0.008 * i), 126.94280673316653 + (0.009 * j)), 150, // radius
                                    Color.argb(50, 0, 200, 0), // strokeColor
                                    Color.argb(50, 0, 200, 0) // fillColor
                            );
                            circleGreen.setTag(0);
                            mMapView.addCircle(circleGreen);


                        }
                    }
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 14; j++) {
                            MapCircle circleGreen = new MapCircle(MapPoint.mapPointWithGeoCoord(37.315705322492554 - (0.008 * i), 126.94480673316653 + (0.009* j)), 150, // radius
                                    Color.argb(50, 0, 200, 0), // strokeColor
                                    Color.argb(50, 0, 200, 0) // fillColor
                            );
                            circleGreen.setTag(0);
                            mMapView.addCircle(circleGreen);


                        }
                    }
                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 15; j++) {

                            MapCircle circleYellow = new MapCircle(MapPoint.mapPointWithGeoCoord(37.320205322492554 - (0.008 * i), 126.93830673316653 + (0.009 * j)), 150, // radius
                                    Color.argb(50, 200, 200, 0), // strokeColor
                                    Color.argb(50, 200, 200, 0) // fillColor
                            );
                            circleYellow.setTag(0);
                            mMapView.addCircle(circleYellow);
                        }
                    }

                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 15; j++) {

                            MapCircle circleRed = new MapCircle(MapPoint.mapPointWithGeoCoord(37.317205322492554 - (0.008 * i), 126.94130673316653 + (0.009 * j)), 150, // radius
                                    Color.argb(50, 200, 0, 0), // strokeColor
                                    Color.argb(50, 200, 0, 0) // fillColor
                            );
                            circleRed.setTag(0);
                            mMapView.addCircle(circleRed);
                        }
                    }
                }
                catch (Exception e) {
                }

            }
        });



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
        mMapView.setShowCurrentLocationMarker(false);
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }


    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

    }

    @Override
    public void onReverseGeoCoderFoundAddress(MapReverseGeoCoder mapReverseGeoCoder, String s) {
        mapReverseGeoCoder.toString();
        onFinishReverseGeoCoding(s);
    }

    @Override
    public void onReverseGeoCoderFailedToFindAddress(MapReverseGeoCoder mapReverseGeoCoder) {
        onFinishReverseGeoCoding("Fail");
    }

    private void onFinishReverseGeoCoding(String result) {
//        Toast.makeText(LocationDemoActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }

    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

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
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음
                mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission(){
        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED ) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음
            mMapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading);
        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.item1:
                mMapView.removeAllPOIItems();
                mMapView.removeAllCircles();
        }
        return super.onOptionsItemSelected(item);
    }

}





