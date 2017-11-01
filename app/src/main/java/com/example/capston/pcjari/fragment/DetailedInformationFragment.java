package com.example.capston.pcjari.fragment;

/**
 * Created by 94tig on 2017-10-27.
 */

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capston.pcjari.PCListItem;
import com.example.capston.pcjari.R;

public class DetailedInformationFragment extends Fragment {
    PCListItem pc;
    TextView notice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("매장 상세");

        View view = inflater.inflate(R.layout.fragment_detailedinformation, container, false);

        Bundle args = getArguments();
        pc = (PCListItem) args.getSerializable("PCItem");

        notice = (TextView) view.findViewById(R.id.di_notice);
        notice.setText(pc.getNotice());

        return view;
    }
}