package com.example.capston.pcjari.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capston.pcjari.Activity.A0_BaseActivity

/**
 * Created by KangSeungho on 2020-07-29.
 * Fragment에서 공통으로 사용하는 메소드, 변수를 정의
 */
open class F0_BaseFragment : Fragment() {
    val TAG = A0_BaseActivity.TAG
    var TITLE = "[${this.javaClass.simpleName}]"

    // region override =============================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logD("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        logD("onCreateView")

        return view
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