package com.example.capston.pcjari.fragment

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.capston.pcjari.*
import com.example.capston.pcjari.PC.PCListAdapter
import com.example.capston.pcjari.PC.PCListItem
import com.example.capston.pcjari.sqlite.DataBaseTables.CreateDB_favorite
import kotlinx.android.synthetic.main.fragment_searchbyme.view.*
import org.json.JSONObject
import java.util.*

/**
 * Created by KangSeungho on 2017-10-27.
 */
class SearchByMeFragment : Fragment(), OnEditorActionListener {
    private lateinit var pcListAdapter: PCListAdapter

    private var pcItem: ArrayList<PCListItem> = ArrayList()

    private lateinit var gps: GPSTracker
    private lateinit var dist: String

    private lateinit var gPHP: GettingPHP
    private var db: SQLiteDatabase? = MainActivity.db
    private var url: String? = ""

    lateinit var editpc2: EditText
    lateinit var button_search2: Button
    lateinit var swipe_layout2: SwipeRefreshLayout
    lateinit var listview2: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "내 주변"

        val view = inflater.inflate(R.layout.fragment_searchbyme, container, false)

        editpc2 = view.editpc2
        button_search2 = view.button_search2
        swipe_layout2 = view.swipe_layout2
        listview2 = view.listview2

        gps = GPSTracker(requireActivity())
        dist = (MainActivity.dist.toDouble() / 10).toString()
        pcListAdapter = PCListAdapter(requireActivity())
        dataSetting()

        editpc2.setOnEditorActionListener(this)

        button_search2.setOnClickListener(selectListener)

        swipe_layout2.setOnRefreshListener(refreshListener)

        listview2.setOnItemClickListener(ListshortListener)
        listview2.setOnItemLongClickListener(ListlongListener)
        return view
    }

    // 키보드로 엔터 눌렀을 때
    override fun onEditorAction(v: TextView, actionId: Int, keyEvent: KeyEvent): Boolean {
        if (v.id == R.id.editpc2 && actionId == EditorInfo.IME_ACTION_DONE) {
            nameSearch()
        }
        return false
    }

    // 검색 버튼
    private var selectListener: View.OnClickListener = View.OnClickListener { nameSearch() }

    // 새로고침
    private var refreshListener: OnRefreshListener = OnRefreshListener {
        dataSetting()
        swipe_layout2.isRefreshing = false
    }

    //리스트 아이템 클릭했을 때 나오는 이벤트
    private var ListshortListener: OnItemClickListener = OnItemClickListener { parent, view, position, id ->
        MainActivity.pc = pcItem[position]

        val intent = Intent(activity, DetailedInformationActivity::class.java)
        intent.putExtra(DetailedInformationActivity.POSITION, position)
        startActivity(intent)
    }

    // 리스트 아이템 꾹 눌렀을 때 나오는 이벤트
    private var ListlongListener: OnItemLongClickListener = OnItemLongClickListener { parent, view, position, id ->
        val pc = pcListAdapter.getItem(position)

        if (!MainActivity.favorite.contains(pc.pcID)) {
            try {
                MainActivity.favorite.add(pc.pcID)

                val sql = ("INSERT INTO " + CreateDB_favorite._TABLENAME + " VALUES(" + pc.pcID + ");")
                MainActivity.db?.execSQL(sql)

                Toast.makeText(context, "즐겨찾기에 추가 되었습니다.", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "즐겨찾기를 추가하던 도중 에러가 났습니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            try {
                val index: Int = MainActivity.favorite.indexOf(pc.pcID)
                MainActivity.favorite.removeAt(index)

                val sql = ("DELETE FROM " + CreateDB_favorite._TABLENAME + " WHERE _ID=" + pc.pcID + ";")
                MainActivity.db?.execSQL(sql)

                Toast.makeText(context, "즐겨찾기에서 삭제 되었습니다.", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "즐겨찾기를 하던 도중 에러가 났습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        pcListAdapter.notifyDataSetChanged()
        true
    }

    private fun dataSetting() {
        gps.Update()

        url = MainActivity.server + "pclist_search.php?"
        url += "code=1&lat=" + gps.getLatitude() + "&lng=" + gps.getLongitude() + "&dist=" + dist
        importData(url)
    }

    // 데이터 이름으로 검색
    fun nameSearch() {
        gps.Update()

        url = MainActivity.server + "pclist_search.php?"
        url += "code=1&lat=" + gps.getLatitude() + "&lng=" + gps.getLongitude() + "&dist=" + dist
        url += "&namesearch=" + editpc2.text
        importData(url)
    }

    private fun importData(url: String?) {
        try {
            gPHP = GettingPHP()
            val strData = gPHP.execute(url).get()

            val jObject = JSONObject(strData)
            val results = jObject.getJSONArray("results")

            if (jObject["status"] == "OK") {
                pcItem.clear()

                if (results.length() == 0) {
                    Toast.makeText(context, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show()

                    pcListAdapter.setItem(pcItem)
                    listview2.adapter = pcListAdapter
                } else {
                    for (i in 0 until results.length()) {
                        val temp = results.getJSONObject(i)
                        val pc = PCListItem()
                        pc.pcID = temp.getInt("id")
                        pc.title = temp.getString("name")
                        pc.icon = temp.getString("url")
                        pc.si = temp.getString("si")
                        pc.gu = temp.getString("gu")
                        pc.dong = temp.getString("dong")
                        pc.price = temp.getInt("price")
                        pc.totalSeat = temp.getInt("total")
                        pc.spaceSeat = temp.getInt("space")
                        pc.usingSeat = temp.getInt("using")
                        pc.location_x = temp.getDouble("x")
                        pc.location_y = temp.getDouble("y")
                        pc.etc_juso = temp.getString("etc_juso")
                        pc.notice = temp.getString("notice")
                        pc.tel = temp.getString("tel")
                        pc.cpu = temp.getString("cpu")
                        pc.ram = temp.getString("ram")
                        pc.vga = temp.getString("vga")
                        pc.peripheral = temp.getString("peripheral")
                        pc.seatLength = temp.getInt("seatlength")
                        pc.dist = temp.getDouble("distance")
                        pc.isCard = temp.getInt("card") != 0
                        pcItem.add(pc)
                    }
                    pcListAdapter.setItem(pcItem)

                    listview2.adapter = pcListAdapter
                }

                pcListAdapter.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}