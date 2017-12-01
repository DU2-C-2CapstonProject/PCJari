package com.example.capston.pcjari.Seat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by KangSeungho on 2017-12-01.
 */

public class SeatItem extends LinearLayout {
    // TextView tv;  (예시)

    public SeatItem(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {
        // View view = LayoutInflater.from(context).inflate(리스트이름, this);
        // tv = (TextView) findViewById(R.id.tv1);  (예시)
    }

    public void setData(Seat seat) {
        // 데이터 삽입
        // tv.setText(seat.getName());  (예시)
    }
}
