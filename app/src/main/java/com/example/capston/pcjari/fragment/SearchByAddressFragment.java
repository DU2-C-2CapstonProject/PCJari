package com.example.capston.pcjari.fragment;

/**
 * Created by KangSeungho on 2017-10-27.
 */

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capston.pcjari.AddressSearchActivity;
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

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static android.app.Activity.RESULT_OK;

public class SearchByAddressFragment extends Fragment implements EditText.OnEditorActionListener{
    private ListView pcListView;
    private PCListAdapter pcListAdapter;
    private ArrayList<PCListItem> pcItem = new ArrayList<PCListItem>();
    private Button selectButton;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView textView_SearchLocation;
    private ImageView dropdown_mark;
    private EditText editPc;
    private static String address[] = {"경기도", "성남시 수정구", "복정동"};
    private GettingPHP gPHP;
    SQLiteDatabase db = MainActivity.db;
    String url="";

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
        textView_SearchLocation = (TextView) view.findViewById(R.id.textView_SearchLocation);
        editPc = (EditText) view.findViewById(R.id.editpc);
        dropdown_mark = (ImageView) view.findViewById(R.id.dropdown_mark);
        selectButton = (Button)view.findViewById(R.id.button_search);

        pcListAdapter = new PCListAdapter();
        dataSetting();

        editPc.setOnEditorActionListener(this);
        pcListView.setAdapter(pcListAdapter);
        selectButton.setOnClickListener(selectListener);
        mSwipeRefreshLayout.setOnRefreshListener(refreshListener);
        pcListView.setOnItemClickListener(ListshortListener);
        pcListView.setOnItemLongClickListener(ListlongListener);
        textView_SearchLocation.setText(address[2]);
        textView_SearchLocation.setOnClickListener(addressSearch);
        dropdown_mark.setOnClickListener(addressSearch);

        return view;
    }

    // 키보드로 엔터 눌렀을 때
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
        if(v.getId() == R.id.editpc && actionId == EditorInfo.IME_ACTION_DONE) {
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

    // 동 설정
    View.OnClickListener addressSearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddressSearchActivity.class);
            startActivityForResult(intent, 0);
        }
    };

    // intent 이후 되돌아올 때 실행되는 메소드
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) {

        } else {
            if(requestCode == 0) {
                String tmp[] = data.getStringExtra("address").split(" ");
                address[0] = tmp[0];
                if(tmp.length > 3){
                    address[1] = tmp[1].concat(" " + tmp[2]);
                    address[2] = tmp[3];
                }
                else {
                    address[1] = tmp[1];
                    address[2] = tmp[2];
                }
                textView_SearchLocation.setText(address[2]);
                dataSetting();
            }
        }
    }

    // 데이터 검색
    void dataSetting() {
        url = MainActivity.server + "pclist_search.php?";
        url+= "code=0&gu=" + address[1] + "&dong=" + address[2];

        importData(url);
    }

    // 데이터 이름으로 검색
    void nameSearch() {
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