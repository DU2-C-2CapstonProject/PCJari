package com.example.capston.pcjari.PC

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.capston.pcjari.MainActivity
import com.example.capston.pcjari.R
import jp.wasabeef.glide.transformations.CropCircleTransformation
import java.util.*
import kotlin.math.roundToInt

/**
 * Created by KangSeungho on 2017-10-30.
 */
class PCListAdapter(var mContext:Context) : BaseAdapter() {
    var pcItems: ArrayList<PCListItem> = ArrayList()

    override fun getCount(): Int {
        return pcItems.size
    }

    override fun getItem(position: Int): PCListItem {
        return pcItems[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        var holder:ViewHolder

        if (view == null) {
            val inflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.pc_list_view, parent, false)

            // 객체 생성
            holder = ViewHolder()
            holder.pc_img = view.findViewById<View?>(R.id.pc_img) as ImageView
            holder.pc_title = view.findViewById<View?>(R.id.pc_title) as TextView
            holder.pc_address = view.findViewById<View?>(R.id.pc_address) as TextView
            holder.pc_price = view.findViewById<View?>(R.id.pc_price) as TextView
            holder.pc_card = view.findViewById<View?>(R.id.pc_card) as TextView
            holder.pc_favoriteMark = view.findViewById<View?>(R.id.favoriteMark) as ImageView
            holder.pc_spaceSeat = view.findViewById<View?>(R.id.pc_spaceSeat) as TextView
            holder.pc_usingSeat = view.findViewById<View?>(R.id.pc_usingSeat) as TextView
            holder.pc_totalSeat = view.findViewById<View?>(R.id.pc_totalSeat) as TextView
            holder.pc_dist = view.findViewById<View?>(R.id.pc_dist) as TextView

            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        // 데이터 셋팅
        val pcItem = getItem(position)

        val img_url: String = MainActivity.server + "pc_images/" + pcItem.icon
        Glide.with(mContext).load(img_url).bitmapTransform(CropCircleTransformation(CustomBitmapPool())).into(holder.pc_img)

        holder.pc_title.text = pcItem.title
        holder.pc_address.text = "${pcItem.si} ${pcItem.gu} ${pcItem.dong}"
        holder.pc_price.text = "${pcItem.price}원"
        holder.pc_card.visibility = if(pcItem.isCard) View.VISIBLE else View.GONE
        holder.pc_favoriteMark.visibility = if(MainActivity.favorite.contains(pcItem.pcID)) View.VISIBLE else View.INVISIBLE

        if (pcItem.dist > 0) {
            if (pcItem.dist > 1) {
                val temp = (pcItem.dist * 10).roundToInt().toDouble()
                holder.pc_dist.text = "${temp / 10}km"

            } else
                holder.pc_dist.text = "${pcItem.dist * 1000}m"
        } else {
            holder.pc_dist.visibility = View.INVISIBLE
        }
        holder.pc_spaceSeat.text = pcItem.spaceSeat.toString()
        holder.pc_usingSeat.text = pcItem.usingSeat.toString()
        holder.pc_totalSeat.text = pcItem.totalSeat.toString()

        return view
    }

    fun setItem(pcItem: ArrayList<PCListItem>) {
        pcItems = ArrayList(pcItem)
    }

    internal inner class ViewHolder {
        lateinit var pc_img: ImageView
        lateinit var pc_title: TextView
        lateinit var pc_address: TextView
        lateinit var pc_price: TextView
        lateinit var pc_card: TextView
        lateinit var pc_favoriteMark: ImageView
        lateinit var pc_spaceSeat: TextView
        lateinit var pc_usingSeat: TextView
        lateinit var pc_totalSeat: TextView
        lateinit var pc_dist: TextView
    }
}