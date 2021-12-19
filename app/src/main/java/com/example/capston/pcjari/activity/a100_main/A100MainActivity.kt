package com.example.capston.pcjari.activity.a100_main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.capston.pcjari.R
import com.example.capston.pcjari.base.BaseActivity
import com.example.capston.pcjari.databinding.A100ActivityMainBinding
import com.example.capston.pcjari.util.Preferences

/**
 * Created by KangSeungho on 2017-09-25.
 */
class A100MainActivity : BaseActivity<A100ActivityMainBinding>() {
    override fun getLayoutResId() = R.layout.a100_activity_main

    var controller: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 네비게이션 처리
        controller = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)?.findNavController()
        controller?.run {
            binding.navigation.setupWithNavController(this)
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf<String?>(Manifest.permission.ACCESS_FINE_LOCATION), 0)

        initUi()
    }

    private fun initUi() {
        // 첫 페이지 설정
        controller?.run {
            val navGraph = navInflater.inflate(R.navigation.main_navigation)
            navGraph.startDestination = when(Preferences.first_screen_index) {
                0 -> R.id.navigation_search_by_address
                1 -> R.id.navigation_search_by_me
                2 -> R.id.navigation_favorite
                else -> R.id.navigation_search_by_address
            }
            graph = navGraph
        }
    }
}