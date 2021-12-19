package com.example.capston.pcjari.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.example.capston.pcjari.R
import com.example.capston.pcjari.activity.a100_main.A100MainActivity
import com.example.capston.pcjari.base.BaseActivity
import com.example.capston.pcjari.databinding.A001ActivityIntroBinding

/**
 * Created by KangSeungho on 2017-11-04.
 */
class A001IntroActivity : BaseActivity<A001ActivityIntroBinding>() {
    override fun getLayoutResId() = R.layout.a001_activity_intro

    var handler = Handler()
    var r: Runnable? = Runnable {
        val intent = Intent(applicationContext, A100MainActivity::class.java)
        startActivity(intent) // 다음 화면으로 전환
        finish() // Activity화면 제거
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar?.hide()

        Glide.with(this)
            .load(R.drawable.intro)
            .into(GlideDrawableImageViewTarget(binding.introGif))
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(r, 1000)
    }

    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(r)
    }
}