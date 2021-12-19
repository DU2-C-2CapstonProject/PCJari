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
import com.example.capston.pcjari.util.Preferences

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

            when (Preferences.first_screen_index) {
                0 -> check(R.id.setting_first_screen_address)
                1 -> check(R.id.setting_first_screen_gps)
                2 -> check(R.id.setting_first_screen_favorite)
            }
        }

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    fun seekbarSetting() {
        binding.settingGpsDistanceSeekbar.progress = Preferences.gps_distance - 5

        if (Preferences.gps_distance >= 10) {
            binding.settingGpsDistance.text = "${Preferences.gps_distance.toDouble() / 10}km"
        } else {
            binding.settingGpsDistance.text = "${Preferences.gps_distance * 100}m"
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
                Preferences.gps_distance = seekBar.progress + 5
            }
        })
    }

    // 라디오 버튼 클릭 시 초기 화면 설정
    private var firstFragmentSetting: RadioGroup.OnCheckedChangeListener = RadioGroup.OnCheckedChangeListener { group, checkedId ->
        when (checkedId) {
            R.id.setting_first_screen_address -> Preferences.first_screen_index = 0
            R.id.setting_first_screen_gps -> Preferences.first_screen_index = 1
            R.id.setting_first_screen_favorite -> Preferences.first_screen_index = 2
        }
    }
}