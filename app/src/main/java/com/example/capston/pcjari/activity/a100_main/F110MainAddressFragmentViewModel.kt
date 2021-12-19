package com.example.capston.pcjari.activity.a100_main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capston.pcjari.PCJariApplication
import com.example.capston.pcjari.activity.a111_address.address.AddressListItem
import com.example.capston.pcjari.pc.PCListItem
import com.example.capston.pcjari.util.retrofit.BaseRetrofitCallback
import com.example.capston.pcjari.util.retrofit.RetrofitClient

class F110MainAddressFragmentViewModel : ViewModel() {
    private val retroRepos = RetrofitClient.getInstance()

    private val _address = MutableLiveData(AddressListItem("경기도", "성남시 수정구", "복정동"))
    val address: LiveData<AddressListItem> get() = _address

    private val _pcList = MutableLiveData<ArrayList<PCListItem>>()
    val pcList: LiveData<ArrayList<PCListItem>> get() = _pcList

    val searchName = MutableLiveData<String>()

    fun setLocation(address: AddressListItem) {
        _address.value = address
    }

    fun getPCList() {
        address.value?.run {
            retroRepos.getPCListByAddress(
                gu = gu,
                dong = dong,
                name = searchName.value)
                .enqueue(object : BaseRetrofitCallback<ArrayList<PCListItem>>(PCJariApplication.instance) {
                    override fun onSuccess(response: ArrayList<PCListItem>) {
                        _pcList.value = response
                    }
                })
        }

    }
}