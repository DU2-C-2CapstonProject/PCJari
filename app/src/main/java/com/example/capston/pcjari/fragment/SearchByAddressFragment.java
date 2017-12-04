package com.example.capston.pcjari.fragment;

/**
 * Created by KangSeungho on 2017-10-27.
 */

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capston.pcjari.AddressSearchActivity;
import com.example.capston.pcjari.DetailedInformationActivity;
import com.example.capston.pcjari.MainActivity;
import com.example.capston.pcjari.PC.PCListAdapter;
import com.example.capston.pcjari.PC.PCListItem;
import com.example.capston.pcjari.R;
import com.example.capston.pcjari.sqlite.DataBaseTables;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class SearchByAddressFragment extends Fragment {
    private ListView pcListView;
    private PCListAdapter pcListAdapter;
    private ArrayList<PCListItem> pcItem = new ArrayList<PCListItem>();
    private Button selectButton;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView textView_SearchLocation;
    private ImageView dropdown_mark;
    private static String address[] = {"경기도", "성남시 수정구", "복정동"};
    SQLiteDatabase db = MainActivity.db;

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
        dropdown_mark = (ImageView) view.findViewById(R.id.dropdown_mark);
        selectButton = (Button)view.findViewById(R.id.button_search);

        pcListAdapter = new PCListAdapter();
        dataSetting();

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

    View.OnClickListener selectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {           //검색 버튼 이벤트
            // 밑의 코드는 즐겨찾기 테스트용 코드임.
            /*
            String sql = "SELECT * FROM " + DataBaseTables.CreateDB_favorite._TABLENAME + ";";
            Cursor results = db.rawQuery(sql, null);
            String str = "";

            results.moveToFirst();

            while(!results.isAfterLast()) {
                str = str.concat(results.getString(0));

                results.moveToNext();
            }
            /*
            try {
                String sql = "SELECT * FROM " + DataBaseTables.CreateDB_setting._TABLENAME + ";";
                Cursor results = db.rawQuery(sql, null);
                String str = "";

                results.moveToFirst();

                while(!results.isAfterLast()) {
                    String tmp = String.valueOf(results.getInt(1));
                    str = str.concat(tmp);

                    results.moveToNext();
                }
                results.close();

                Toast.makeText(getContext(), "아직 구현되지 않은 기능입니다." + str, Toast.LENGTH_SHORT).show();
            } catch (Exception e ) {
                Toast.makeText(getContext(), "에러", Toast.LENGTH_SHORT).show();
            }
            */

            Toast.makeText(getContext(), "아직 구현되지 않은 기능입니다.", Toast.LENGTH_SHORT).show();
        }
    };

    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            /*
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
            */
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };

    AdapterView.OnItemClickListener ListshortListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {          //리스트 아이템 클릭했을 때 나오는 이벤트
            Intent intent = new Intent(getActivity(), DetailedInformationActivity.class);
            intent.putExtra(DetailedInformationActivity.POSITION, position);
            MainActivity.pc = pcItem.get(position);
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

    View.OnClickListener addressSearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {           //동 설정
            Intent intent = new Intent(getContext(), AddressSearchActivity.class);
            startActivityForResult(intent, 0);
        }
    };

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

    void dataSetting() {
        String url = MainActivity.server + "pclist_search.php?";
        String code = "0";
        String gu = address[1];
        String dong = address[2];

        url+= "code=" + code + "&gu=" + gu + "&dong=" + dong;

        GettingPHP gPHP = new GettingPHP();
        gPHP.execute(url);
    }

    class GettingPHP extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                URL phpUrl = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) phpUrl.openConnection();

                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                        while (true) {
                            String line = br.readLine();
                            if (line == null)
                                break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonHtml.toString();
        }

        protected void onPostExecute(String str) {
            try {
                JSONObject jObject = new JSONObject(str);
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
                e.printStackTrace();
            }
        }
    }
}