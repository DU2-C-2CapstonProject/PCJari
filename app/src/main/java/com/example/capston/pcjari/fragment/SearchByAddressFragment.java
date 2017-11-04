package com.example.capston.pcjari.fragment;

/**
 * Created by 94tig on 2017-10-27.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.capston.pcjari.DetailedInformationActivity;
import com.example.capston.pcjari.MainActivity;
import com.example.capston.pcjari.PCListAdapter;
import com.example.capston.pcjari.PCListItem;
import com.example.capston.pcjari.R;
import com.example.capston.pcjari.StaticData;

import java.util.ArrayList;

public class SearchByAddressFragment extends Fragment{
    private ListView pcListView;
    private PCListAdapter pcListAdapter;
    private PCListItem pcItem[] = StaticData.pcItems;
    private Button selectButton;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("주소로 찾기");

        View view = inflater.inflate(R.layout.fragment_searchbyaddress, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        pcListView = (ListView)view.findViewById(R.id.listview1);
        selectButton = (Button)view.findViewById(R.id.button_search);
        selectButton.setOnClickListener(selectListener);

        pcListAdapter = new PCListAdapter();
        dataSetting();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int random[] = new int[7];
                for(int i=0; i<7; i++) {
                    int min=10;
                    int max=0;
                    int space = pcListAdapter.getItem(i).getSpaceSeat();
                    if(space>=0 && space<10)
                        max=0;
                    else if(space>=10 && space<20)
                        max=10;
                    else if(space>=20 && space<30)
                        max=20;
                    else if(space>=30 && space<40)
                        max=30;
                    else
                        max=1;
                    random[i] = (int) (Math.random() * min) + max;

                    pcListAdapter.getItem(i).setSpaceSeat(random[i]);
                    pcListAdapter.getItem(i).setUsingSeat(pcListAdapter.getItem(i).getTotalSeat()-random[i]);
                }

                pcListView.setAdapter(pcListAdapter);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        pcListView.setOnItemClickListener(ListshortListener);
        pcListView.setOnItemLongClickListener(ListlongListener);

        return view;
    }

    View.OnClickListener selectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String x = String.valueOf(StaticData.GPS_X);
            String y = String.valueOf(StaticData.GPS_Y);
            Toast.makeText(getContext(), "위도 : " + x + ", 경도 : " + y, Toast.LENGTH_SHORT).show();
        }
    };

    AdapterView.OnItemClickListener ListshortListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {          //리스트 아이템 클릭했을 때 나오는 이벤트
            Intent intent = new Intent(getContext(), DetailedInformationActivity.class);
            intent.putExtra(DetailedInformationActivity.POSITION, position);
            startActivity(intent);

            /*
            Bundle args = new Bundle();
            args.putSerializable("PCItem", pc);
            Fragment detailedInformationFragment = new DetailedInformationFragment();
            detailedInformationFragment.setArguments(args);
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, detailedInformationFragment, "PCItemTag")
                    .addToBackStack("PCItemTag").commit();
            */
        }
    };

    AdapterView.OnItemLongClickListener ListlongListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {       //리스트 아이템 꾹 눌렀을 때 나오는 이벤트
            PCListItem pc = pcListAdapter.getItem(position);

            pc.setFavorite(!pc.isFavorite());
            if(pc.isFavorite())
                Toast.makeText(getContext(), "즐겨찾기에 추가 되었습니다.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getContext(), "즐겨찾기에서 삭제 되었습니다.", Toast.LENGTH_SHORT).show();

            pcListView.setAdapter(pcListAdapter);

            return true;
        }
    };

    private void dataSetting(){
        for(PCListItem pc : pcItem)
            pcListAdapter.addItem(pc);

        pcListView.setAdapter(pcListAdapter);
    }
}
