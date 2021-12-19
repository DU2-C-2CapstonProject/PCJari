package com.example.capston.pcjari.activity.a100_main

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.example.capston.pcjari.R
import com.example.capston.pcjari.activity.A100MainActivity
import com.example.capston.pcjari.base.BaseFragment
import com.example.capston.pcjari.databinding.F140FragmentSettingBinding
import com.example.capston.pcjari.util.db.DataBaseTables.CreateDB_setting

/**
 * Created by KangSeungho on 2017-10-27.
 */
class F140SettingFragment : BaseFragment<F140FragmentSettingBinding>() {
    lateinit var main: A100MainActivity

    override fun getLayoutResId() = R.layout.f140_fragment_setting

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        activity?.title = getString(R.string.title_information)

        main = activity as A100MainActivity

        seekbarSetting()

        binding.settingFirstScreenRadioGroup.apply {
            setOnCheckedChangeListener(firstFragmentSetting)

            when (main.position) {
                0 -> check(R.id.setting_first_screen_address)
                1 -> check(R.id.setting_first_screen_gps)
                2 -> check(R.id.setting_first_screen_favorite)
            }
        }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    fun seekbarSetting() {
        binding.settingGpsDistanceSeekbar.progress = main.dist - 5

        if (main.dist >= 10) {
            binding.settingGpsDistance.text = "${main.dist.toDouble() / 10}km"
        } else {
            binding.settingGpsDistance.text = "${main.dist * 100}m"
        }

        binding.settingGpsDistanceSeekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                val dist = seekBar.progress + 5
                if (dist >= 10) {
                    binding.settingGpsDistance.text = "${dist.toDouble() / 10}km"
                } else {
                    binding.settingGpsDistance.text = "${dist * 100}m"
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