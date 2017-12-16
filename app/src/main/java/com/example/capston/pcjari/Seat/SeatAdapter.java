package com.example.capston.pcjari.Seat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
    ViewHolder viewHolder;
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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.space_list_view, parent, false);

            // 객체 생성
            viewHolder = new ViewHolder();
            viewHolder.space = (RelativeLayout) convertView.findViewById(R.id.SPACE);
            viewHolder.seat_PCNum = (RelativeLayout) convertView.findViewById(R.id.seat_PCNum);
            viewHolder.seat_AP = (RelativeLayout) convertView.findViewById(R.id.seat_AP);
            viewHolder.seat_DP = (RelativeLayout) convertView.findViewById(R.id.seat_DP);
            viewHolder.textView_PCNum = (TextView) convertView.findViewById(R.id.textView_PCNum);
            viewHolder.textView_RT = (TextView) convertView.findViewById(R.id.textView_RT);
            viewHolder.textView_ST = (TextView) convertView.findViewById(R.id.textView_ST);
            viewHolder.ImageView_SM = (ImageView) convertView.findViewById(R.id.ImageView_SM);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Seat seat = getItem(position);

        if(seat.getSeat_id() > 0) {
            viewHolder.space.setVisibility(VISIBLE);
            viewHolder.ImageView_SM.setVisibility(INVISIBLE);
            viewHolder.seat_PCNum.setVisibility(VISIBLE);
            viewHolder.seat_AP.setVisibility(INVISIBLE);
            viewHolder.seat_DP.setVisibility(INVISIBLE);
            viewHolder.textView_PCNum.setText(String.valueOf(seat.getSeat_id()));

            if(seat.getPc_state() == 0) {
                viewHolder.space.setBackgroundResource(R.drawable.seat_va);
            }

            else {
                int tmp = seat.getPc_time();
                int hour = tmp/60;
                int minute = tmp%60;

                if(seat.getPc_state() == 1) {
                    viewHolder.space.setBackgroundResource(R.drawable.seat_ap);
                    viewHolder.seat_AP.setVisibility(VISIBLE);
                    viewHolder.textView_RT.setText(String.valueOf(hour) + ":" + String.valueOf(minute));
                }
                else if(seat.getPc_state() == 2) {
                    viewHolder.space.setBackgroundResource(R.drawable.seat_dp);
                    viewHolder.seat_DP.setVisibility(VISIBLE);
                    viewHolder.textView_ST.setText(String.valueOf(hour) + ":" + String.valueOf(minute));
                }
            }
        }
        else if(seat.getSeat_id() == SMOKE) {
            viewHolder.space.setVisibility(VISIBLE);
            viewHolder.ImageView_SM.setVisibility(VISIBLE);
            viewHolder.seat_PCNum.setVisibility(INVISIBLE);
            viewHolder.space.setBackgroundResource(R.drawable.space_smoke);
        }

        else if(seat.getSeat_id() == COUNTER) {
            viewHolder.space.setVisibility(VISIBLE);
            viewHolder.ImageView_SM.setVisibility(INVISIBLE);
            viewHolder.seat_PCNum.setVisibility(INVISIBLE);
            viewHolder.space.setBackgroundResource(R.drawable.space_counter);
        }
        else if(seat.getSeat_id() == 0) {
            viewHolder.space.setVisibility(INVISIBLE);
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

    class ViewHolder {
        RelativeLayout space;
        RelativeLayout seat_PCNum;

        TextView textView_PCNum;

        RelativeLayout seat_AP, seat_DP;
        TextView textView_RT, textView_ST;

        ImageView ImageView_SM;
    }
}
