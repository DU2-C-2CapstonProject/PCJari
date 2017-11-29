package com.example.capston.pcjari;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capston.pcjari.fragment.SearchByAddressFragment;
import com.example.capston.pcjari.sqlite.DataBaseHelper;
import com.example.capston.pcjari.sqlite.DataBaseTables;

/**
 * Created by KangSeungho on 2017-11-05.
 */

public class AddressSearchActivity extends AppCompatActivity {
    Button button_search;
    EditText search_dong;

    private DataBaseHelper DBHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.setTitle("주소 검색");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresssearch);

        DBHelper = new DataBaseHelper(getApplicationContext());
        db = DBHelper.getWritableDatabase();

        search_dong = (EditText) findViewById(R.id.search_dong);
        button_search = (Button) findViewById(R.id.button_search);

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String juso = "";
                String dong = search_dong.getText().toString();
                if(dong.equals("")) {
                    Toast.makeText(getApplicationContext(), "아직 구현되지 않은 기능입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    String sql = "SELECT * FROM " + DataBaseTables.CreateDB_juso._TABLENAME + " WHERE DONG LIKE'" + dong + "%';";
                    Cursor results = db.rawQuery(sql, null);

                    if(results.getCount() == 0)
                        Toast.makeText(getApplicationContext(), "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
                    else {
                        results.moveToFirst();
                        if(!results.isAfterLast()) {
                            juso = results.getString(1) + " " + results.getString(2) + " " + results.getString(3);
                        }

                        Toast.makeText(getApplicationContext(), juso, Toast.LENGTH_SHORT).show();
                    }

                    results.close();
                }
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
