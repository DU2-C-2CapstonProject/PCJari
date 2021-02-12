package com.example.capston.pcjari.Activity

import android.Manifest
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.capston.pcjari.Base.BaseActivity
import com.example.capston.pcjari.PC.PCListItem
import com.example.capston.pcjari.Activity.A100_Main.F140_SettingFragment
import com.example.capston.pcjari.Activity.A100_Main.F110_MainAddressFragment
import com.example.capston.pcjari.Activity.A100_Main.F130_MainFavoriteFragment
import com.example.capston.pcjari.Activity.A100_Main.F120_MainGpsFragment
import com.example.capston.pcjari.Util.DB.DataBaseHelper
import com.example.capston.pcjari.Util.DB.DataBaseTables.CreateDB_favorite
import com.example.capston.pcjari.Util.DB.DataBaseTables.CreateDB_setting
import com.example.capston.pcjari.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.a100_activity_main.*
import java.util.*

/**
 * Created by KangSeungho on 2017-09-25.
 */
class A100_MainActivity : BaseActivity() {

    lateinit var favorite: ArrayList<Int>
    var position = 0
    var dist = 0

    lateinit var db: SQLiteDatabase

    lateinit var pc: PCListItem

    lateinit var DBHelper: DataBaseHelper
    private val mOnNavigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener? = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_search_by_address -> {
                supportFragmentManager.beginTransaction().replace(R.id.location_listview, F110_MainAddressFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search_by_me -> {
                supportFragmentManager.beginTransaction().replace(R.id.location_listview, F120_MainGpsFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                supportFragmentManager.beginTransaction().replace(R.id.location_listview, F130_MainFavoriteFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_information -> {
                supportFragmentManager.beginTransaction().replace(R.id.location_listview, F140_SettingFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a100_activity_main)
        supportFragmentManager.beginTransaction().add(R.id.location_listview, F110_MainAddressFragment()).commit()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
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

        val prev = navigation.menu.getItem(position)
        prev.isChecked = true

        when (position) {
            0 -> supportFragmentManager.beginTransaction().replace(R.id.location_listview, F110_MainAddressFragment()).commit()
            1 -> supportFragmentManager.beginTransaction().replace(R.id.location_listview, F120_MainGpsFragment()).commit()
            2 -> supportFragmentManager.beginTransaction().replace(R.id.location_listview, F130_MainFavoriteFragment()).commit()
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