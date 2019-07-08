package com.example.capston.pcjari.fragment;

/**
 * Created by KangSeungho on 2017-10-27.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capston.pcjari.DetailedInformationActivity;
import com.example.capston.pcjari.GettingPHP;
import com.example.capston.pcjari.MainActivity;
import com.example.capston.pcjari.PC.PCListAdapter;
import com.example.capston.pcjari.PC.PCListItem;
import com.example.capston.pcjari.R;
import com.example.capston.pcjari.sqlite.DataBaseTables;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.capston.pcjari.MainActivity.db;

public class SearchByFavoriteFragment extends android.support.v4.app.Fragment implements EditText.OnEditorActionListener{
    private ListView pcListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private EditText editPc;
    private Button selectButton;
    private PCListAdapter pcListAdapter;
    private ArrayList<PCListItem> pcItem = new ArrayList<PCListItem>();
    private String url;
    private GettingPHP gPHP;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("즐겨찾기");

        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        pcListView = (ListView)view.findViewById(R.id.listview3);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout3);
        editPc = (EditText) view.findViewById(R.id.editpc3);
        selectButton = (Button)view.findViewById(R.id.button_search3);

        pcListAdapter = new PCListAdapter();
        dataSetting();

        editPc.setOnEditorActionListener(this);
        selectButton.setOnClickListener(selectListener);
        mSwipeRefreshLayout.setOnRefreshListener(refreshListener);
        pcListView.setOnItemClickListener(ListshortListener);
        pcListView.setOnItemLongClickListener(ListlongListener);

        return view;
    }

    // 키보드로 엔터 눌렀을 때
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
        if(v.getId() == R.id.editpc3 && actionId == EditorInfo.IME_ACTION_DONE) {
            nameSearch();
        }
        return false;
    }

    // 검색 버튼
    View.OnClickListener selectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nameSearch();
        }
    };

    // 새로고침
    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            dataSetting();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    //리스트 아이템 클릭했을 때 나오는 이벤트
    AdapterView.OnItemClickListener ListshortListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), DetailedInformationActivity.class);
            intent.putExtra(DetailedInformationActivity.POSITION, position);
            MainActivity.pc = pcItem.get(position);
            startActivity(intent);
        }
    };

    // 리스트 아이템 꾹 눌렀을 때 나오는 이벤트
    AdapterView.OnItemLongClickListener ListlongListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            PCListItem pc = pcListAdapter.getItem(position);
            int pcId = pc.getPcID();

            if(!MainActivity.favorite.contains(pcId)) {
                try {
                    MainActivity.favorite.add(pcId);
                    String sql = "INSERT INTO " + DataBaseTables.CreateDB_favorite._TABLENAME + " VALUES("
                            + pc.getPcID() + ");";
                    db.execSQL(sql);
                    Toast.makeText(getContext(), "즐겨찾기에 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "즐겨찾기를 추가하던 도중 에러가 났습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                try {
                    int index = MainActivity.favorite.indexOf(pcId);
                    MainActivity.favorite.remove(index);
                    pcItem.remove(position);            // 이거 다름
                    pcListAdapter.setItem(pcItem);    // 이거도
                    String sql = "DELETE FROM " + DataBaseTables.CreateDB_favorite._TABLENAME + " WHERE _ID="
                            + pc.getPcID() + ";";
                    db.execSQL(sql);
                    Toast.makeText(getContext(), "즐겨찾기에서 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "즐겨찾기를 하던 도중 에러가 났습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            pcListAdapter.notifyDataSetChanged();

            return true;
        }
    };

    private void dataSetting(){
        String favoriteList="";

        for(int i=0; i<MainActivity.favorite.size(); i++) {
            if(i!=0)
                favoriteList += "," + MainActivity.favorite.get(i);
            else
                favoriteList = String.valueOf(MainActivity.favorite.get(i));
        }

        url = MainActivity.server + "pclist_search.php?";
        url+= "code=2&favorite=" + favoriteList;

        importData(url);
    }

    // 데이터 이름으로 검색
    void nameSearch() {
        String favoriteList="";

        for(int i=0; i<MainActivity.favorite.size(); i++) {
            if(i!=0)
                favoriteList += "," + MainActivity.favorite.get(i);
            else
                favoriteList = String.valueOf(MainActivity.favorite.get(i));
        }

        url = MainActivity.server + "pclist_search.php?";
        url+= "code=2&favorite=" + favoriteList;
        url += "&namesearch=" + editPc.getText();

        importData(url);
    }

    private void importData(String url) {
        try {
            gPHP = new GettingPHP();
            String strData = gPHP.execute(url).get();
            JSONObject jObject = new JSONObject(strData);
            JSONArray results = jObject.getJSONArray("results");

            if (jObject.get("status").equals("OK")) {
                pcItem.clear();
                pcItem = new ArrayList<PCListItem>();

                if(results.length() == 0) {
                    Toast.makeText(getContext(), "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                    pcListAdapter.setItem(pcItem);
                    pcListView.setAdapter(pcListAdapter);
                }
                else {
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject temp = results.getJSONObject(i);

                        PCListItem pc = new PCListItem();
                        pc.setPcID(temp.getInt("id"));
                        pc.setTitle(temp.getString("name"));
                        pc.setIcon(temp.getString("url"));
                        pc.setSi(temp.getString("si"));
                        pc.setGu(temp.getString("gu"));
                        pc.setDong(temp.getString("dong"));
                        pc.setPrice(temp.getInt("price"));
                        pc.setTotalSeat(temp.getInt("total"));
                        pc.setSpaceSeat(temp.getInt("space"));
                        pc.setUsingSeat(temp.getInt("using"));
                        pc.setLocation_x(temp.getDouble("x"));
                        pc.setLocation_y(temp.getDouble("y"));

                        pc.setEtc_juso(temp.getString("etc_juso"));
                        pc.setNotice(temp.getString("notice"));
                        pc.setTel(temp.getString("tel"));
                        pc.setCpu(temp.getString("cpu"));
                        pc.setRam(temp.getString("ram"));
                        pc.setVga(temp.getString("vga"));
                        pc.setPeripheral(temp.getString("peripheral"));
                        pc.setSeatLength(temp.getInt("seatlength"));

                        if(temp.isNull("distance")) {
                            pc.setDist(temp.getDouble("distance"));
                        }

                        if(temp.getInt("card") == 0) {
                            pc.setCard(false);
                        } else {
                            pc.setCard(true);
                        }

                        pcItem.add(pc);
                    }

                    pcListAdapter = new PCListAdapter();
                    pcListAdapter.setItem(pcItem);
                    pcListView.setAdapter(pcListAdapter);
                }
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}