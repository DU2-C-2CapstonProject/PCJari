package com.example.capston.pcjari;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by KangSeungho on 2017-11-01.
 */

public class DetailedInformationActivity extends AppCompatActivity{
    public static final String POSITION = "포지션";
    int position;
    Intent get_intent, send_intent;
    TextView di_notice, di_address, di_tel;
    PCListItem pc;
    ImageView location_mark;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedinformation);

        di_notice = (TextView) findViewById(R.id.di_notice);
        di_address = (TextView)findViewById(R.id.di_address);
        di_tel = (TextView)findViewById(R.id.di_tel);
        location_mark = (ImageView) findViewById(R.id.location_mark);

        get_intent = getIntent();
        position = get_intent.getIntExtra(POSITION, 0);

        pc = StaticData.pcItems[position];

        di_notice.setText(pc.getNotice());
        di_address.setText(pc.getAddress());
        di_tel.setText(pc.getTel());

        location_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_intent = new Intent(getApplicationContext(), MapViewActivity.class);
                send_intent.putExtra(POSITION, position);
                startActivity(send_intent);
            }
        });
    }
}