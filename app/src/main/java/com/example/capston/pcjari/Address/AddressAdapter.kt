package com.example.capston.pcjari.Address

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.capston.pcjari.R
import kotlin.collections.ArrayList

/**
 * Created by KangSeungho on 2017-11-29.
 */
class AddressAdapter(var mContext:Context) : BaseAdapter() {
    lateinit var addressItems: ArrayList<String>

    override fun getCount(): Int {
        return addressItems.size
    }

    override fun getItem(position: Int): String {
        return addressItems[position]
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
            view = inflater.inflate(R.layout.address_list_view, parent, false) as View

            holder = ViewHolder()
            holder.address = view.findViewById<TextView>(R.id.address_)

            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        holder.address?.text = addressItem
        return view
    }

    fun addItem(addressItems: ArrayList<String>) {
        this.addressItems = addressItems
    }

    inner class ViewHolder {
        var address: TextView? = null
    }
}