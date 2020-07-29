package com.example.capston.pcjari.Seat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.capston.pcjari.R
import java.util.*

/**
 * Created by KangSeungho on 2017-12-01.
 */
class SeatAdapter(var context: Context) : BaseAdapter() {
    var seats: ArrayList<Seat> = ArrayList()

    override fun getCount(): Int {
        return seats.size
    }

    override fun getItem(position: Int): Seat {
        return seats[position]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        val seat = getItem(position)

        var seatHolder: SeatHolder
        var defaultHolder: DefaultHolder

        if (seat.seat_id == 0) {                // 복도
            if (view == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                view = inflater.inflate(R.layout.hall_list_view, parent, false)
                defaultHolder = DefaultHolder()
                view.tag = defaultHolder
            } else {
                defaultHolder = view.tag as DefaultHolder
            }
        } else if (seat.seat_id > 0) {            // 좌석
            if (view == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                view = inflater.inflate(R.layout.space_list_view, parent, false)

                // 객체 생성
                seatHolder = SeatHolder()
                seatHolder.space = view.findViewById<View?>(R.id.SPACE) as RelativeLayout
                seatHolder.textView_PCNum = view.findViewById<View?>(R.id.textView_PCNum) as TextView
                seatHolder.textView_Time = view.findViewById<View?>(R.id.textView_Time) as TextView
                seatHolder.textView_seat_payment = view.findViewById<View?>(R.id.textView_seat_payment) as TextView
                seatHolder.textView_seat_state = view.findViewById<View?>(R.id.textView_seat_state) as TextView
                view.tag = seatHolder
            } else {
                seatHolder = view.tag as SeatHolder
            }
            seatHolder.textView_PCNum.text = seat.seat_id.toString()
            if (seat.pc_state == 0) {
                seatHolder.space.setBackgroundResource(R.drawable.seat_va)
                seatHolder.textView_seat_payment.visibility = View.INVISIBLE
                seatHolder.textView_seat_state.visibility = View.INVISIBLE
                seatHolder.textView_Time.visibility = View.INVISIBLE
            } else {
                val tmp = seat.pc_time
                val hour = tmp / 60
                val minute = tmp % 60
                var str_hour = "0"
                var str_minute = "0"
                if (hour < 10) {
                    str_hour += hour
                } else {
                    str_hour = hour.toString()
                }
                if (minute < 10) {
                    str_minute += minute
                } else {
                    str_minute = minute.toString()
                }
                seatHolder.textView_Time.text = "$str_hour:$str_minute"
                seatHolder.textView_seat_payment.visibility = View.VISIBLE
                seatHolder.textView_seat_state.visibility = View.VISIBLE
                seatHolder.textView_Time.visibility = View.VISIBLE
                if (seat.pc_state == 1) {
                    seatHolder.space.setBackgroundResource(R.drawable.seat_ap)
                    seatHolder.textView_seat_payment.setTextColor(ContextCompat.getColor(context, R.color.seat_ap))
                    seatHolder.textView_seat_payment.text = "선불"
                    seatHolder.textView_seat_state.text = "남음"
                } else if (seat.pc_state == 2) {
                    seatHolder.space.setBackgroundResource(R.drawable.seat_dp)
                    seatHolder.textView_seat_payment.setTextColor(ContextCompat.getColor(context, R.color.seat_dp))
                    seatHolder.textView_seat_payment.text = "후불"
                    seatHolder.textView_seat_state.text = "시작"
                }
            }
        } else if (seat.seat_id == SMOKE) {
            if (view == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                view = inflater.inflate(R.layout.smoke_list_view, parent, false)
                defaultHolder = DefaultHolder()
                view.tag = defaultHolder
            } else {
                defaultHolder = view.tag as DefaultHolder
            }
        } else if (seat.seat_id == COUNTER) {
            if (view == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                view = inflater.inflate(R.layout.counter_list_view, parent, false)
                defaultHolder = DefaultHolder()
                view.tag = defaultHolder
            } else {
                defaultHolder = view.tag as DefaultHolder
            }
        }

        return view
    }

    fun addSeats(seats: ArrayList<Seat>) {
        this.seats.clear()
        this.seats.addAll(seats)
    }

    internal inner class SeatHolder {
        lateinit var space: RelativeLayout
        lateinit var textView_PCNum: TextView
        lateinit var textView_Time: TextView
        lateinit var textView_seat_payment: TextView
        lateinit var textView_seat_state: TextView
    }

    internal inner class DefaultHolder
    companion object {
        const val SMOKE = -1
        const val COUNTER = -2
    }

}