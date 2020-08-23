package com.example.capston.pcjari.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.capston.pcjari.Util.Retrofit.RetrofitClient
import com.example.capston.pcjari.Util.Retrofit.RetrofitNetwork

/**
 * Created by KangSeungho on 2020-07-29.
 * Activity에서 공통으로 사용하는 메소드, 변수를 정의
 */
open class A0_BaseActivity : AppCompatActivity() {
    companion object {
        const val TAG = "PCJR"
    }

    var TITLE = "[${this.javaClass.simpleName}]"

    protected val networkAPI : RetrofitNetwork = RetrofitClient.getInstance()

    // region override =============================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logD("onCreate")
    }

    override fun onResume() {
        super.onResume()
        logD("onResume")
    }

    override fun onPause() {
        logD("onPause")
        super.onPause()
    }

    override fun onDestroy() {
        logD("onDestroy")
        super.onDestroy()
    }

    // endregion override ==========================

    // region Log ==================================

    public fun logV(msg: String) {
        Log.v(TAG, "$TITLE : $msg")
    }

    public fun logD(msg: String) {
        Log.d(TAG, "$TITLE : $msg")
    }

    public fun logI(msg: String) {
        Log.i(TAG, "$TITLE : $msg")
    }

    public fun logW(msg: String) {
        Log.w(TAG, "$TITLE : $msg")
    }

    public fun logE(msg: String) {
        Log.e(TAG, "$TITLE : $msg")
    }

    // endregion Log ===============================
}