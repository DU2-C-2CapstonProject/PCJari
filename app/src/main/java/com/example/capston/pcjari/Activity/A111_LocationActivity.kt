package com.example.capston.pcjari.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView.OnItemClickListener
import com.example.capston.pcjari.Activity.A111_Location.Location.LocationAdapter
import com.example.capston.pcjari.Activity.A111_Location.Location.LocationListItem
import com.example.capston.pcjari.Activity.A111_Location.Location.LocationListResponse
import com.example.capston.pcjari.Base.BaseActivity
import com.example.capston.pcjari.R
import kotlinx.android.synthetic.main.a111_activity_location.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by KangSeungho on 2017-11-05.
 */
class A111_LocationActivity : BaseActivity() {
    companion object {
        val LOCATION_INFO = "location"
    }

    private lateinit var locationAdapter: LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "주소 검색"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a111_activity_location)

        // 폰으로 엔터키 눌렀을 때 리스트 검색
        location_search_edit.setOnEditorActionListener { v, actionId, event ->
            if (v.id == R.id.location_search_edit && actionId == EditorInfo.IME_ACTION_DONE) {
                mysql_list_search()
            }

            return@setOnEditorActionListener false
        }
        location_search_button.setOnClickListener {
            mysql_list_search()
        }

        locationAdapter = LocationAdapter(this)
        location_listview.adapter = locationAdapter
        location_listview.onItemClickListener = locationClick
    }

    // 리스트의 지역을 클릭 했을 때 이전 엑티비티로 전환
    var locationClick: OnItemClickListener = OnItemClickListener { adapterView, view, position, id ->
        val location = locationAdapter.getItem(position)
        val intent = Intent(applicationContext, A100_MainActivity::class.java)
        intent.putExtra(LOCATION_INFO, location)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun mysql_list_search() {
        val dong = location_search_edit.getText().toString()
        location_search_text.text = " \"$dong\""
        if (dong != "") {
            networkAPI.getLocationList(dong)
                    .enqueue(object : Callback<LocationListResponse> {
                        override fun onResponse(call: Call<LocationListResponse>, response: Response<LocationListResponse>) {
                            val result = response.body() as LocationListResponse

                            if(result.status == "OK") {
                                val locationList = result.locationList as ArrayList<LocationListItem>

                                locationAdapter.addItem(locationList)
                                locationAdapter.notifyDataSetChanged()
                            }
                        }

                        override fun onFailure(call: Call<LocationListResponse>, t: Throwable) {
                            logE("mysql_list_search error!!")
                            logE(t.stackTraceToString())
                        }
                    })
        }
    }
}