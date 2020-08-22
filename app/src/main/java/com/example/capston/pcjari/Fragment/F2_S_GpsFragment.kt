package com.example.capston.pcjari.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capston.pcjari.*
import com.example.capston.pcjari.PC.PCListItem
import com.example.capston.pcjari.Util.GPSTracker
import kotlinx.android.synthetic.main.f2_s_fragment_gps.view.*
import java.util.*

/**
 * Created by KangSeungho on 2017-10-27.
 */
class F2_S_GpsFragment : F0_S_BaseFragment() {
    private lateinit var gps: GPSTracker
    private lateinit var dist: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        requireActivity().title = "내 주변"
        val view = inflater.inflate(R.layout.f2_s_fragment_gps, container, false)

        mListener = object : RefreshListener {
            override fun done(name: String?) {
                gps.Update()

                val lat = gps.getLatitude()
                val lng = gps.getLatitude()

                var pcItem: ArrayList<PCListItem> = ArrayList<PCListItem>()

                if(lat != null && lng != null)
                    pcItem = pcListManager.getPCListByGps(lat, lng, dist, name)

                pcListAdapter.setItem(pcItem)
                pcListAdapter.notifyDataSetChanged()
            }
        }

        initUI(view)
        setListener()

        gps = GPSTracker(requireActivity())
        dist = (main.dist.toDouble() / 10).toString()
        mListener.done(null)

        return view
    }

    private fun initUI(view : View) {
        search_name = view.gps_search_name
        search_button = view.gps_search_button

        swipe = view.gps_swipe
        listview = view.gps_listview
    }
}