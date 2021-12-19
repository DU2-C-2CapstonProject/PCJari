package com.example.capston.pcjari.activity.a100_main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.capston.pcjari.PCJariApplication
import com.example.capston.pcjari.pc.PCListItem
import com.example.capston.pcjari.util.Preferences
import com.example.capston.pcjari.util.retrofit.BaseRetrofitCallback
import com.example.capston.pcjari.util.retrofit.RetrofitClient

class F130MainFavoriteFragmentViewModel : ViewModel() {
    private val retroRepos = RetrofitClient.getInstance()

    private val _pcList = MutableLiveData<ArrayList<PCListItem>>()
    val pcList: LiveData<ArrayList<PCListItem>> get() = _pcList

    val searchName = MutableLiveData<String>()

    fun getPCList() {
        retroRepos.getPCListByFavorite(
            favorite = Preferences.favorite_list.joinToString(),
            name = searchName.value)
            .enqueue(object : BaseRetrofitCallback<ArrayList<PCListItem>>(PCJariApplication.instance) {
                override fun onSuccess(response: ArrayList<PCListItem>) {
                    _pcList.value = response
                }
            })
    }
}