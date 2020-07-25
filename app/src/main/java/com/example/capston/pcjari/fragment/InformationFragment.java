package com.example.capston.pcjari.fragment;

/**
 * Created by KangSeungho on 2017-10-27.
 */

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.capston.pcjari.MainActivity;
import com.example.capston.pcjari.R;
import com.example.capston.pcjari.sqlite.DataBaseTables;

import androidx.fragment.app.Fragment;

import static com.example.capston.pcjari.MainActivity.dist;

public class InformationFragment extends Fragment {
    SQLiteDatabase db = MainActivity.db;
    SeekBar seekbar;
    TextView textView_SettingsGPS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("정보");
        View view = inflater.inflate(R.layout.fragment_information, container, false);

        seekbarSetting(view);

        final RadioGroup rg = (RadioGroup) view.findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(firstFragmentSetting);

        // 라디오 버튼 Default 값 설정
        switch (MainActivity.position) {
            case 0:
                rg.check(R.id.radioButton_FP01);
                break;
            case 1:
                rg.check(R.id.radioButton_FP02);
                break;
            case 2:
                rg.check(R.id.radioButton_FP03);
                break;
        }

        return view;
    }

    void seekbarSetting(View view) {
        seekbar = (SeekBar) view.findViewById(R.id.seekBar_SettingsGPS);
        textView_SettingsGPS = (TextView) view.findViewById(R.id.textView_SettingsGPS);

        seekbar.setProgress(dist-5);
        if(dist >= 10) {
            textView_SettingsGPS.setText(((double) dist/10) + "km");
        }
        else {
            textView_SettingsGPS.setText((dist*100) + "m");
        }
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                int dist = seekBar.getProgress()+5;
                if(dist >= 10) {
                    textView_SettingsGPS.setText(((double) dist/10) + "km");
                }
                else {
                    textView_SettingsGPS.setText((dist*100) + "m");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int dist = seekBar.getProgress()+5;
                MainActivity.dist = dist;

                String sql = "UPDATE " + DataBaseTables.CreateDB_setting._TABLENAME
                        + " SET " + DataBaseTables.CreateDB_setting.DIST + "=" + dist + ";";
                db.execSQL(sql);
            }
        });
    }

    // 라디오 버튼 클릭 시 초기 화면 설정
    RadioGroup.OnCheckedChangeListener firstFragmentSetting = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int i=0;
            switch(checkedId) {
                case R.id.radioButton_FP01:
                    i=0;
                    break;
                case R.id.radioButton_FP02:
                    i=1;
                    break;
                case R.id.radioButton_FP03:
                    i=2;
                    break;
            }

            String sql = "UPDATE " + DataBaseTables.CreateDB_setting._TABLENAME
                    + " SET " + DataBaseTables.CreateDB_setting.FIRST_ACTIVITY + "=" + i + ";";
            db.execSQL(sql);
        }
    };
}