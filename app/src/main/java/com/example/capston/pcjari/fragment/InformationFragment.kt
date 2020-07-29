package com.example.capston.pcjari.fragment

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import com.example.capston.pcjari.MainActivity
import com.example.capston.pcjari.R
import com.example.capston.pcjari.sqlite.DataBaseTables.CreateDB_setting
import kotlinx.android.synthetic.main.fragment_information.view.*

/**
 * Created by KangSeungho on 2017-10-27.
 */
class InformationFragment : BaseFragment() {
    var db: SQLiteDatabase? = MainActivity.db

    lateinit var radioGroup : RadioGroup
    lateinit var seekBar_SettingsGPS : SeekBar
    lateinit var textView_SettingsGPS : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.title = "정보"

        val view = inflater.inflate(R.layout.fragment_information, container, false)

        radioGroup = view.radioGroup
        seekBar_SettingsGPS = view.seekBar_SettingsGPS
        textView_SettingsGPS = view.textView_SettingsGPS

        seekbarSetting()

        radioGroup.setOnCheckedChangeListener(firstFragmentSetting)
        when (MainActivity.position) {
            0 -> radioGroup.check(R.id.radioButton_FP01)
            1 -> radioGroup.check(R.id.radioButton_FP02)
            2 -> radioGroup.check(R.id.radioButton_FP03)
        }
        return view
    }

    @SuppressLint("SetTextI18n")
    fun seekbarSetting() {
        seekBar_SettingsGPS.progress = MainActivity.dist - 5

        if (MainActivity.dist >= 10) {
            textView_SettingsGPS.text = "${MainActivity.dist.toDouble() / 10}km"
        } else {
            textView_SettingsGPS.text = "${MainActivity.dist * 100}m"
        }

        seekBar_SettingsGPS.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                val dist = seekBar.progress + 5
                if (dist >= 10) {
                    textView_SettingsGPS.text = "${dist.toDouble() / 10}km"
                } else {
                    textView_SettingsGPS.text = "${dist * 100}m"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val dist = seekBar.progress + 5
                MainActivity.dist = dist
                val sql = ("UPDATE " + CreateDB_setting._TABLENAME
                        + " SET " + CreateDB_setting.DIST + "=" + dist + ";")
                db?.execSQL(sql)
            }
        })
    }

    // 라디오 버튼 클릭 시 초기 화면 설정
    private var firstFragmentSetting: RadioGroup.OnCheckedChangeListener = RadioGroup.OnCheckedChangeListener { group, checkedId ->
        var i = 0
        when (checkedId) {
            R.id.radioButton_FP01 -> i = 0
            R.id.radioButton_FP02 -> i = 1
            R.id.radioButton_FP03 -> i = 2
        }
        val sql = ("UPDATE " + CreateDB_setting._TABLENAME
                + " SET " + CreateDB_setting.FIRST_ACTIVITY + "=" + i + ";")
        db?.execSQL(sql)
    }
}