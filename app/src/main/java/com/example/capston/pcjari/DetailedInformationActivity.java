package com.example.capston.pcjari;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.capston.pcjari.fragment.DetailedInformationFragment;

/**
 * Created by KangSeungho on 2017-11-01.
 */

public class DetailedInformationActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedinformation);

        getSupportFragmentManager().beginTransaction().add(R.id.content, new DetailedInformationFragment()).commit();
    }
}
