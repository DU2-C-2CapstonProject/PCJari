package com.example.capston.pcjari.pc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.capston.pcjari.R
import com.example.capston.pcjari.databinding.ItemPcBinding
import java.util.*

/**
 * Created by KangSeungho on 2017-10-30.
 */
class PCListAdapter : RecyclerView.Adapter<PCListAdapter.PCItemViewHolder>() {
    private var pcItems: ArrayList<PCListItem> = ArrayList()
    private var onClickListener: ((PCListItem) -> Unit)? = null
    private var onLongClickListener: ((PCListItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (PCListItem) -> Unit) {
        onClickListener = listener
    }

    fun setOnItemLongClickListener(listener: (PCListItem) -> Unit) {
        onLongClickListener = listener
    }

    override fun getItemCount() = pcItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PCItemViewHolder {
        return PCItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pc, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PCItemViewHolder, position: Int) {
        holder.apply {
            val item = pcItems[position]
            binding.item = item
            binding.root.setOnClickListener { onClickListener?.invoke(item) }
            binding.root.setOnLongClickListener {
                onLongClickListener?.invoke(item)
                true
            }
        }
    }

    fun setItem(pcItem: ArrayList<PCListItem>) {
        pcItems = pcItem
    }

    inner class PCItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemPcBinding>(view)!!
    }
}