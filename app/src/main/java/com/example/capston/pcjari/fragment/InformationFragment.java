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

import com.example.capston.pcjari.MainActivity;
import com.example.capston.pcjari.R;
import com.example.capston.pcjari.sqlite.DataBaseHelper;
import com.example.capston.pcjari.sqlite.DataBaseTables;

public class InformationFragment extends android.support.v4.app.Fragment {
    SQLiteDatabase db = MainActivity.db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("정보");
        View view = inflater.inflate(R.layout.fragment_information, container, false);

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