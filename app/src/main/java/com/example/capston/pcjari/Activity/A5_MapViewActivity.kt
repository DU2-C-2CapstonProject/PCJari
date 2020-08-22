package com.example.capston.pcjari.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.capston.pcjari.Activity.A3_InformationActivity.Companion.PCITEM
import com.example.capston.pcjari.PC.PCListItem
import com.example.capston.pcjari.R
import com.example.capston.pcjari.Util.GPSTracker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * Created by KangSeungho on 2017-11-04.
 */
class A5_MapViewActivity : A0_BaseActivity(), OnMapReadyCallback {
    var mMap: GoogleMap? = null
    lateinit var pc: PCListItem
    lateinit var gps: GPSTracker
    var check = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a5_activity_map_view)

        supportActionBar?.hide()

        pc = intent.getSerializableExtra(PCITEM) as PCListItem
        gps = GPSTracker(applicationContext)

        // gps 받아올 수 있는 환경인지 Check~
        if (gps.canGetLocation) {
            gps.Update()

            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        } else {
            // GPS OFF 일때 Dialog 표시
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("위치 서비스 설정")
            alertDialog.setMessage("무선 네트워크, GPS 중 한 가지 이상 체크하셔야 정확한 위치 서비스가 가능합니다.\n위치 서비스 기능을 설정하시겠습니까?")

            // Dialog OK 버튼 (GPS 설정 화면으로 이동)
            alertDialog.setPositiveButton("OK") { dialog, which ->
                check++
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                intent.addCategory(Intent.CATEGORY_DEFAULT)
                startActivity(intent)
            }

            // Dialog No 버튼
            alertDialog.setNegativeButton("NO") { dialog, which -> finish() }

            // Dialog 보여주기
            alertDialog.create().show()
        }
    }

    // 맵에 마커 그리기
    override fun onMapReady(map: GoogleMap) {
        val PC_location = LatLng(pc.location_x, pc.location_y)
        val markerOptions = MarkerOptions()
        markerOptions
                .position(PC_location)
                .title(pc.title)
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true)
            map.getUiSettings().isMyLocationButtonEnabled = true
        }
        map.addMarker(markerOptions)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(PC_location, 16f))
    }

    // 다른 Activity 갔다 올 때 다시 시작하는 메소드
    override fun onResume() {
        super.onResume()
        if (check >= 1) {
            gps = GPSTracker(applicationContext)
            if (gps.canGetLocation) {
                gps.Update()
                val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this)
            } else {
                Toast.makeText(applicationContext, "위치 기능을 키지 않을 경우\n원활한 자료 제공이 되지 않습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }
}