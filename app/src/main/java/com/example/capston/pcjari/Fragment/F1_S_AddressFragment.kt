package com.example.capston.pcjari.Fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.capston.pcjari.*
import com.example.capston.pcjari.Activity.A9_LocationActivity
import kotlinx.android.synthetic.main.f1_s_fragment_address.view.*

/**
 * Created by KangSeungho on 2017-10-27.
 */
class F1_S_AddressFragment : F0_S_BaseFragment() {
    companion object {
        private val address: Array<String> = arrayOf("경기도", "성남시 수정구", "복정동")
    }

    private lateinit var location:ViewGroup
    private lateinit var location_text:TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        requireActivity().title = "주소로 찾기"
        val view = inflater.inflate(R.layout.f1_s_fragment_address, container, false)

        mListener = object : RefreshListener {
            override fun done(name: String?) {
                val pcList = pcListManager.getPCListByAddress(address[1], address[2], name)

                pcListAdapter.setItem(pcList)
                pcListAdapter.notifyDataSetChanged()
            }
        }

        initUI(view)
        this.setListener()

        mListener.done(null)

        return view
    }

    private fun initUI(view : View) {
        location = view.address_location
        location_text = view.address_location_text

        search_name = view.address_search_name
        search_button = view.address_search_button

        swipe = view.address_swipe
        listview = view.address_listview
    }

    override fun setListener() {
        // 지역 설정 페이지로 이동
        location.setOnClickListener {
            val intent = Intent(context, A9_LocationActivity::class.java)
            startActivityForResult(intent, 0)
        }
        location_text.text = address[2]

        super.setListener()
    }

    // intent 이후 되돌아올 때 실행되는 메소드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                val tmp: Array<String?>? = data.getStringExtra("address")?.split(" ".toRegex())?.toTypedArray()
                tmp?.get(0)?.let { address[0] = it }

                if (tmp != null) {
                    if (tmp.size > 3) {
                        address[1] = tmp[1] + " " + tmp[2]
                        address[2] = tmp[3].toString()
                    } else {
                        address[1] = tmp[1].toString()
                        address[2] = tmp[2].toString()
                    }
                }
                location_text.text = address[2]
                mListener.done(null)
            }
        }
    }
}