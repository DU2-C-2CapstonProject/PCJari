package com.example.capston.pcjari.Activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView.OnEditorActionListener
import com.example.capston.pcjari.Activity.A9_Location_Activity.Location.LocationAdapter
import com.example.capston.pcjari.R
import com.example.capston.pcjari.Util.GettingPHP
import kotlinx.android.synthetic.main.a9_activity_location.*
import org.json.JSONObject
import java.util.*

/**
 * Created by KangSeungho on 2017-11-05.
 */
class A9_LocationActivity : A0_BaseActivity(), OnEditorActionListener {
    private lateinit var jusoAdapter: LocationAdapter
    private lateinit var juso: ArrayList<String>
    private lateinit var gPHP: GettingPHP

    override fun onCreate(savedInstanceState: Bundle?) {
        this.title = "주소 검색"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a9_activity_location)

        juso = ArrayList()
        search_dong.setOnEditorActionListener(this)
        content.onItemClickListener = addressClick
        address_search_button.setOnClickListener(addressSearch)
    }

    // 폰으로 엔터키 눌렀을 때 리스트 검색
    override fun onEditorAction(v: TextView, actionId: Int, keyEvent: KeyEvent): Boolean {
        if (v.id == R.id.search_dong && actionId == EditorInfo.IME_ACTION_DONE) {
            mysql_list_search()
        }
        return false
    }

    // 검색 버튼 눌렀을 때 검색
    var addressSearch: View.OnClickListener = View.OnClickListener { mysql_list_search() }

    // 리스트의 주소를 클릭 했을 때 이전 엑티비티로 전환
    var addressClick: OnItemClickListener = OnItemClickListener { adapterView, view, position, id ->
        val address = juso.get(position)
        val intent = Intent(applicationContext, A2_MainActivity::class.java)
        intent.putExtra("address", address)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun mysql_list_search() {
        val dong = search_dong.getText().toString()
        search_result.text = " \"$dong\""
        if (dong != "") {
            val url: String = A2_MainActivity.server + "jusosearch.php?dong=" + dong
            importData(url)
            val gPHP = GettingPHP()
            gPHP.execute(url + dong)
        }
    }

    fun importData(url: String?) {
        try {
            gPHP = GettingPHP()
            val strData = gPHP.execute(url).get()
            val jObject = JSONObject(strData)
            val results = jObject.getJSONArray("results")
            if (jObject["status"] == "OK") {
                juso.clear()
                juso = ArrayList()
                if (results.length() == 0) {
                    Toast.makeText(applicationContext, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    for (i in 0 until results.length()) {
                        val temp = results.getJSONObject(i)
                        juso.add(temp["si"].toString() + " " + temp["gu"] + " " + temp["dong"])
                    }
                    jusoAdapter = LocationAdapter(this)
                    jusoAdapter.addItem(juso)
                    content.adapter = jusoAdapter
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}