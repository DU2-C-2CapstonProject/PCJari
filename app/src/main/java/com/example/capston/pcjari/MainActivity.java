package com.example.capston.pcjari;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.capston.pcjari.fragment.FavoriteFragment;
import com.example.capston.pcjari.fragment.InformationFragment;
import com.example.capston.pcjari.fragment.SearchByAddressFragment;
import com.example.capston.pcjari.fragment.SearchByMeFragment;


public class MainActivity extends AppCompatActivity {
    GPSTracker gps = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search_by_address:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new SearchByAddressFragment()).commit();
                    return true;
                case R.id.navigation_search_by_me:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new SearchByMeFragment()).commit();
                    return true;
                case R.id.navigation_favorite:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new FavoriteFragment()).commit();
                    return true;
                case R.id.navigation_information:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new InformationFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.content, new SearchByAddressFragment()).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Intent intent = new Intent(this, Intro.class);
        //startActivity(intent);

        //gpsGetLocation();
    }

    private void gpsGetLocation() {
        if(gps == null) {
            gps = new GPSTracker(this);
        }else{
            gps.Update();
        }

        // check if GPS enabled
        if(gps.canGetLocation()){
            StaticData.GPS_X = gps.getLatitude();
            StaticData.GPS_Y = gps.getLongitude();
            // \n is for new line
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + StaticData.GPS_X + "\nLong: " + StaticData.GPS_Y, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    }
}
