package com.example.capston.pcjari.fragment;

/**
 * Created by KangSeungho on 2017-10-27.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.capston.pcjari.PCListAdapter;
import com.example.capston.pcjari.R;

public class SearchByMeFragment extends Fragment {
    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("내 주변");

        View view = inflater.inflate(R.layout.fragment_searchbyme, container, false);
        mListView = (ListView)view.findViewById(R.id.listview1);
        dataSetting();

        return view;
    }

    private void dataSetting(){
        PCListAdapter mMyAdapter = new PCListAdapter();

        /*
        mMyAdapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.po1), "바닐라PC방",  "경기도 성남시 수정구");
        mMyAdapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.po2), "더캠프 PC방 동서울대점" , "경기 성남시 수정구");
        mMyAdapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.po3), "허브PC방" , "경기 성남시 수정구");
        mMyAdapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.po4), "당근PC방" , "경기 성남시 수정구");
        mMyAdapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.po5), "쓰리팝PC까페" , "경기도 성남시 수정구");
        mMyAdapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.po6), "이네이처PC방태평점" , "경기도 성남시 수정구");
        mMyAdapter.addItem(ContextCompat.getDrawable(getContext(), R.drawable.po7), "라이온PC방 위례본점" , "경기도 성남시 수정구");
        */

        mListView.setAdapter(mMyAdapter);
    }
}