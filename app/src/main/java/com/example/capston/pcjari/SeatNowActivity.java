package com.example.capston.pcjari;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.RelativeLayout;
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

import androidx.appcompat.app.AppCompatActivity;
import pl.polidea.view.ZoomView;

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

        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_seatnow, null, false);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ZoomView zoomView = new ZoomView(getApplicationContext());
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMaxZoom(4f);
        //zoomView.setMiniMapEnabled(true);
        //zoomView.setMiniMapCaptionSize(10);

        RelativeLayout container = (RelativeLayout) findViewById(R.id.seatnow_container);
        container.addView(zoomView);
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
                        Toast.makeText(getApplicationContext(), "데이터를 가져오던 중 오류가 발생하였습니다.", Toast.LENGTH_SHORT).show();
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
                    seatGridView.setNumColumns(MainActivity.pc.getSeatLength());
                    seatGridView.setAdapter(seatAdapter);
                    seatGridView.setEnabled(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
