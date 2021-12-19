package com.example.capston.pcjari.activity.a111_address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capston.pcjari.R
import com.example.capston.pcjari.activity.a100_main.A100MainActivity
import com.example.capston.pcjari.activity.a111_address.address.AddressAdapter
import com.example.capston.pcjari.activity.a111_address.address.AddressListItem
import com.example.capston.pcjari.base.BaseActivity
import com.example.capston.pcjari.databinding.A111ActivityAddressBinding
import com.example.capston.pcjari.util.retrofit.BaseRetrofitCallback

/**
 * Created by KangSeungho on 2017-11-05.
 */
class A111AddressActivity : BaseActivity<A111ActivityAddressBinding>() {
    override fun getLayoutResId() = R.layout.a111_activity_address

    companion object {
        val LOCATION_INFO = "location"
    }

    lateinit var adapter : AddressAdapter

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

        adapter = AddressAdapter(listener)
        binding.locationRecyclerview.adapter = adapter
    }

    private val listener = object : AddressAdapter.OnLocationClickListener {
        override fun onClick(item: AddressListItem) {
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
                    .enqueue(object : BaseRetrofitCallback<ArrayList<AddressListItem>>(this) {
                        override fun onSuccess(response: ArrayList<AddressListItem>) {
                            adapter.addItem(response)
                            adapter.notifyDataSetChanged()
                        }
                    })
        }
    }
}