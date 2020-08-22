package com.example.capston.pcjari.Activity.A4_SeatNowActivity.Seat

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.capston.pcjari.R
import kotlinx.android.synthetic.main.item_none_seat.view.*
import kotlinx.android.synthetic.main.item_seat.view.*
import java.util.*

/**
 * Created by KangSeungho on 2017-12-01.
 */
class SeatAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val SEAT = 1
        const val HALL = 0
        const val SMOKE = -1
        const val COUNTER = -2
    }

    var seats: ArrayList<Seat> = ArrayList()

    fun getItem(position: Int): Seat {
        return seats[position]
    }

    override fun getItemCount(): Int {
        return seats.size
    }

    override fun getItemViewType(position: Int): Int {
        val seat = getItem(position)
        var type = HALL

        if(seat.seat_id == HALL)
            type = HALL
        else if(seat.seat_id == SMOKE)
            type = SMOKE
        else if(seat.seat_id == COUNTER)
            type = COUNTER
        else
            type = SEAT

        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder: RecyclerView.ViewHolder

        if(viewType == SEAT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seat, parent, false) as View

            holder = SeatHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_none_seat, parent, false) as View

            holder = NoneSeatHolder(view)
        }

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val seat = getItem(position)
        val viewType = getItemViewType(position)

        if(viewType == SEAT)
            showSeat(seat, holder as SeatHolder)
        else if(viewType == HALL)
            showHall(seat, holder as NoneSeatHolder)
        else if(viewType == SMOKE)
            showSmokePlace(seat, holder as NoneSeatHolder)
        else if(viewType == COUNTER)
            showCounter(seat, holder as NoneSeatHolder)
    }

    @SuppressLint("SetTextI18n")
    private fun showSeat(seat: Seat, holder: SeatHolder) {
        holder.number.text = seat.seat_id.toString()

        if(seat.pc_state == 0) {
            holder.background.setBackgroundResource(R.drawable.seat_va)
            holder.payment.visibility = View.INVISIBLE
            holder.state.visibility = View.INVISIBLE
            holder.time.visibility = View.INVISIBLE
        } else {
            holder.payment.visibility = View.VISIBLE
            holder.state.visibility = View.VISIBLE
            holder.time.visibility = View.VISIBLE

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
            holder.time.text = "$str_hour:$str_minute"

            if(seat.pc_state == 1) {
                holder.background.setBackgroundResource(R.drawable.seat_ap)

                holder.payment.setTextColor(context.getColor(R.color.seat_ap))
                holder.payment.text = "선불"
                holder.state.text = "남음"
            } else if(seat.pc_state == 2) {
                holder.background.setBackgroundResource(R.drawable.seat_dp)

                holder.payment.setTextColor(context.getColor(R.color.seat_dp))
                holder.payment.text = "후불"
                holder.state.text = "시작"
            }
        }
    }

    private fun showHall(seat: Seat, holder: NoneSeatHolder) {
        holder.background.setBackgroundColor(0)
        holder.smoke.visibility = View.GONE
    }

    private fun showSmokePlace(seat: Seat, holder: NoneSeatHolder) {
        holder.background.setBackgroundColor(context.getColor(R.color.space_smoke))
        holder.smoke.visibility = View.VISIBLE
    }

    private fun showCounter(seat: Seat, holder: NoneSeatHolder) {
        holder.background.setBackgroundColor(context.getColor(R.color.space_counter))
        holder.smoke.visibility = View.GONE
    }

    fun addSeats(seats: ArrayList<Seat>) {
        this.seats.clear()
        this.seats.addAll(seats)
    }

    inner class SeatHolder constructor(view: View) : RecyclerView.ViewHolder(view){
        var background: RelativeLayout = view.seat_background
        var number: TextView = view.seat_number
        var time: TextView = view.seat_time
        var payment: TextView = view.seat_payment
        var state: TextView = view.seat_state
    }

    inner class NoneSeatHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        var background: RelativeLayout = view.none_seat_background
        var smoke: ImageView = view.none_seat_smoke
    }
}