package com.example.capston.pcjari.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.capston.pcjari.R
import com.example.capston.pcjari.base.BaseActivity
import com.example.capston.pcjari.databinding.A200ActivityInfoBinding
import com.example.capston.pcjari.pc.PCListItem
import com.example.capston.pcjari.util.retrofit.RetrofitClient
import kotlinx.android.synthetic.main.a200_activity_info.*

/**
 * Created by KangSeungho on 2017-11-01.
 */
class A200InfoActivity : BaseActivity<A200ActivityInfoBinding>() {
    override fun getLayoutResId() = R.layout.a200_activity_info

    lateinit var pc: PCListItem

    companion object {
        const val PCITEM = "pcitem"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pc = intent.getSerializableExtra(PCITEM) as PCListItem

        supportActionBar?.title = pc.title

        val imgUrl: String = RetrofitClient.serverUrl + "pc_images/" + pc.icon
        Glide.with(applicationContext).load(imgUrl).into(pc_info_image)

        binding.item = pc

        binding.pcInfoAddressShowMap.setOnClickListener(locationListener)
        binding.pcInfoTel.setOnClickListener(telListener)
        binding.pcInfoSeatShowBtn.setOnClickListener(seatListener)
    }

    // 주소 보기 버튼 클릭
    var locationListener: View.OnClickListener = View.OnClickListener { // 위치 확인 권한 확인
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(parent, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        } else {
            val send_intent = Intent(applicationContext, A201MapActivity::class.java)
            send_intent.putExtra(PCITEM, pc)
            startActivity(send_intent)
        }
    }

    // 전화번호 버튼 클릭
    var telListener: View.OnClickListener = View.OnClickListener {
        val tel_value: Array<String?> = pc.tel?.split("-".toRegex())!!.toTypedArray()
        var tel = "tel:"
        for (str in tel_value) tel = tel + str
        val tel_intent = Intent(Intent.ACTION_DIAL, Uri.parse(tel))
        startActivity(tel_intent)
    }
    var seatListener: View.OnClickListener? = View.OnClickListener {
        val seat_intent = Intent(application, A210SeatActivity::class.java)
        seat_intent.putExtra(PCITEM, pc)
        startActivity(seat_intent)
    }
}