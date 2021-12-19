package com.example.capston.pcjari.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.example.capston.pcjari.activity.A200InfoActivity.Companion.PCITEM
import com.example.capston.pcjari.activity.a210_seat.seat.SeatAdapter
import com.example.capston.pcjari.activity.a210_seat.seat.SeatResponse
import com.example.capston.pcjari.base.BaseActivity
import com.example.capston.pcjari.pc.PCListItem
import com.example.capston.pcjari.R
import com.example.capston.pcjari.databinding.A210ActivitySeatBinding
import kotlinx.android.synthetic.main.a210_activity_seat.*
import kotlinx.android.synthetic.main.include_seat_info.*
import pl.polidea.view.ZoomView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by KangSeungho on 2017-11-15.
 */
class A210SeatActivity : BaseActivity<A210ActivitySeatBinding>() {
    override fun getLayoutResId() = R.layout.a210_activity_seat

    lateinit var pc : PCListItem
    lateinit var seatAdapter: SeatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "좌석현황"
        super.onCreate(savedInstanceState)

        pc = intent.getSerializableExtra(PCITEM) as PCListItem

        seatAdapter = SeatAdapter(this)

        dataSetting()

        val zoomView = ZoomView(this)
        val v = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.include_seat_info, null, false)
        zoomView.addView(v)
        zoomView.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        zoomView.maxZoom = 4f

        seat_root.addView(zoomView)

        seat_list.adapter = seatAdapter
        seat_list.layoutManager = GridLayoutManager(this, pc.seatLength)
        seat_list.isEnabled = false
    }

    fun dataSetting() {
        networkAPI.getSeatList(pc.pcID)
                .enqueue(object : Callback<SeatResponse> {
                    override fun onResponse(call: Call<SeatResponse>, response: Response<SeatResponse>) {
                        Log.d(BaseActivity.TAG, "retrofit result : " + response.body())
                        val result = response.body() as SeatResponse

                        if(result.status == "OK") {
                            seatAdapter.setItems(result.seatList)
                            seatAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(call: Call<SeatResponse>, t: Throwable) {
                        logE("seat data get error!")
                    }
                })
    }
}