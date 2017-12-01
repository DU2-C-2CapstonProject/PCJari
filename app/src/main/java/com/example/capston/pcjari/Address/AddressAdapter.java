package com.example.capston.pcjari.Address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.capston.pcjari.R;

import java.util.ArrayList;

/**
 * Created by KangSeungho on 2017-11-29.
 */

public class AddressAdapter extends BaseAdapter {
    ArrayList<String> addressItems;
    ViewHolder viewHolder;

    @Override
    public int getCount() {
        return addressItems.size();
    }

    @Override
    public String getItem(int position) {
        return addressItems.get(position);
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
            convertView = inflater.inflate(R.layout.address_list_view, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.address = (TextView) convertView.findViewById(R.id.address_);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String addressItem = getItem(position);
        viewHolder.address.setText(addressItem);

        return convertView;
    }

    public void addItem(ArrayList<String> addressItems) {
        this.addressItems = addressItems;
    }

    class ViewHolder {
        TextView address;
    }
}
