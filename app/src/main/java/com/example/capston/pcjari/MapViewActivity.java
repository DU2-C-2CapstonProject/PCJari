package com.example.capston.pcjari;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.capston.pcjari.PC.PCListItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by KangSeungho on 2017-11-04.
 */

public class MapViewActivity extends AppCompatActivity implements OnMapReadyCallback {
    Intent intent;
    GoogleMap mMap;
    PCListItem pc;
    GPSTracker gps;
    int check=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();

        intent = getIntent();
        pc = MainActivity.pc;
        gps = new GPSTracker(getApplicationContext());

        // gps 받아올 수 있는 환경인지 Check~
        if(gps.canGetLocation) {
            gps.Update();
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        else {
            // GPS OFF 일때 Dialog 표시
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("위치 서비스 설정");
            alertDialog.setMessage("무선 네트워크, GPS 중 한 가지 이상 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정하시겠습니까?");

            // Dialog OK 버튼 (GPS 설정 화면으로 이동)
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    check++;
                    Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                }
            });

            // Dialog No 버튼
            alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            // Dialog 보여주기
            alertDialog.create().show();
        }
    }

    // 맵에 마커 그리기
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        LatLng PC_location = new LatLng(pc.getLocation_x(), pc.getLocation_y());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions
                .position(PC_location)
                .title(pc.getTitle());

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }

        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PC_location, 16));
    }

    // 다른 Activity 갔다 올 때 다시 시작하는 메소드
    @Override
    protected void onResume() {
        super.onResume();

        if(check >=1 ) {
            gps = new GPSTracker(getApplicationContext());

            if(gps.canGetLocation) {
                gps.Update();
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
            }
            else {
                Toast.makeText(getApplicationContext(), "위치 기능을 키지 않을 경우\n원활한 자료 제공이 되지 않습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
