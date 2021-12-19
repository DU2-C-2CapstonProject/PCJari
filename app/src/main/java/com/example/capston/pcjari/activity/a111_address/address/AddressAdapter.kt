package com.example.capston.pcjari.activity.a111_address.address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capston.pcjari.BR
import com.example.capston.pcjari.databinding.ItemAddressBinding

/**
 * Created by KangSeungho on 2017-11-29.
 */
class AddressAdapter(private val mListener : OnLocationClickListener) : RecyclerView.Adapter<AddressAdapter.ViewHolder>() {
    private var mAddressList = ArrayList<AddressListItem>()

    override fun getItemCount(): Int {
        return mAddressList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val ui = ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(ui)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mAddressList[position]

        holder.bind(item)
        holder.ui.root.setOnClickListener{ mListener.onClick(item) }
    }

    fun addItem(addressList: ArrayList<AddressListItem>) {
        this.mAddressList = addressList
    }

    class ViewHolder(public val ui: ItemAddressBinding) : RecyclerView.ViewHolder(ui.root) {
        fun bind(item : AddressListItem) {
            ui.setVariable(BR.address, item)
        }
    }

    interface OnLocationClickListener {
        fun onClick(item : AddressListItem)
    }
}