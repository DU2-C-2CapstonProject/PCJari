package com.example.capston.pcjari.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.capston.pcjari.util.retrofit.RetrofitClient
import com.example.capston.pcjari.util.retrofit.RetrofitNetwork

/**
 * Created by KangSeungho on 2020-07-29.
 * Activity에서 공통으로 사용하는 메소드, 변수를 정의
 */
abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {
    companion object {
        const val TAG = "PCJR"
    }

    private var TITLE = "[${this.javaClass.simpleName}]"

    @LayoutRes
    abstract fun getLayoutResId(): Int

    private var _binding: T? = null
    protected val binding: T get() = _binding!!

    protected val networkAPI : RetrofitNetwork = RetrofitClient.getInstance()

    // region override =============================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logD("onCreate")
        _binding = DataBindingUtil.setContentView(this, getLayoutResId())
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