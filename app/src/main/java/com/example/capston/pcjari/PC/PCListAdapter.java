package com.example.capston.pcjari.PC;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.capston.pcjari.MainActivity;
import com.example.capston.pcjari.R;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by KangSeungho on 2017-10-30.
 */

public class PCListAdapter extends BaseAdapter{
    ArrayList<PCListItem> pcItems = new ArrayList<>();
    ViewHolder viewHolder;

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

            // 객체 생성
            viewHolder = new ViewHolder();
            viewHolder.pc_img = (ImageView) convertView.findViewById(R.id.pc_img) ;
            viewHolder.pc_title = (TextView) convertView.findViewById(R.id.pc_title) ;
            viewHolder.pc_address = (TextView) convertView.findViewById(R.id.pc_address) ;
            viewHolder.pc_price = (TextView) convertView.findViewById(R.id.pc_price) ;
            viewHolder.pc_card = (TextView) convertView.findViewById(R.id.pc_card) ;
            viewHolder.pc_favoriteMark = (ImageView) convertView.findViewById(R.id.favoriteMark);
            viewHolder.pc_spaceSeat = (TextView) convertView.findViewById(R.id.pc_spaceSeat);
            viewHolder.pc_usingSeat = (TextView) convertView.findViewById(R.id.pc_usingSeat);
            viewHolder.pc_totalSeat = (TextView) convertView.findViewById(R.id.pc_totalSeat);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        // 데이터 셋팅
        PCListItem pcItem = getItem(position);
        String img_url = MainActivity.server + "pc_images/" + pcItem.getIcon();

        Glide.with(context).load(img_url).bitmapTransform(new CropCircleTransformation(new CustomBitmapPool())).into(viewHolder.pc_img);
        //pc_img.setImageResource(pcItem.getIcon());
        viewHolder.pc_title.setText(pcItem.getTitle());
        viewHolder.pc_address.setText(pcItem.getAddress());
        viewHolder.pc_price.setText(String.valueOf(pcItem.getPrice()+"원"));

        if(pcItem.isCard()) {
            viewHolder.pc_card.setVisibility(View.VISIBLE);
        } else {
            viewHolder.pc_card.setVisibility(View.INVISIBLE);
        }

        if(MainActivity.favorite.contains(pcItem.getPcID())) {
            viewHolder.pc_favoriteMark.setVisibility(View.VISIBLE);
        } else {
            viewHolder.pc_favoriteMark.setVisibility(View.INVISIBLE);
        }

        viewHolder.pc_spaceSeat.setText(String.valueOf(pcItem.getSpaceSeat()));
        viewHolder.pc_usingSeat.setText(String.valueOf(pcItem.getUsingSeat()));
        viewHolder.pc_totalSeat.setText(String.valueOf(pcItem.getTotalSeat()));

        return convertView;
    }

    public void addItem(PCListItem pcItem) {
        pcItems.add(pcItem);
    }

    class ViewHolder {
        ImageView pc_img;
        TextView pc_title;
        TextView pc_address;
        TextView pc_price;
        TextView pc_card;
        ImageView pc_favoriteMark;
        TextView pc_spaceSeat;
        TextView pc_usingSeat;
        TextView pc_totalSeat;
    }
}