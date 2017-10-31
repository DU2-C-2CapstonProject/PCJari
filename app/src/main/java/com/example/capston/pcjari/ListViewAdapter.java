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

public class ListViewAdapter extends BaseAdapter{
    private ArrayList<ListViewItem> pcItems = new ArrayList<>();

    @Override
    public int getCount() {
        return pcItems.size();
    }

    @Override
    public ListViewItem getItem(int position) {
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

        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img) ;
        TextView pc_name = (TextView) convertView.findViewById(R.id.pc_name) ;
        TextView pc_address = (TextView) convertView.findViewById(R.id.pc_address) ;
        TextView pc_price = (TextView) convertView.findViewById(R.id.pc_price) ;
        TextView pc_card = (TextView) convertView.findViewById(R.id.pc_card) ;

        ListViewItem pcItem = getItem(position);

        iv_img.setImageDrawable(pcItem.getIcon());
        pc_name.setText(pcItem.getTitle());
        pc_address.setText(pcItem.getAddress());
        pc_price.setText(String.valueOf(pcItem.getPrice()+"원"));

        if(pcItem.isCard()) {
            pc_card.setVisibility(View.VISIBLE);
        } else {
            pc_card.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }

    public void addItem(Drawable img, String name, String address, int price, boolean card) {
        ListViewItem pcItem = new ListViewItem();

        pcItem.setIcon(img);
        pcItem.setTitle(name);
        pcItem.setAddress(address);
        pcItem.setPrice(price);
        pcItem.setCard(card);

        pcItems.add(pcItem);
    }
}
