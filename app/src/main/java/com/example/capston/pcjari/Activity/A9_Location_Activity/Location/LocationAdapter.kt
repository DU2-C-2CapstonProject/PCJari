package com.example.capston.pcjari.Activity.A9_Location_Activity.Location

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
    lateinit var locationList: ArrayList<String>

    override fun getCount(): Int {
        return locationList.size
    }

    override fun getItem(position: Int): String {
        return locationList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        val holder:ViewHolder
        val addressItem = getItem(position)

        if (view == null) {
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.item_address, parent, false) as View

            holder = ViewHolder()
            holder.address = view.address_text

            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        holder.address.text = addressItem
        return view
    }

    fun addItem(addressItems: ArrayList<String>) {
        this.locationList = addressItems
    }

    class ViewHolder {
        lateinit var address: TextView
    }
}