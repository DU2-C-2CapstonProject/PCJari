package com.example.capston.pcjari;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by KangSeungho on 2017-11-01.
 */

public class DetailedInformationActivity extends AppCompatActivity{
    public static final String POSITION = "포지션";
    private DetailedInformationActivity activity;
    int position;
    Intent get_intent;
    TextView di_notice, di_address, di_tel;
    PCListItem pc;
    ImageView location_mark, imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedinformation);
        activity = this;

        di_notice = (TextView) findViewById(R.id.di_notice);
        di_address = (TextView) findViewById(R.id.di_address);
        di_tel = (TextView) findViewById(R.id.di_tel);
        location_mark = (ImageView) findViewById(R.id.location_mark);
        imageView = (ImageView) findViewById(R.id.imageView);

        get_intent = getIntent();
        position = get_intent.getIntExtra(POSITION, 0);

        pc = StaticData.pcItems[position];

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setTitle(pc.getTitle());

        di_notice.setText(pc.getNotice());
        di_address.setText(pc.getAddress());
        di_tel.setText(pc.getTel());
        imageView.setImageDrawable(pc.getIcon());

        location_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 위치 확인 권한 확인
                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions( activity , new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  }, 0 );
                }
                else {
                    Intent send_intent = new Intent(getApplicationContext(), MapViewActivity.class);
                    send_intent.putExtra(POSITION, position);
                    startActivity(send_intent);
                }
            }
        });

        di_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel_value[] = pc.getTel().split("-");
                String tel = "tel:";
                for(String str : tel_value)
                    tel=tel.concat(str);

                Intent tel_intent = new Intent(Intent.ACTION_DIAL, Uri.parse(tel));
                startActivity(tel_intent);
            }
        });
    }
}