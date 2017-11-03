package com.example.capston.pcjari.fragment;

/**
 * Created by 94tig on 2017-11-03.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capston.pcjari.R;

public class DetailedInformationFragment extends android.support.v4.app.Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("매장 상세");

        View view = inflater.inflate(R.layout.fragment_detailedinformation, container, false);


        return view;
    }
}