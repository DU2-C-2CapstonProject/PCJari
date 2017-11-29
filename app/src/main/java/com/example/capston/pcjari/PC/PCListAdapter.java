package com.example.capston.pcjari.PC;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capston.pcjari.MainActivity;
import com.example.capston.pcjari.R;

import java.util.ArrayList;

/**
 * Created by KangSeungho on 2017-10-30.
 */

public class PCListAdapter extends BaseAdapter{
    private ArrayList<PCListItem> pcItems = new ArrayList<>();

    @Override
    public int getCount() {
        return pcItems.size();
    }

    @Override
    public PCListItem getItem(int position) {
        return pcItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pc_list_view, parent, false);
        }

        ImageView pc_img = (ImageView) convertView.findViewById(R.id.pc_img) ;
        TextView pc_title = (TextView) convertView.findViewById(R.id.pc_title) ;
        TextView pc_address = (TextView) convertView.findViewById(R.id.pc_address) ;
        TextView pc_price = (TextView) convertView.findViewById(R.id.pc_price) ;
        TextView pc_card = (TextView) convertView.findViewById(R.id.pc_card) ;
        ImageView pc_favoriteMark = (ImageView) convertView.findViewById(R.id.favoriteMark);
        TextView pc_spaceSeat = (TextView) convertView.findViewById(R.id.pc_spaceSeat);
        TextView pc_usingSeat = (TextView) convertView.findViewById(R.id.pc_usingSeat);
        TextView pc_totalSeat = (TextView) convertView.findViewById(R.id.pc_totalSeat);

        PCListItem pcItem = getItem(position);

        pc_img.setImageDrawable(pcItem.getIcon());
        pc_title.setText(pcItem.getTitle());
        pc_address.setText(pcItem.getAddress());
        pc_price.setText(String.valueOf(pcItem.getPrice()+"원"));

        if(pcItem.isCard()) {
            pc_card.setVisibility(View.VISIBLE);
        } else {
            pc_card.setVisibility(View.INVISIBLE);
        }

        if(MainActivity.favorite.contains(pcItem.getPcID())) {
            pc_favoriteMark.setVisibility(View.VISIBLE);
        } else {
            pc_favoriteMark.setVisibility(View.INVISIBLE);
        }

        pc_spaceSeat.setText(String.valueOf(pcItem.getSpaceSeat()));
        pc_usingSeat.setText(String.valueOf(pcItem.getUsingSeat()));
        pc_totalSeat.setText(String.valueOf(pcItem.getTotalSeat()));

        return convertView;
    }

    public void addItem(PCListItem pcItem) {
        pcItems.add(pcItem);
    }
}