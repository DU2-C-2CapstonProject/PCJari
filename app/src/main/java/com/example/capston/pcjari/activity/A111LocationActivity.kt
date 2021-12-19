package com.example.capston.pcjari.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capston.pcjari.activity.a111_location.location.LocationAdapter
import com.example.capston.pcjari.activity.a111_location.location.LocationListResponse
import com.example.capston.pcjari.base.BaseActivity
import com.example.capston.pcjari.R
import com.example.capston.pcjari.activity.a111_location.location.LocationListItem
import com.example.capston.pcjari.databinding.A111ActivityLocationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by KangSeungho on 2017-11-05.
 */
class A111LocationActivity : BaseActivity<A111ActivityLocationBinding>() {
    override fun getLayoutResId() = R.layout.a111_activity_location

    companion object {
        val LOCATION_INFO = "location"
    }

    lateinit var adapter : LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "주소 검색"
        super.onCreate(savedInstanceState)

        // 폰으로 엔터키 눌렀을 때 리스트 검색
        binding.locationSearchEdit.setOnEditorActionListener { v, actionId, event ->
            if (v.id == R.id.location_search_edit && actionId == EditorInfo.IME_ACTION_DONE) {
                mysql_list_search()
            }

            return@setOnEditorActionListener false
        }
        binding.locationSearchButton.setOnClickListener {
            mysql_list_search()
        }

        binding.locationRecyclerview.layoutManager = LinearLayoutManager(this)

        adapter = LocationAdapter(listener)
        binding.locationRecyclerview.adapter = adapter
    }

    private val listener = object : LocationAdapter.OnLocationClickListener {
        override fun onClick(item: LocationListItem) {
            // 리스트의 지역을 클릭 했을 때 이전 엑티비티로 전환
            val intent = Intent(applicationContext, A100MainActivity::class.java)
            intent.putExtra(LOCATION_INFO, item)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun mysql_list_search() {
        val dong = binding.locationSearchEdit.text.toString()
        binding.locationSearchText.text = " \"$dong\""
        if (dong != "") {
            networkAPI.getLocationList(dong)
                    .enqueue(object : Callback<LocationListResponse> {
                        override fun onResponse(call: Call<LocationListResponse>, response: Response<LocationListResponse>) {
                            val result = response.body() as LocationListResponse

                            if(result.status == "OK") {
                                adapter.addItem(result.locationList)
                                adapter.notifyDataSetChanged()
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