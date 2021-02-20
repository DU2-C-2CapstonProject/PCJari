package com.example.capston.pcjari.activity.a111_location.location

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capston.pcjari.BR
import com.example.capston.pcjari.databinding.ItemAddressBinding
import kotlin.collections.ArrayList

/**
 * Created by KangSeungho on 2017-11-29.
 */
class LocationAdapter(private val mListener : OnLocationClickListener) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    private var mLocationList = ArrayList<LocationListItem>()

    override fun getItemCount(): Int {
        return mLocationList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val ui = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(ui)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mLocationList[position]

        holder.bind(item)
        holder.ui.root.setOnClickListener{ mListener.onClick(item) }
    }

    fun addItem(locationList: ArrayList<LocationListItem>) {
        this.mLocationList = locationList
    }

    class ViewHolder(public val ui: ItemAddressBinding) : RecyclerView.ViewHolder(ui.root) {
        fun bind(item : LocationListItem) {
            ui.setVariable(BR.locationItem, item)
        }
    }

    interface OnLocationClickListener {
        fun onClick(item : LocationListItem)
    }
}