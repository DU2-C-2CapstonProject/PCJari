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
        pcItem[0] = new PCListItem();
        pcItem[0].setIcon(ContextCompat.getDrawable(getContext(), R.drawable.po1));
        pcItem[0].setTitle("바닐라PC방");
        pcItem[0].setNotice("12월 1일 전 좌석 그래픽카드 GTX 1080으로 업그레이드");
        pcItem[0].setAddress("경기도 성남시 수정구");
        pcItem[0].setPrice(1000);
        pcItem[0].setCard(true);
        pcItem[0].setFavorite(false);
        pcItem[0].setSpaceSeat(12);
        pcItem[0].setUsingSeat(50);
        pcItem[0].setTotalSeat(62);
        pcItem[0].setLocation_x(37.454853);
        pcItem[0].setLocation_y(127.127196);
        pcItem[0].setCpu("i7-7770K");
        pcItem[0].setRam("16GB");
        pcItem[0].setVga("GTX-1070");
        pcItem[0].setTel("031-758-1234");

        pcItem[1] = new PCListItem();
        pcItem[1].setIcon(ContextCompat.getDrawable(getContext(), R.drawable.po2));
        pcItem[1].setTitle("더캠프 PC방 동서울대점");
        pcItem[1].setNotice("더캠프 공지");
        pcItem[1].setAddress("경기도 성남시 수정구");
        pcItem[1].setPrice(1000);
        pcItem[1].setCard(false);
        pcItem[1].setFavorite(true);
        pcItem[1].setSpaceSeat(8);
        pcItem[1].setUsingSeat(72);
        pcItem[1].setTotalSeat(80);
        pcItem[1].setLocation_x(37.459219);
        pcItem[1].setLocation_y(127.126464);
        pcItem[1].setCpu("i7-7770");
        pcItem[1].setRam("8GB");
        pcItem[1].setVga("GTX-1060 3GB");
        pcItem[1].setTel("031-758-1235");

        pcItem[2] = new PCListItem();
        pcItem[2].setIcon(ContextCompat.getDrawable(getContext(), R.drawable.po3));
        pcItem[2].setTitle("갤러리PC방");
        pcItem[2].setNotice("갤러리 공지");
        pcItem[2].setAddress("경기도 성남시 수정구");
        pcItem[2].setPrice(1000);
        pcItem[2].setCard(true);
        pcItem[2].setFavorite(false);
        pcItem[2].setSpaceSeat(2);
        pcItem[2].setUsingSeat(38);
        pcItem[2].setTotalSeat(40);
        pcItem[2].setLocation_x(37.457661);
        pcItem[2].setLocation_y(127.126680);
        pcItem[2].setCpu("i7-7770K");
        pcItem[2].setRam("16GB");
        pcItem[2].setVga("GTX-1070Ti");
        pcItem[2].setTel("031-758-1236");

        pcItem[3] = new PCListItem();
        pcItem[3].setIcon(ContextCompat.getDrawable(getContext(), R.drawable.po4));
        pcItem[3].setTitle("당근PC방");
        pcItem[3].setNotice("당근 공지");
        pcItem[3].setAddress("경기도 성남시 수정구");
        pcItem[3].setPrice(1000);
        pcItem[3].setCard(true);
        pcItem[3].setFavorite(false);
        pcItem[3].setSpaceSeat(5);
        pcItem[3].setUsingSeat(55);
        pcItem[3].setTotalSeat(60);
        pcItem[3].setLocation_x(37.454598);
        pcItem[3].setLocation_y(127.127243);
        pcItem[3].setCpu("i7-7770K");
        pcItem[3].setRam("16GB");
        pcItem[3].setVga("GTX-1060 8GB");
        pcItem[3].setTel("031-758-1237");

        pcItem[4] = new PCListItem();
        pcItem[4].setIcon(ContextCompat.getDrawable(getContext(), R.drawable.po5));
        pcItem[4].setTitle("쓰리팝PC까페");
        pcItem[4].setNotice("쓰리팝 공지");
        pcItem[4].setAddress("경기도 성남시 수정구");
        pcItem[4].setPrice(1000);
        pcItem[4].setCard(true);
        pcItem[4].setFavorite(false);
        pcItem[4].setSpaceSeat(38);
        pcItem[4].setUsingSeat(12);
        pcItem[4].setTotalSeat(50);
        pcItem[4].setLocation_x(37.463818);
        pcItem[4].setLocation_y(127.140416);
        pcItem[4].setCpu("i7-7770K");
        pcItem[4].setRam("16GB");
        pcItem[4].setVga("GTX-1080");
        pcItem[4].setTel("031-758-1238");

        pcItem[5] = new PCListItem();
        pcItem[5].setIcon(ContextCompat.getDrawable(getContext(), R.drawable.po6));
        pcItem[5].setTitle("허브 PC방");
        pcItem[5].setNotice("허브 공지");
        pcItem[5].setAddress("경기도 성남시 수정구");
        pcItem[5].setPrice(1000);
        pcItem[5].setCard(true);
        pcItem[5].setFavorite(false);
        pcItem[5].setSpaceSeat(26);
        pcItem[5].setUsingSeat(22);
        pcItem[5].setTotalSeat(48);
        pcItem[5].setLocation_x(37.454288);
        pcItem[5].setLocation_y(127.127206);
        pcItem[5].setCpu("i7-7770K");
        pcItem[5].setRam("16GB");
        pcItem[5].setVga("GTX-1080");
        pcItem[5].setTel("031-758-1239");

        pcItem[6] = new PCListItem();
        pcItem[6].setIcon(ContextCompat.getDrawable(getContext(), R.drawable.po7));
        pcItem[6].setTitle("라이온PC방 위례본점");
        pcItem[6].setNotice("라이온 공지");
        pcItem[6].setAddress("경기도 성남시 수정구");
        pcItem[6].setPrice(1000);
        pcItem[6].setCard(true);
        pcItem[6].setFavorite(false);
        pcItem[6].setSpaceSeat(4);
        pcItem[6].setUsingSeat(36);
        pcItem[6].setTotalSeat(40);
        pcItem[6].setLocation_x(37.472175);
        pcItem[6].setLocation_y(127.143076);
        pcItem[6].setCpu("i7-7770K");
        pcItem[6].setRam("16GB");
        pcItem[6].setVga("GTX-1070");
        pcItem[6].setTel("031-758-1240");

        for(PCListItem pc : pcItem)
            pcListAdapter.addItem(pc);

        pcListView.setAdapter(pcListAdapter);
    }
}
