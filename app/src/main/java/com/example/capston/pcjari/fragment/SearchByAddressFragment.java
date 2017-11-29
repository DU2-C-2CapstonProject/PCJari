package com.example.capston.pcjari.fragment;

/**
 * Created by 94tig on 2017-10-27.
 */

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.example.capston.pcjari.PCListAdapter;
import com.example.capston.pcjari.PCListItem;
import com.example.capston.pcjari.R;
import com.example.capston.pcjari.StaticData;
import com.example.capston.pcjari.sqlite.DataBaseHelper;
import com.example.capston.pcjari.sqlite.DataBaseTables;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class SearchByAddressFragment extends Fragment {
    private ListView pcListView;
    private PCListAdapter pcListAdapter;
    private PCListItem pcItem[] = StaticData.pcItems;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView textView_SearchLocation;
    private static String address;

    private DataBaseHelper DBHelper;
    private SQLiteDatabase db;

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
        ImageView dropdown_mark = (ImageView) view.findViewById(R.id.dropdown_mark);
        Button selectButton = (Button)view.findViewById(R.id.button_search);
        selectButton.setOnClickListener(selectListener);

        DBHelper = new DataBaseHelper(getContext());
        db = DBHelper.getWritableDatabase();

        if(address != null)
            textView_SearchLocation.setText(address);

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

    AdapterView.OnItemClickListener ListshortListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {          //리스트 아이템 클릭했을 때 나오는 이벤트
            Intent intent = new Intent(getActivity(), DetailedInformationActivity.class);
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
            if(pc.isFavorite()) {
                try {
                    String sql = "INSERT INTO " + DataBaseTables.CreateDB_favorite._TABLENAME + " VALUES("
                            + pc.getPcID() + ");";
                    db.execSQL(sql);

                    Toast.makeText(getContext(), "즐겨찾기에 추가 되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    String sql = "DELETE FROM " + DataBaseTables.CreateDB_favorite._TABLENAME + " WHERE _ID="
                            + pc.getPcID() + ";";
                    db.execSQL(sql);

                    Toast.makeText(getContext(), "즐겨찾기를 추가하던 도중 에러가 났습니다.", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                try {

                    Toast.makeText(getContext(), "즐겨찾기에서 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getContext(), "즐겨찾기를 추가하던 도중 에러가 났습니다.", Toast.LENGTH_SHORT).show();
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

    //AddressSearchActivity(동 검색 액티비티)에서 결과값 반환
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if(requestCode == 0) {
                address = "경기도 성남시 수정구";
                Toast.makeText(getContext(), data.getStringExtra("test"), Toast.LENGTH_LONG).show();
                textView_SearchLocation.setText("경기도 성남시 수정구");
            }
        }
    }

    private void dataSetting(){
        for(PCListItem pc : pcItem)
            pcListAdapter.addItem(pc);

        pcListView.setAdapter(pcListAdapter);
    }
}
