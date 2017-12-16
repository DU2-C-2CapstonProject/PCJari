package com.example.capston.pcjari.Seat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by KangSeungho on 2017-12-01.
 */

public class SeatAdapter extends BaseAdapter{
    Context context;
    ArrayList<Seat> seats = new ArrayList<Seat>();

    public SeatAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return seats.size();
    }

    @Override
    public Object getItem(int position) {
        return seats.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = new SeatItem(context);
        }

        ((SeatItem) convertView).setData(seats.get(position));

        return convertView;
    }

    public void addSeats(ArrayList<Seat> seats) {
        this.seats.clear();
        this.seats.addAll(seats);
    }
}
