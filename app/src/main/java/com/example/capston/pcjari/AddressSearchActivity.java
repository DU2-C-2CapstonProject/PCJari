package com.example.capston.pcjari;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.capston.pcjari.fragment.SearchByAddressFragment;

/**
 * Created by 94tig on 2017-11-05.
 */

public class AddressSearchActivity extends AppCompatActivity {
    Button button_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("주소 검색");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresssearch);

        button_search = (Button) findViewById(R.id.button_search);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "아직 구현되지 않은 기능입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        /*
        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "아직 구현되지 않은 기능입니다.", Snackbar.LENGTH_LONG)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("test", "내가 최고지!");
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        }).show();
            }
        });
        */
    }
}
