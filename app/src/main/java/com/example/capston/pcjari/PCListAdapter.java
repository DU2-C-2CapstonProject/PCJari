package com.example.capston.pcjari;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by KangSeungho on 2017-10-30.
 */

public class PCListAdapter extends BaseAdapter{
    private ArrayList<PCListItem> pcItems = new ArrayList<>();
    private ImageView pc_img;
    private TextView pc_title;
    private TextView pc_address;
    private TextView pc_price;
    private TextView pc_card;
    private ImageView pc_favoriteMark;
    private TextView pc_spaceSeat;
    private TextView pc_usingSeat;
    private TextView pc_totalSeat;

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
            if(inflater != null) {
                convertView = inflater.inflate(R.layout.pc_list_view, parent, false);
                pc_img = (ImageView) convertView.findViewById(R.id.pc_img);
                pc_title = (TextView) convertView.findViewById(R.id.pc_title);
                pc_address = (TextView) convertView.findViewById(R.id.pc_address);
                pc_price = (TextView) convertView.findViewById(R.id.pc_price);
                pc_card = (TextView) convertView.findViewById(R.id.pc_card);
                pc_favoriteMark = (ImageView) convertView.findViewById(R.id.favoriteMark);
                pc_spaceSeat = (TextView) convertView.findViewById(R.id.pc_spaceSeat);
                pc_usingSeat = (TextView) convertView.findViewById(R.id.pc_usingSeat);
                pc_totalSeat = (TextView) convertView.findViewById(R.id.pc_totalSeat);
            }
        }



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

        if(pcItem.isFavorite()) {
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

    public void addItem(Drawable img, String name, String address, int price, boolean card, boolean favorite, int spaceSeat, int usingSeat, int totalSeat) {
        PCListItem pcItem = new PCListItem();

        pcItem.setIcon(img);
        pcItem.setTitle(name);
        pcItem.setAddress(address);
        pcItem.setPrice(price);
        pcItem.setCard(card);
        pcItem.setFavorite(favorite);
        pcItem.setSpaceSeat(spaceSeat);
        pcItem.setUsingSeat(usingSeat);
        pcItem.setTotalSeat(totalSeat);

        pcItems.add(pcItem);
    }

    public void addItem(Drawable img, String name, String address, int price, boolean card) {
        PCListItem pcItem = new PCListItem();

        pcItem.setIcon(img);
        pcItem.setTitle(name);
        pcItem.setAddress(address);
        pcItem.setPrice(price);
        pcItem.setCard(card);

        pcItems.add(pcItem);
    }
}
