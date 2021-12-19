package com.example.capston.pcjari.util.retrofit

import android.content.Context
import android.util.Log
import com.example.capston.pcjari.base.BaseActivity
import com.example.capston.pcjari.util.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseRetrofitCallback<T>(private val mContext: Context) : Callback<T> {
    abstract fun onSuccess(response: T)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        when(response.isSuccessful) {
            true -> {
                val data = response.body()

                if(data != null)
                    onSuccess(data)
                else
                    mContext.showToast("정보가 없습니다.")
            }
            false -> {
                Log.e(BaseActivity.TAG, "response Code : ${response.code()}, errorBody : ${response.errorBody()?.string()}")
            }
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.e(BaseActivity.TAG, "retrofit error\n" + t.stackTraceToString())
    }
}