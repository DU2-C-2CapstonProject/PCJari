package com.example.capston.pcjari.activity.a100_main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import com.example.capston.pcjari.activity.A100MainActivity
import com.example.capston.pcjari.base.BaseFragment
import com.example.capston.pcjari.R
import com.example.capston.pcjari.databinding.F140FragmentSettingBinding
import com.example.capston.pcjari.util.db.DataBaseTables.CreateDB_setting
import kotlinx.android.synthetic.main.f140_fragment_setting.view.*

/**
 * Created by KangSeungho on 2017-10-27.
 */
class F140SettingFragment : BaseFragment<F140FragmentSettingBinding>() {
    lateinit var main: A100MainActivity

    override fun getLayoutResId() = R.layout.f140_fragment_setting

    lateinit var radio_group : RadioGroup
    lateinit var gps_distance_seekbar : SeekBar
    lateinit var gps_distance : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.title = "정보"
        val view = binding.root

        main = activity as A100MainActivity

        radio_group = view.setting_first_screen_radio_group

        gps_distance = view.setting_gps_distance
        gps_distance_seekbar = view.setting_gps_distance_seekbar

        seekbarSetting()

        radio_group.setOnCheckedChangeListener(firstFragmentSetting)
        when (main.position) {
            0 -> radio_group.check(R.id.setting_first_screen_address)
            1 -> radio_group.check(R.id.setting_first_screen_gps)
            2 -> radio_group.check(R.id.setting_first_screen_favorite)
        }
        return view
    }

    @SuppressLint("SetTextI18n")
    fun seekbarSetting() {
        gps_distance_seekbar.progress = main.dist - 5

        if (main.dist >= 10) {
            gps_distance.text = "${main.dist.toDouble() / 10}km"
        } else {
            gps_distance.text = "${main.dist * 100}m"
        }

        gps_distance_seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                val dist = seekBar.progress + 5
                if (dist >= 10) {
                    gps_distance.text = "${dist.toDouble() / 10}km"
                } else {
                    gps_distance.text = "${dist * 100}m"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val dist = seekBar.progress + 5
                main.dist = dist
                val sql = ("UPDATE " + CreateDB_setting._TABLENAME
                        + " SET " + CreateDB_setting.DIST + "=" + dist + ";")
                main.db.execSQL(sql)
            }
        })
    }

    // 라디오 버튼 클릭 시 초기 화면 설정
    private var firstFragmentSetting: RadioGroup.OnCheckedChangeListener = RadioGroup.OnCheckedChangeListener { group, checkedId ->
        var i = 0
        when (checkedId) {
            R.id.setting_first_screen_address -> i = 0
            R.id.setting_first_screen_gps -> i = 1
            R.id.setting_first_screen_favorite -> i = 2
        }
        val sql = ("UPDATE " + CreateDB_setting._TABLENAME
                + " SET " + CreateDB_setting.FIRST_ACTIVITY + "=" + i + ";")
        main.db.execSQL(sql)
    }
}