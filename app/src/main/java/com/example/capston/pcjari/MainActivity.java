package com.example.capston.pcjari;

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
    }

}
