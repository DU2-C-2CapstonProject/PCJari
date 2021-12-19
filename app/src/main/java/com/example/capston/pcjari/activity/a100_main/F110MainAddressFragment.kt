package com.example.capston.pcjari.activity.a100_main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.capston.pcjari.*
import com.example.capston.pcjari.base.BaseActivity
import com.example.capston.pcjari.activity.A111LocationActivity
import com.example.capston.pcjari.activity.a111_location.location.LocationListItem
import com.example.capston.pcjari.activity.A111LocationActivity.Companion.LOCATION_INFO
import com.example.capston.pcjari.databinding.F110FragmentAddressBinding
import com.example.capston.pcjari.pc.PCListResponse
import kotlinx.android.synthetic.main.f110_fragment_address.view.*
import kotlinx.android.synthetic.main.include_pc_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by KangSeungho on 2017-10-27.
 */
class F110MainAddressFragment : MainBaseFragment<F110FragmentAddressBinding>(R.layout.f110_fragment_address) {
    companion object {
        private var location = LocationListItem("경기도", "성남시 수정구", "복정동")
    }

    private lateinit var location_layer:ViewGroup
    private lateinit var location_text:TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        requireActivity().title = "주소로 찾기"
        val view = binding.root

        initUI(view)
        setListener()

        mListener = object : RefreshListener {
            override fun done(name: String?) {
                networkAPI.getPCListByAddress(0, location.gu, location.dong, name)
                        .enqueue(object : Callback<PCListResponse> {
                            override fun onResponse(call: Call<PCListResponse>, response: Response<PCListResponse>) {
                                Log.d(BaseActivity.TAG, "retrofit result : " + response.body())
                                val result = response.body() as PCListResponse

                                if(result.status == "OK") {
                                    pcListAdapter.setItem(result.pcList)
                                    pcListAdapter.notifyDataSetChanged()
                                }
                            }

                            override fun onFailure(call: Call<PCListResponse>, t: Throwable) {
                                Log.e(BaseActivity.TAG, "retrofit getPCListByAddress error")
                            }
                        })
            }
        }

        mListener.done(null)

        return view
    }

    private fun initUI(view : View) {
        location_layer = view.address_location
        location_text = view.address_location_text

        search_name = view.address_search_name
        search_button = view.location_search_button

        swipe = view.pc_list_swipe_layout
        listview = view.pc_listview
    }

    override fun setListener() {
        // 지역 설정 페이지로 이동
        location_layer.setOnClickListener {
            val intent = Intent(context, A111LocationActivity::class.java)
            startActivityForResult(intent, 0)
        }
        location_text.text = location.dong

        super.setListener()
    }

    // intent 이후 되돌아올 때 실행되는 메소드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0 && data != null) {
                location = data.getSerializableExtra(LOCATION_INFO) as LocationListItem
                location_text.text = location.dong
                mListener.done(null)
            }
        }
    }
}