package com.example.capston.pcjari.activity.a100_main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capston.pcjari.*
import com.example.capston.pcjari.base.BaseActivity
import com.example.capston.pcjari.databinding.F120FragmentGpsBinding
import com.example.capston.pcjari.pc.PCListItem
import com.example.capston.pcjari.pc.PCListResponse
import com.example.capston.pcjari.util.GPSTracker
import kotlinx.android.synthetic.main.f120_fragment_gps.view.*
import kotlinx.android.synthetic.main.include_pc_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Created by KangSeungho on 2017-10-27.
 */
class F120MainGpsFragment : MainBaseFragment<F120FragmentGpsBinding>(R.layout.f120_fragment_gps) {
    private lateinit var gps: GPSTracker
    private lateinit var dist: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        requireActivity().title = "내 주변"
        val view = binding.root

        initUI(view)
        setListener()

        mListener = object : RefreshListener {
            override fun done(name: String?) {
                gps.update()

                val lat = gps.getLatitude()
                val lng = gps.getLongitude()

                var pcItem: ArrayList<PCListItem> = ArrayList<PCListItem>()

                if(lat != null && lng != null) {
                    networkAPI.getPCListByGps(1, lat, lng, dist, name)
                            .enqueue(object : Callback<PCListResponse> {
                                override fun onResponse(call: Call<PCListResponse>, response: Response<PCListResponse>) {
                                    Log.d(BaseActivity.TAG, "retrofit result : " + response.body())
                                    var result = response.body() as PCListResponse

                                    if(result.status == "OK") {
                                        pcListAdapter.setItem(result.pcList)
                                        pcListAdapter.notifyDataSetChanged()
                                    }
                                }

                                override fun onFailure(call: Call<PCListResponse>, t: Throwable) {
                                    Log.e(BaseActivity.TAG, "retrofit getPCListByGps error")
                                }
                            })
                }
            }
        }

        gps = GPSTracker(requireActivity())
        dist = (main.dist.toDouble() / 10).toString()
        mListener.done(null)

        return view
    }

    private fun initUI(view : View) {
        search_name = view.gps_search_name
        search_button = view.gps_search_button

        swipe = view.pc_list_swipe_layout
        listview = view.pc_listview
    }
}