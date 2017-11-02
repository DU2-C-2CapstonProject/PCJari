package com.example.capston.pcjari;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.capston.pcjari.fragment.SearchByAddressFragment;

/**
 * Created by KangSeungho on 2017-11-01.
 */

public class DetailedInformationActivity extends AppCompatActivity{
    PCListItem pc;
    TextView notice, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedinformation);

        this.setTitle("매장 상세");

        Intent intent = getIntent();
        pc = StaticData.pcItems[intent.getIntExtra("Po", 0)];

        notice = (TextView) findViewById(R.id.di_notice);
        address = (TextView) findViewById(R.id.di_address);
        notice.setText(pc.getNotice());
        address.setText(pc.getAddress());
    }
}
