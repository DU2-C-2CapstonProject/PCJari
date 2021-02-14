package com.example.capston.pcjari.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget
import com.example.capston.pcjari.base.BaseActivity
import com.example.capston.pcjari.R

/**
 * Created by KangSeungho on 2017-11-04.
 */
class A001IntroActivity : BaseActivity() {
    var handler: Handler? = Handler()
    var r: Runnable? = Runnable {
        val intent = Intent(applicationContext, A100MainActivity::class.java)
        startActivity(intent) // 다음 화면으로 전환
        finish() // Activity화면 제거
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a001_activity_intro)
        val actionBar = supportActionBar
        actionBar?.hide()
        val intro = findViewById<View?>(R.id.intro_gif) as ImageView
        val gifImage = GlideDrawableImageViewTarget(intro)
        Glide.with(this).load(R.drawable.intro).into(gifImage)
    }

    override fun onResume() {
        super.onResume()
        handler?.postDelayed(r, 1000)
    }

    override fun onPause() {
        super.onPause()
        handler?.removeCallbacks(r)
    }
}