package com.example.capston.pcjari.Activity

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.capston.pcjari.Activity.A3_InformationActivity.Companion.PCITEM
import com.example.capston.pcjari.PC.PCListItem
import com.example.capston.pcjari.R
import com.example.capston.pcjari.Activity.A4_SeatNowActivity.Seat.Seat
import com.example.capston.pcjari.Activity.A4_SeatNowActivity.Seat.SeatAdapter
import kotlinx.android.synthetic.main.a4_activity_seat.*
import kotlinx.android.synthetic.main.include_seat_info.*
import org.json.JSONObject
import pl.polidea.view.ZoomView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * Created by KangSeungho on 2017-11-15.
 */
class A4_SeatActivity : A0_BaseActivity() {
    lateinit var pc : PCListItem
    var seats: ArrayList<Seat> = ArrayList()
    lateinit var seatAdapter: SeatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "좌석현황"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a4_activity_seat)

        pc = intent.getSerializableExtra(PCITEM) as PCListItem

        dataSetting()

        val v = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.include_seat_info, null, false)

        val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val zoomView = ZoomView(applicationContext)
        zoomView.addView(v)
        zoomView.layoutParams = layoutParams
        zoomView.maxZoom = 4f

        seat_root.addView(zoomView)
    }

    fun dataSetting() {
        val url: String = A2_MainActivity.server + "seat_search.php?id=" + pc.pcID
        val gettingPHP = GettingPHP()
        gettingPHP.execute(url)
    }

    internal inner class GettingPHP : AsyncTask<String?, Int?, String?>() {
        override fun doInBackground(vararg params: String?): String? {
            val jsonHtml = StringBuilder()
            try {
                val phpUrl = URL(params[0])
                val conn = phpUrl.openConnection() as HttpURLConnection
                conn.connectTimeout = 10000
                conn.useCaches = false
                if (conn.responseCode == HttpURLConnection.HTTP_OK) {
                    val br = BufferedReader(InputStreamReader(conn.inputStream, "UTF-8"))
                    while (true) {
                        val line = br.readLine() ?: break
                        jsonHtml.append(line.trimIndent())
                    }
                    br.close()
                }
                conn.disconnect()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return jsonHtml.toString()
        }

        override fun onPostExecute(str: String?) {
            try {
                val jObject = JSONObject(str)
                val results = jObject.getJSONArray("results")
                if (jObject["status"] == "OK") {
                    seats.clear()
                    seats = ArrayList()
                    if (results.length() == 0) {
                        Toast.makeText(applicationContext, "데이터를 가져오던 중 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        for (i in 0 until results.length()) {
                            val temp = results.getJSONObject(i)
                            val seat = Seat()
                            seat.pc_id = temp.getInt("pc_id")
                            seat.place = temp.getInt("place_id")
                            seat.seat_id = temp.getInt("seat_id")
                            if (temp.getString("state") != "null") seat.pc_state = temp.getInt("state")
                            if (temp.getString("time") != "null") seat.pc_time = temp.getInt("time")
                            seats.add(seat)
                        }
                    }
                    seatAdapter = SeatAdapter(applicationContext)
                    seatAdapter.addSeats(seats)

                    seat_list.adapter = seatAdapter
                    seat_list.layoutManager = GridLayoutManager(applicationContext, pc.seatLength)
                    seat_list.isEnabled = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}