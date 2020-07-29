package com.example.capston.pcjari

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import com.example.capston.pcjari.Seat.Seat
import com.example.capston.pcjari.Seat.SeatAdapter
import kotlinx.android.synthetic.main.activity_seatnow.*
import kotlinx.android.synthetic.main.item_seatnow.*
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
class SeatNowActivity : BaseActivity() {
    var seats: ArrayList<Seat> = ArrayList()
    lateinit var seatAdapter: SeatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "좌석현황"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seatnow)

        dataSetting()

        val v = (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.item_seatnow, null, false)

        val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val zoomView = ZoomView(applicationContext)
        zoomView.addView(v)
        zoomView.layoutParams = layoutParams
        zoomView.maxZoom = 4f

        seatnow_container.addView(zoomView)
    }

    fun dataSetting() {
        val url: String = MainActivity.Companion.server + "seat_search.php?id=" + MainActivity.pc.pcID
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

                    gridView1.numColumns = MainActivity.Companion.pc.seatLength
                    gridView1.adapter = seatAdapter
                    gridView1.isEnabled = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}