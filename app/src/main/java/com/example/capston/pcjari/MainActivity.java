package com.example.capston.pcjari;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.capston.pcjari.fragment.FavoriteFragment;
import com.example.capston.pcjari.fragment.InformationFragment;
import com.example.capston.pcjari.fragment.SearchByAddressFragment;
import com.example.capston.pcjari.fragment.SearchByMeFragment;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search_by_address:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new SearchByAddressFragment()).commit();
                    return true;
                case R.id.navigation_search_by_me:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new SearchByAddressFragment()).commit();
                    return true;
                case R.id.navigation_favorite:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new SearchByAddressFragment()).commit();
                    return true;
                case R.id.navigation_information:
                    getSupportFragmentManager().beginTransaction().replace(R.id.content, new InformationFragment()).commit();
                    return true;
            }
            /*
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
            */
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

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  }, 0 );
        }

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.CALL_PHONE  }, 1 );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 0:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.CALL_PHONE ) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.CALL_PHONE  }, 1 );
                    }
                }
                break;
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                        ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  }, 1 );
                    }
                }
                break;
        }
    }
}
