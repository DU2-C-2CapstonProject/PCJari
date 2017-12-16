package com.example.capston.pcjari;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;

import com.example.capston.pcjari.Seat.Seat;
import com.example.capston.pcjari.Seat.SeatAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by KangSeungho on 2017-11-15.
 */

public class SeatNowActivity  extends AppCompatActivity {
    ArrayList<Seat> seats = new ArrayList<Seat>();
    SeatAdapter seatAdapter;
    GridView seatGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("좌석현황");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seatnow);

        dataSetting();
    }

    void dataSetting() {
        /*
        for(int i=0; i<5; i++) {
            Seat seat = new Seat();
            seat.setPc_id(i);
            seats.add(seat);
        }
        */
        String url = MainActivity.server + "seat_search.php?id=" + MainActivity.pc.getPcID();
        GettingPHP gettingPHP = new GettingPHP();
        gettingPHP.execute(url);
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
                    seats.clear();
                    seats = new ArrayList<Seat>();

                    if(results.length() == 0) {
                        Toast.makeText(getApplicationContext(), "좌석표시에서 오류", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject temp = results.getJSONObject(i);

                            Seat seat = new Seat();

                            seat.setPc_id(temp.getInt("pc_id"));
                            seat.setPlace(temp.getInt("place_id"));
                            seat.setSeat_id(temp.getInt("seat_id"));

                            if(!temp.getString("state").equals("null"))
                                seat.setPc_state(temp.getInt("state"));
                            if(!temp.getString("time").equals("null"))
                                seat.setPc_time(temp.getInt("time"));

                            seats.add(seat);
                        }
                    }
                    seatAdapter = new SeatAdapter(getApplicationContext());
                    seatAdapter.addSeats(seats);
                    seatGridView = (GridView) findViewById(R.id.gridView1);
                    seatGridView.setNumColumns(4);
                    seatGridView.setAdapter(seatAdapter);
                    seatGridView.setClickable(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
