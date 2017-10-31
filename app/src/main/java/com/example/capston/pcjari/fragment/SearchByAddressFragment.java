package com.example.capston.pcjari.fragment;

/**
 * Created by 94tig on 2017-10-27.
 */

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.capston.pcjari.MainActivity;
import com.example.capston.pcjari.PCListAdapter;
import com.example.capston.pcjari.PCListItem;
import com.example.capston.pcjari.R;

public class SearchByAddressFragment extends Fragment {
    private ListView pcListView;
    private PCListAdapter pcListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("주소로 찾기");

        View view = inflater.inflate(R.layout.fragment_searchbyaddress, container, false);
        pcListView = (ListView)view.findViewById(R.id.listview1);
        pcListAdapter = new PCListAdapter();
        dataSetting();

        pcListView.setOnItemClickListener(shortListener);
        pcListView.setOnItemLongClickListener(longListener);

        return view;
    }

    AdapterView.OnItemClickListener shortListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            PCListItem pc = pcListAdapter.getItem(position);
            Toast.makeText(getContext(), pc.getTitle(), Toast.LENGTH_SHORT).show();
        }
    };

    AdapterView.OnItemLongClickListener longListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            PCListItem pc = pcListAdapter.getItem(position);
            Toast.makeText(getContext(), String.valueOf(pc.getTotalSeat()), Toast.LENGTH_SHORT).show();

            return true;
        }
    };

    private void dataSetting(){
        PCListItem pcItem[] = new PCListItem[7];

        pcItem[0] = new PCListItem();
        pcItem[0].setIcon(ContextCompat.getDrawable(getContext(), R.drawable.po1));
        pcItem[0].setTitle("바닐라PC방");
        pcItem[0].setAddress("경기도 성남시 수정구");
        pcItem[0].setPrice(1000);
        pcItem[0].setCard(true);
        pcItem[0].setFavorite(false);
        pcItem[0].setSpaceSeat(12);
        pcItem[0].setUsingSeat(50);
        pcItem[0].setTotalSeat(62);

        pcItem[1] = new PCListItem();
        pcItem[1].setIcon(ContextCompat.getDrawable(getContext(), R.drawable.po2));
        pcItem[1].setTitle("더캠프 PC방 동서울대점");
        pcItem[1].setAddress("경기도 성남시 수정구");
        pcItem[1].setPrice(1000);
        pcItem[1].setCard(false);
        pcItem[1].setFavorite(true);
        pcItem[1].setSpaceSeat(8);
        pcItem[1].setUsingSeat(72);
        pcItem[1].setTotalSeat(80);

        pcItem[2] = new PCListItem();
        pcItem[2].setIcon(ContextCompat.getDrawable(getContext(), R.drawable.po3));
        pcItem[2].setTitle("허브PC방");
        pcItem[2].setAddress("경기도 성남시 수정구");
        pcItem[2].setPrice(1200);
        pcItem[2].setCard(true);
        pcItem[2].setFavorite(false);
        pcItem[2].setSpaceSeat(2);
        pcItem[2].setUsingSeat(38);
        pcItem[2].setTotalSeat(40);

        pcItem[3] = new PCListItem();
        pcItem[3].setIcon(ContextCompat.getDrawable(getContext(), R.drawable.po4));
        pcItem[3].setTitle("당근PC방");
        pcItem[3].setAddress("경기도 성남시 수정구");
        pcItem[3].setPrice(1000);
        pcItem[3].setCard(true);
        pcItem[3].setFavorite(false);
        pcItem[3].setSpaceSeat(5);
        pcItem[3].setUsingSeat(55);
        pcItem[3].setTotalSeat(60);

        pcItem[4] = new PCListItem();
        pcItem[4].setIcon(ContextCompat.getDrawable(getContext(), R.drawable.po5));
        pcItem[4].setTitle("쓰리팝PC까페");
        pcItem[4].setAddress("경기도 성남시 수정구");
        pcItem[4].setPrice(1000);
        pcItem[4].setCard(true);
        pcItem[4].setFavorite(false);
        pcItem[4].setSpaceSeat(38);
        pcItem[4].setUsingSeat(12);
        pcItem[4].setTotalSeat(50);

        pcItem[5] = new PCListItem();
        pcItem[5].setIcon(ContextCompat.getDrawable(getContext(), R.drawable.po6));
        pcItem[5].setTitle("이네이처PC방태평점");
        pcItem[5].setAddress("경기도 성남시 수정구");
        pcItem[5].setPrice(1000);
        pcItem[5].setCard(true);
        pcItem[5].setFavorite(false);
        pcItem[5].setSpaceSeat(26);
        pcItem[5].setUsingSeat(22);
        pcItem[5].setTotalSeat(48);

        pcItem[6] = new PCListItem();
        pcItem[6].setIcon(ContextCompat.getDrawable(getContext(), R.drawable.po7));
        pcItem[6].setTitle("라이온PC방 위례본점");
        pcItem[6].setAddress("경기도 성남시 수정구");
        pcItem[6].setPrice(1000);
        pcItem[6].setCard(true);
        pcItem[6].setFavorite(false);
        pcItem[6].setSpaceSeat(4);
        pcItem[6].setUsingSeat(36);
        pcItem[6].setTotalSeat(40);

        for(PCListItem pc : pcItem)
            pcListAdapter.addItem(pc);

        pcListView.setAdapter(pcListAdapter);
    }
}
