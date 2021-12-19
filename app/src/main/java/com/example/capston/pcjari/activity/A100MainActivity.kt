package com.example.capston.pcjari.activity

import android.Manifest
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.capston.pcjari.R
import com.example.capston.pcjari.base.BaseActivity
import com.example.capston.pcjari.databinding.A100ActivityMainBinding
import com.example.capston.pcjari.pc.PCListItem
import com.example.capston.pcjari.util.db.DataBaseHelper
import com.example.capston.pcjari.util.db.DataBaseTables.CreateDB_favorite
import com.example.capston.pcjari.util.db.DataBaseTables.CreateDB_setting
import java.util.*

/**
 * Created by KangSeungho on 2017-09-25.
 */
class A100MainActivity : BaseActivity<A100ActivityMainBinding>() {
    override fun getLayoutResId() = R.layout.a100_activity_main

    var controller: NavController? = null

    lateinit var favorite: ArrayList<Int>
    var position = 0
    var dist = 0

    lateinit var db: SQLiteDatabase

    lateinit var pc: PCListItem

    lateinit var DBHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 네비게이션 처리
        controller = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main)?.findNavController()
        controller?.run {
            binding.navigation.setupWithNavController(this)
        }

        DBHelper = DataBaseHelper(applicationContext)

        db = DBHelper.writableDatabase
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf<String?>(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }
        favorite = ArrayList()
        firstSetting()
    }

    private fun firstSetting() {
        favorite_setting()
        //프레그먼트 셋팅은 항상 마지막에
        fragment_setting()
    }

    private fun fragment_setting() {
        val sql = "SELECT * FROM " + CreateDB_setting._TABLENAME + ";"
        val results = db?.rawQuery(sql, null)

        if(results == null)
            return

        results.moveToFirst()
        position = results.getInt(1)
        dist = results.getInt(2)
        results.close()

        controller?.run {
            val navGraph = navInflater.inflate(R.navigation.main_navigation)
            navGraph.startDestination = when(position) {
                0 -> R.id.navigation_search_by_address
                1 -> R.id.navigation_search_by_me
                2 -> R.id.navigation_favorite
                else -> R.id.navigation_search_by_address
            }
            graph = navGraph
        }
    }

    private fun favorite_setting() {
        val sql = "SELECT * FROM " + CreateDB_favorite._TABLENAME + ";"
        val results = db?.rawQuery(sql, null)

        if(results == null)
            return

        results.moveToFirst()
        while (!results.isAfterLast) {
            val tmp = results.getString(0)
            favorite.add(Integer.valueOf(tmp))
            results.moveToNext()
        }
        results.close()
    }
}