package com.example.capston.pcjari;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capston.pcjari.Address.AddressAdapter;
import com.example.capston.pcjari.sqlite.DataBaseHelper;
import com.example.capston.pcjari.sqlite.DataBaseTables;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by KangSeungho on 2017-11-05.
 */

public class AddressSearchActivity extends AppCompatActivity implements EditText.OnEditorActionListener{
    Button button_search;
    EditText search_dong;
    AddressAdapter jusoAdapter;
    ArrayList<String> juso;
    ListView addressListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("주소 검색");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresssearch);

        juso = new ArrayList<String>();

        addressListView = (ListView) findViewById(R.id.content);
        search_dong = (EditText) findViewById(R.id.search_dong);
        button_search = (Button) findViewById(R.id.button_search);

        search_dong.setOnEditorActionListener(this);
        addressListView.setOnItemClickListener(addressClick);
        button_search.setOnClickListener(addressSearch);
    }

    // 폰으로 엔터키 눌렀을 때 리스트 검색
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
        if(v.getId() == R.id.search_dong && actionId == EditorInfo.IME_ACTION_DONE) {
            mysql_list_search();
        }
        return false;
    }

    // 검색 버튼 눌렀을 때 검색
    View.OnClickListener addressSearch = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mysql_list_search();
        }
    };

    // 리스트의 주소를 클릭 했을 때 이전 엑티비티로 전환
    AdapterView.OnItemClickListener addressClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            String address = juso.get(position);

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("address", address);

            setResult(RESULT_OK, intent);
            finish();
        }
    };

    void mysql_list_search() {
        String dong = search_dong.getText().toString();
        if (!dong.equals("")) {
            String url = "http://210.179.67.98:80/php/jusosearch.php?dong=";
            GettingPHP gPHP = new GettingPHP();

            gPHP.execute(url.concat(dong));
        }
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
                    juso.clear();
                    juso = new ArrayList<String>();

                    if(results.length() == 0) {
                        Toast.makeText(getApplicationContext(), "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject temp = results.getJSONObject(i);
                            juso.add(temp.get("si") + " " + temp.get("gu") + " " + temp.get("dong"));
                        }

                        jusoAdapter = new AddressAdapter();
                        jusoAdapter.addItem(juso);
                        addressListView.setAdapter(jusoAdapter);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}