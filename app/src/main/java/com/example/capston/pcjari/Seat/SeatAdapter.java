package com.example.capston.pcjari.Seat;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.capston.pcjari.R;

import java.util.ArrayList;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

/**
 * Created by KangSeungho on 2017-12-01.
 */

public class SeatAdapter extends BaseAdapter{
    Context context;
    ArrayList<Seat> seats = new ArrayList<Seat>();
    SeatHolder seatHolder;
    DefaultHolder defaultHolder;
    final static int SMOKE=-1, COUNTER=-2;

    public SeatAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return seats.size();
    }

    @Override
    public Seat getItem(int position) {
        return seats.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*
        if(convertView == null) {
            convertView = new SeatItem(context);
        }

        ((SeatItem) convertView).setData(seats.get(position));
        */
        Seat seat = getItem(position);

        if(seat.getSeat_id() == 0) {                // 복도
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.hall_list_view, parent, false);
                defaultHolder = new DefaultHolder();

                convertView.setTag(defaultHolder);
            } else {
                defaultHolder = (DefaultHolder)convertView.getTag();
            }
        }

        else if(seat.getSeat_id() > 0) {            // 좌석
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.space_list_view, parent, false);

                // 객체 생성
                seatHolder = new SeatHolder();
                seatHolder.space = (RelativeLayout) convertView.findViewById(R.id.SPACE);
                seatHolder.textView_PCNum = (TextView) convertView.findViewById(R.id.textView_PCNum);
                seatHolder.textView_Time = (TextView) convertView.findViewById(R.id.textView_Time);
                seatHolder.textView_seat_payment = (TextView) convertView.findViewById(R.id.textView_seat_payment);
                seatHolder.textView_seat_state = (TextView) convertView.findViewById(R.id.textView_seat_state);

                convertView.setTag(seatHolder);
            } else {
                seatHolder = (SeatHolder)convertView.getTag();
            }

            seatHolder.textView_PCNum.setText(String.valueOf(seat.getSeat_id()));

            if(seat.getPc_state() == 0) {
                seatHolder.space.setBackgroundResource(R.drawable.seat_va);
                seatHolder.textView_seat_payment.setVisibility(INVISIBLE);
                seatHolder.textView_seat_state.setVisibility(INVISIBLE);
                seatHolder.textView_Time.setVisibility(INVISIBLE);
            }

            else {
                int tmp = seat.getPc_time();
                int hour = tmp/60;
                int minute = tmp%60;

                String str_hour="0";
                String str_minute="0";

                if(hour < 10) {
                    str_hour += hour;
                } else {
                    str_hour = String.valueOf(hour);
                }

                if(minute < 10) {
                    str_minute += minute;
                } else {
                    str_minute = String.valueOf(minute);
                }

                seatHolder.textView_Time.setText(str_hour + ":" + str_minute);
                seatHolder.textView_seat_payment.setVisibility(VISIBLE);
                seatHolder.textView_seat_state.setVisibility(VISIBLE);
                seatHolder.textView_Time.setVisibility(VISIBLE);

                if(seat.getPc_state() == 1) {
                    seatHolder.space.setBackgroundResource(R.drawable.seat_ap);
                    seatHolder.textView_seat_payment.setTextColor(ContextCompat.getColor(context, R.color.seat_ap));
                    seatHolder.textView_seat_payment.setText("선불");
                    seatHolder.textView_seat_state.setText("남음");
                }
                else if(seat.getPc_state() == 2) {
                    seatHolder.space.setBackgroundResource(R.drawable.seat_dp);
                    seatHolder.textView_seat_payment.setTextColor(ContextCompat.getColor(context, R.color.seat_dp));
                    seatHolder.textView_seat_payment.setText("후불");
                    seatHolder.textView_seat_state.setText("시작");
                }
            }
        }

        else if(seat.getSeat_id() == SMOKE) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.smoke_list_view, parent, false);

                defaultHolder = new DefaultHolder();

                convertView.setTag(defaultHolder);
            } else {
                defaultHolder = (DefaultHolder)convertView.getTag();
            }
        }

        else if(seat.getSeat_id() == COUNTER) {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.counter_list_view, parent, false);

                defaultHolder = new DefaultHolder();

                convertView.setTag(defaultHolder);
            } else {
                defaultHolder = (DefaultHolder)convertView.getTag();
            }
        }

        /*
        RelativeLayout space;
        RelativeLayout seat_PCNum;

        TextView textView_PCNum;

        RelativeLayout seat_AP, seat_DP;
        TextView textView_RT, textView_ST;

        ImageView ImageView_SM;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.space_list_view, parent, false);
        }

        space = (RelativeLayout) convertView.findViewById(R.id.SPACE);
        seat_PCNum = (RelativeLayout) convertView.findViewById(R.id.seat_PCNum);
        seat_AP = (RelativeLayout) convertView.findViewById(R.id.seat_AP);
        seat_DP = (RelativeLayout) convertView.findViewById(R.id.seat_DP);
        textView_PCNum = (TextView) convertView.findViewById(R.id.textView_PCNum);
        textView_RT = (TextView) convertView.findViewById(R.id.textView_RT);
        textView_ST = (TextView) convertView.findViewById(R.id.textView_ST);
        ImageView_SM = (ImageView) convertView.findViewById(R.id.ImageView_SM);

        Seat seat = getItem(position);

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
        */

        return convertView;
    }

    public void addSeats(ArrayList<Seat> seats) {
        this.seats.clear();
        this.seats.addAll(seats);
    }

    class SeatHolder {
        RelativeLayout space;
        TextView textView_PCNum, textView_Time, textView_seat_payment, textView_seat_state;
    }

    class DefaultHolder {}
}
