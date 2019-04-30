package com.example.capston.pcjari;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.capston.pcjari.PC.PCListItem;
import com.example.capston.pcjari.fragment.FavoriteFragment;
import com.example.capston.pcjari.fragment.InformationFragment;
import com.example.capston.pcjari.fragment.SearchByAddressFragment;
import com.example.capston.pcjari.fragment.SearchByMeFragment;
import com.example.capston.pcjari.sqlite.DataBaseHelper;
import com.example.capston.pcjari.sqlite.DataBaseTables;

import java.util.ArrayList;

/**
 * Created by KangSeungho on 2017-09-25.
 */

public class MainActivity extends AppCompatActivity {
    public static int position;
    public static int dist;
    public static ArrayList<Integer> favorite;
    public static SQLiteDatabase db;
    public final static String server = "http://sosocom.duckdns.org:80/php/";
    public static PCListItem pc;
    DataBaseHelper DBHelper;

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

        DBHelper = new DataBaseHelper(getApplicationContext());
        db = DBHelper.getWritableDatabase();

        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  }, 0 );
        }

        favorite = new ArrayList<Integer>();
        firstSetting(navigation);
    }

    private void firstSetting(BottomNavigationView navigation) {
        favorite_setting();
        //프레그먼트 셋팅은 항상 마지막에
        fragment_setting(navigation);
    }

    private void fragment_setting(BottomNavigationView navigation) {
        String sql = "SELECT * FROM " + DataBaseTables.CreateDB_setting._TABLENAME + ";";
        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();
        position = results.getInt(1);
        dist = results.getInt(2);

        MenuItem prev = navigation.getMenu().getItem(position);
        prev.setChecked(true);

        results.close();

        switch (position) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new SearchByAddressFragment()).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new SearchByMeFragment()).commit();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.content, new FavoriteFragment()).commit();
                break;
        }
    }

    private void favorite_setting() {
        String sql = "SELECT * FROM " + DataBaseTables.CreateDB_favorite._TABLENAME + ";";;
        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        while(!results.isAfterLast()) {
            String tmp = results.getString(0);
            favorite.add(Integer.valueOf(tmp));
            results.moveToNext();
        }
        results.close();
    }
}
