package com.example.databaseapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
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

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapReverseGeoCoder.ReverseGeoCodingResultListener {

    private static final String LOG_TAG = "MainActivity";
    private MapView mMapView;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private ImageButton imageButton1, imageButton2, imageButton3, imageButton4;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};
    float latidude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mMapView = (MapView) findViewById(R.id.map_view);
        //mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
        mMapView.setCurrentLocationEventListener(this);

        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

            checkRunTimePermission();
        }

        imageButton1 = findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(new View.OnClickListener() // 회원가입 버튼 클릭 시
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        imageButton2 = findViewById(R.id.imageButton2);
        imageButton2.setOnClickListener(new View.OnClickListener() // 회원가입 버튼 클릭 시
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        imageButton3 = findViewById(R.id.imageButton3);
        imageButton3.setOnClickListener(new View.OnClickListener() // 회원가입 버튼 클릭 시
        {
            @Override
            public void onClick(View v)
            {

            }
        });

        imageButton4 = findViewById(R.id.imageButton4);
        imageButton4.setOnClickListener(new View.OnClickListener() // 회원가입 버튼 클릭 시
        {
            @Override
            public void onClick(View v)
            {

            }
        });


        MapPoint mylocate = MapPoint.mapPointWithGeoCoord(37.27572276644065, 127.04401066266085);
        MapPoint cctv1 = MapPoint.mapPointWithGeoCoord(37.274662612838625, 127.04370399486666);
        MapPoint cctv2 = MapPoint.mapPointWithGeoCoord(37.278338398960194, 127.04310618561408);
        MapPoint cctv3 = MapPoint.mapPointWithGeoCoord(37.27638009705588, 127.04348671211531);
        MapPoint cctv4 = MapPoint.mapPointWithGeoCoord(37.2752640518535, 127.04198796468304);
        MapPoint cctv5 = MapPoint.mapPointWithGeoCoord(37.27413590270301, 127.04562343987345);
        MapPoint cctv6 = MapPoint.mapPointWithGeoCoord(37.27365643419459, 127.04339050873282);
        MapPoint cctv7 = MapPoint.mapPointWithGeoCoord(37.276835374282, 127.04392722234033);
        MapPoint convenience1 = MapPoint.mapPointWithGeoCoord(37.274881876753064, 127.04411622320126);
        MapPoint convenience2 = MapPoint.mapPointWithGeoCoord(37.27413934994582, 127.04501794931391);
        MapPoint convenience3 = MapPoint.mapPointWithGeoCoord(37.27581127538482, 127.04370097109954);
        MapPoint convenience4 = MapPoint.mapPointWithGeoCoord(37.274508210913915, 127.04262659415325);
        MapPoint convenience5 = MapPoint.mapPointWithGeoCoord(37.27590779776539, 127.04466271176611);
        MapPoint convenience6 = MapPoint.mapPointWithGeoCoord(37.27497014639692, 127.04577607820546);
        MapPoint rating1 = MapPoint.mapPointWithGeoCoord(37.27497014639692, 127.04577607820546);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("현재위치");
        marker.setTag(0);
        marker.setMapPoint(mylocate);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);// 커스텀마커 설정
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(marker);

        MapPOIItem cctvMarker1 = new MapPOIItem();
        cctvMarker1.setItemName("CCTV");
        cctvMarker1.setTag(0);
        cctvMarker1.setMapPoint(cctv1);
        cctvMarker1.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
        cctvMarker1.setCustomImageResourceId(R.drawable.cctv); //마커 모양
        cctvMarker1.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(cctvMarker1);

        MapPOIItem cctvMarker2 = new MapPOIItem();
        cctvMarker2.setItemName("CCTV");
        cctvMarker2.setTag(0);
        cctvMarker2.setMapPoint(cctv2);
        cctvMarker2.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
        cctvMarker2.setCustomImageResourceId(R.drawable.cctv); //마커 모양
        cctvMarker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(cctvMarker2);

        MapPOIItem marker3 = new MapPOIItem();
        marker3.setItemName("CCTV");
        marker3.setTag(0);
        marker3.setMapPoint(cctv3);
        marker3.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
        marker3.setCustomImageResourceId(R.drawable.cctv); //마커 모양
        marker3.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(marker3);

        MapPOIItem marker4 = new MapPOIItem();
        marker4.setItemName("CCTV");
        marker4.setTag(0);
        marker4.setMapPoint(cctv4);
        marker4.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
        marker4.setCustomImageResourceId(R.drawable.cctv); //마커 모양
        marker4.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(marker4);

        MapPOIItem marker5 = new MapPOIItem();
        marker5.setItemName("CCTV");
        marker5.setTag(0);
        marker5.setMapPoint(cctv5);
        marker5.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
        marker5.setCustomImageResourceId(R.drawable.cctv); //마커 모양
        marker5.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(marker5);

        MapPOIItem marker6 = new MapPOIItem();
        marker6.setItemName("CCTV");
        marker6.setTag(0);
        marker6.setMapPoint(cctv6);
        marker6.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
        marker6.setCustomImageResourceId(R.drawable.cctv); //마커 모양
        marker6.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(marker6);

        MapPOIItem marker7 = new MapPOIItem();
        marker7.setItemName("CCTV");
        marker7.setTag(0);
        marker7.setMapPoint(cctv7);
        marker7.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
        marker7.setCustomImageResourceId(R.drawable.cctv); //마커 모양
        marker7.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(marker7);

        MapPOIItem conMarker1 = new MapPOIItem();
        conMarker1.setItemName("편의점");
        conMarker1.setTag(0);
        conMarker1.setMapPoint(convenience1);
        conMarker1.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
        conMarker1.setCustomImageResourceId(R.drawable.convenience); //마커 모양
        conMarker1.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(conMarker1);

        MapPOIItem conMarker2 = new MapPOIItem();
        conMarker2.setItemName("편의점");
        conMarker2.setTag(0);
        conMarker2.setMapPoint(convenience2);
        conMarker2.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
        conMarker2.setCustomImageResourceId(R.drawable.convenience); //마커 모양
        conMarker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(conMarker2);

        MapPOIItem conMarker3 = new MapPOIItem();
        conMarker3.setItemName("편의점");
        conMarker3.setTag(0);
        conMarker3.setMapPoint(convenience3);
        conMarker3.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
        conMarker3.setCustomImageResourceId(R.drawable.convenience); //마커 모양
        conMarker3.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(conMarker3);

        MapPOIItem conMarker4 = new MapPOIItem();
        conMarker4.setItemName("편의점");
        conMarker4.setTag(0);
        conMarker4.setMapPoint(convenience4);
        conMarker4.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
        conMarker4.setCustomImageResourceId(R.drawable.convenience); //마커 모양
        conMarker4.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(conMarker1);

        MapPOIItem conMarker5 = new MapPOIItem();
        conMarker5.setItemName("편의점");
        conMarker5.setTag(0);
        conMarker5.setMapPoint(convenience5);
        conMarker5.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
        conMarker5.setCustomImageResourceId(R.drawable.convenience); //마커 모양
        conMarker5.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(conMarker5);

        MapPOIItem conMarker6 = new MapPOIItem();
        conMarker6.setItemName("편의점");
        conMarker6.setTag(0);
        conMarker6.setMapPoint(convenience6);
        conMarker6.setMarkerType(MapPOIItem.MarkerType.CustomImage);// 커스텀마커 설정
        conMarker6.setCustomImageResourceId(R.drawable.convenience); //마커 모양
        conMarker6.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(conMarker6);

        MapPOIItem ratingMarker6 = new MapPOIItem();
        ratingMarker6.setItemName("안전");
        ratingMarker6.setTag(0);
        ratingMarker6.setMapPoint(convenience6);// 커스텀마커 설정
        ratingMarker6.setCustomImageResourceId(R.drawable.convenience); //마커 모양
        ratingMarker6.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);// 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        mMapView.addPOIItem(conMarker6);

        MapCircle circle1 = new MapCircle(MapPoint.mapPointWithGeoCoord(37.274662612838625, 127.04370399486666), 250, // radius
                Color.argb(100, 200, 0, 0), // strokeColor
                Color.argb(100, 0, 200, 0) // fillColor
        );
        circle1.setTag(0);
        mMapView.addCircle(circle1);

        MapCircle circle2 = new MapCircle(MapPoint.mapPointWithGeoCoord(37.27581127538482, 127.04370097109954), 250, // radius
                Color.argb(100, 200, 0, 0), // strokeColor
                Color.argb(100, 200, 200, 0) // fillColor
        );
        circle2.setTag(0);
        mMapView.addCircle(circle2);




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
}



