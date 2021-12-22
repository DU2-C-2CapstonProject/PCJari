package com.example.capston.pcjari.pc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capston.pcjari.R
import com.example.capston.pcjari.databinding.ItemPcBinding

/**
 * Created by KangSeungho on 2017-10-30.
 */
class PCListAdapter : ListAdapter<PCListItem, PCListAdapter.PCItemViewHolder>(diff) {
    companion object {
        private val diff = object: DiffUtil.ItemCallback<PCListItem>() {
            override fun areItemsTheSame(oldItem: PCListItem, newItem: PCListItem): Boolean {
                return oldItem.pcID == newItem.pcID
            }

            override fun areContentsTheSame(oldItem: PCListItem, newItem: PCListItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var onClickListener: ((PCListItem) -> Unit)? = null
    private var onLongClickListener: ((Int, PCListItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (PCListItem) -> Unit) {
        onClickListener = listener
    }

    fun setOnItemLongClickListener(listener: (Int, PCListItem) -> Unit) {
        onLongClickListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PCItemViewHolder {
        return PCItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_pc, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PCItemViewHolder, position: Int) {
        holder.apply {
            val item = getItem(position)
            binding.item = item
            binding.root.setOnClickListener { onClickListener?.invoke(item) }
            binding.root.setOnLongClickListener {
                onLongClickListener?.invoke(position, item)
                true
            }
        }
    }

    inner class PCItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = DataBindingUtil.bind<ItemPcBinding>(view)!!
    }
}