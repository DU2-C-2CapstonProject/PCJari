package com.example.capston.pcjari.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Created by KangSeungho on 2020-07-29.
 * Fragment에서 공통으로 사용하는 메소드, 변수를 정의
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    val TAG = BaseActivity.TAG
    val TITLE = "[${this.javaClass.simpleName}]"

    private var _binding: T? = null
    protected val binding: T get() = _binding!!

    // region override =============================

    @LayoutRes
    abstract fun getLayoutResId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logD("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        logD("onCreateView")

        return binding.root
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