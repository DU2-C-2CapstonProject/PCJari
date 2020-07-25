package com.example.capston.pcjari;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capston.pcjari.PC.PCListItem;

/**
 * Created by KangSeungho on 2017-11-01.
 */

public class DetailedInformationActivity extends AppCompatActivity {
    public static final String POSITION = "포지션";
    int position;
    Intent get_intent;
    TextView di_notice, di_address, di_tel, di_cpu, di_ram, di_vga, di_per, di_price;
    PCListItem pc;
    ImageView location_mark, imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailedinformation);

        di_notice = (TextView) findViewById(R.id.di_notice);
        di_address = (TextView) findViewById(R.id.di_address);
        di_tel = (TextView) findViewById(R.id.di_tel);
        di_cpu = (TextView) findViewById(R.id.di_cpu);
        di_ram = (TextView) findViewById(R.id.di_ram);
        di_vga = (TextView) findViewById(R.id.di_vga);
        di_per = (TextView) findViewById(R.id.di_per);
        di_price = (TextView) findViewById(R.id.di_price);

        location_mark = (ImageView) findViewById(R.id.location_mark);
        imageView = (ImageView) findViewById(R.id.imageView);

        button = (Button) findViewById(R.id.button);

        get_intent = getIntent();
        position = get_intent.getIntExtra(POSITION, 0);

        pc = MainActivity.pc;

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(pc.getTitle());

        di_notice.setText(pc.getNotice());
        di_address.setText(pc.getSi() + " " + pc.getGu() + " " + pc.getDong() + " " + pc.getEtc_juso());
        di_tel.setText(pc.getTel());
        di_cpu.setText(pc.getCpu());
        di_ram.setText(pc.getRam());
        di_vga.setText(pc.getVga());
        di_per.setText(pc.getPeripheral());
        di_price.setText("1시간 당 " + pc.getPrice() + "원");
        String img_url = MainActivity.server + "pc_images/" + pc.getIcon();
        Glide.with(getApplicationContext()).load(img_url).into(imageView);

        location_mark.setOnClickListener(locationListener);
        di_tel.setOnClickListener(telListener);
        button.setOnClickListener(seatListener);
    }

    // 주소 보기 버튼 클릭
    View.OnClickListener locationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // 위치 확인 권한 확인
            if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions( getParent() , new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  }, 0 );
            }
            else {
                Intent send_intent = new Intent(getApplicationContext(), MapViewActivity.class);
                send_intent.putExtra(POSITION, position);
                startActivity(send_intent);
            }
        }
    };

    // 전화번호 버튼 클릭
    View.OnClickListener telListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tel_value[] = pc.getTel().split("-");
            String tel = "tel:";
            for(String str : tel_value)
                tel=tel.concat(str);

            Intent tel_intent = new Intent(Intent.ACTION_DIAL, Uri.parse(tel));
            startActivity(tel_intent);
        }
    };

    View.OnClickListener seatListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent seat_intent = new Intent(getApplication(), SeatNowActivity.class);
            startActivity(seat_intent);
        }
    };
}