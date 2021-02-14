package com.example.capston.pcjari.activity.a111_location.location

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.capston.pcjari.R
import kotlinx.android.synthetic.main.item_address.view.*
import kotlin.collections.ArrayList

/**
 * Created by KangSeungho on 2017-11-29.
 */
class LocationAdapter(var mContext:Context) : BaseAdapter() {
    var locationList = ArrayList<LocationListItem>()

    override fun getCount(): Int {
        return locationList.size
    }

    override fun getItem(position: Int): LocationListItem {
        return locationList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder: ViewHolder
        val locationItem = getItem(position)

        if (view == null) {
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_address, parent, false) as View

            holder = ViewHolder()
            holder.address = view.address_text

            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        holder.address.text = locationItem.si + " " + locationItem.gu + " " + locationItem.dong
        return view
    }

    fun addItem(locationList: ArrayList<LocationListItem>) {
        this.locationList = locationList
    }

    class ViewHolder {
        lateinit var address: TextView
    }
}