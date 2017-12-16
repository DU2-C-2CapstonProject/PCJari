package com.example.capston.pcjari.Seat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.capston.pcjari.R;

/**
 * Created by KangSeungho on 2017-12-01.
 */

public class SeatItem extends RelativeLayout {
    RelativeLayout space;
    RelativeLayout seat_PCNum;

    TextView textView_PCNum;

    RelativeLayout seat_AP, seat_DP;
    TextView textView_RT, textView_ST;

    ImageView ImageView_SM;

    final static int SMOKE=-1, COUNTER=-2;

    public SeatItem(Context context) {
        super(context);
        init(context);
    }

    public void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.space_list_view, this);

        space = (RelativeLayout) findViewById(R.id.SPACE);
        seat_PCNum = (RelativeLayout) findViewById(R.id.seat_PCNum);
        seat_AP = (RelativeLayout) findViewById(R.id.seat_AP);
        seat_DP = (RelativeLayout) findViewById(R.id.seat_DP);
        textView_PCNum = (TextView) findViewById(R.id.textView_PCNum);
        textView_RT = (TextView) findViewById(R.id.textView_RT);
        textView_ST = (TextView) findViewById(R.id.textView_ST);
        ImageView_SM = (ImageView) findViewById(R.id.ImageView_SM);
    }

    public void setData(Seat seat) {
        if(seat.getSeat_id() > 0) {
            seat_PCNum.setVisibility(VISIBLE);
            textView_PCNum.setText(String.valueOf(seat.getSeat_id()));

            if(seat.getPc_state() == 0) {
                space.setBackgroundResource(R.drawable.seat_va);
            }

            else {
                int tmp = seat.getPc_time();
                int hour = tmp/60;
                int minute = tmp%60;

                if(seat.getPc_state() == 1) {
                    space.setBackgroundResource(R.drawable.seat_ap);
                    seat_AP.setVisibility(VISIBLE);
                    textView_RT.setText(String.valueOf(hour) + ":" + String.valueOf(minute));
                }
                else if(seat.getPc_state() == 2) {
                    space.setBackgroundResource(R.drawable.seat_dp);
                    seat_DP.setVisibility(VISIBLE);
                    textView_ST.setText(String.valueOf(hour) + ":" + String.valueOf(minute));
                }
            }
        }
        else if(seat.getSeat_id() == SMOKE) {
            space.setBackgroundResource(R.drawable.space_smoke);
            ImageView_SM.setVisibility(VISIBLE);
        }

        else if(seat.getSeat_id() == COUNTER) {
            space.setBackgroundResource(R.drawable.space_counter);
        }
        else if(seat.getSeat_id() == 0) {
            space.setVisibility(INVISIBLE);
        }
    }
}
