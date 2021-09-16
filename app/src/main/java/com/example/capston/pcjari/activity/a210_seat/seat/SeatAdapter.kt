package com.example.capston.pcjari.activity.a210_seat.seat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capston.pcjari.R
import com.example.capston.pcjari.BR
import com.example.capston.pcjari.databinding.ItemSeatPcBinding
import java.util.*

/**
 * Created by KangSeungho on 2017-12-01.
 */
class SeatAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var seats: ArrayList<Seat> = ArrayList()

    fun getItem(position: Int): Seat {
        return seats[position]
    }

    override fun getItemCount(): Int {
        return seats.size
    }

    override fun getItemViewType(position: Int): Int {
        val seat = getItem(position)
        val type : Int

        type = if(seat.seatId == Seat.SEAT_ID_HALL)
            Seat.SEAT_ID_HALL
        else if(seat.seatId == Seat.SEAT_ID_SMOKE)
            Seat.SEAT_ID_SMOKE
        else if(seat.seatId == Seat.SEAT_ID_COUNTER)
            Seat.SEAT_ID_COUNTER
        else
            Seat.SEAT_ID_SEAT

        return type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder: RecyclerView.ViewHolder

        if(viewType == Seat.SEAT_ID_SEAT) {
            val ui = ItemSeatPcBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            holder = SeatHolder(ui)
        } else if(viewType == Seat.SEAT_ID_SMOKE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seat_smoke, parent, false) as View
            holder = NoneSeatHolder(view)
        } else if(viewType == Seat.SEAT_ID_COUNTER) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seat_counter, parent, false) as View
            holder = NoneSeatHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_seat_hall, parent, false) as View
            holder = NoneSeatHolder(view)
        }

        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val seat = getItem(position)
        val viewType = getItemViewType(position)

        if(viewType == Seat.SEAT_ID_SEAT)
            (holder as SeatHolder).bind(seat)
    }

    fun setItems(seats: ArrayList<Seat>) {
        this.seats.clear()
        this.seats.addAll(seats)
    }

    class SeatHolder(private val ui: ItemSeatPcBinding) : RecyclerView.ViewHolder(ui.root) {
        fun bind(item : Seat) {
            ui.setVariable(BR.seatItem, item)
        }
    }

    class NoneSeatHolder constructor(view: View) : RecyclerView.ViewHolder(view)
}