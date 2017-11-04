package com.example.capston.pcjari;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        intent = getIntent();
        pc = StaticData.pcItems[intent.getIntExtra(DetailedInformationActivity.POSITION, 0)];

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        LatLng PC_location = new LatLng(pc.getLocation_x(), pc.getLocation_y());
        StaticData.gpsGetLocation(getApplicationContext());

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
}
