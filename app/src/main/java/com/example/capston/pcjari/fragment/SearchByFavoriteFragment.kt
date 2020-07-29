package com.example.capston.pcjari.fragment

import android.content.Intent
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.capston.pcjari.DetailedInformationActivity
import com.example.capston.pcjari.GettingPHP
import com.example.capston.pcjari.MainActivity
import com.example.capston.pcjari.PC.PCListAdapter
import com.example.capston.pcjari.PC.PCListItem
import com.example.capston.pcjari.R
import com.example.capston.pcjari.sqlite.DataBaseTables.CreateDB_favorite
import kotlinx.android.synthetic.main.fragment_favorite.view.*
import org.json.JSONObject
import java.util.*

/**
 * Created by KangSeungho on 2017-10-27.
 */
class SearchByFavoriteFragment : BaseFragment(), OnEditorActionListener {
    private lateinit var pcListAdapter: PCListAdapter
    private var pcItem: ArrayList<PCListItem> = ArrayList()
    private lateinit var url: String
    private lateinit var gPHP: GettingPHP

    lateinit var editpc3: EditText
    lateinit var button_search3: Button
    lateinit var swipe_layout3: SwipeRefreshLayout
    lateinit var listview3: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "즐겨찾기"

        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        editpc3 = view.editpc3
        button_search3 = view.button_search3
        swipe_layout3 = view.swipe_layout3
        listview3 = view.listview3

        pcListAdapter = PCListAdapter(requireActivity())

        dataSetting()

        editpc3.setOnEditorActionListener(this)

        button_search3.setOnClickListener(selectListener)

        swipe_layout3.setOnRefreshListener(refreshListener)

        listview3.onItemClickListener = ListshortListener
        listview3.onItemLongClickListener = ListlongListener

        return view
    }

    // 키보드로 엔터 눌렀을 때
    override fun onEditorAction(v: TextView, actionId: Int, keyEvent: KeyEvent): Boolean {
        if (v.id == R.id.editpc3 && actionId == EditorInfo.IME_ACTION_DONE) {
            nameSearch()
        }

        return false
    }

    // 검색 버튼
    private var selectListener: View.OnClickListener = View.OnClickListener { nameSearch() }

    // 새로고침
    private var refreshListener: OnRefreshListener = OnRefreshListener {
        dataSetting()
        swipe_layout3.isRefreshing = false
    }

    //리스트 아이템 클릭했을 때 나오는 이벤트
    private var ListshortListener: OnItemClickListener = OnItemClickListener { parent, view, position, id ->
        MainActivity.pc = pcItem[position]

        val intent = Intent(activity, DetailedInformationActivity::class.java)
        intent.putExtra(DetailedInformationActivity.POSITION, position)
        startActivity(intent)
    }

    // 리스트 아이템 꾹 눌렀을 때 나오는 이벤트
    private var ListlongListener: OnItemLongClickListener? = OnItemLongClickListener { parent, view, position, id ->
        val pc = pcListAdapter.getItem(position)
        val pcId = pc.pcID

        if (!MainActivity.favorite.contains(pcId)) {
            try {
                MainActivity.favorite.add(pcId)

                val sql = ("INSERT INTO " + CreateDB_favorite._TABLENAME + " VALUES(" + pc.pcID + ");")

                MainActivity.db?.execSQL(sql)

                Toast.makeText(context, "즐겨찾기에 추가 되었습니다.", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "즐겨찾기를 추가하던 도중 에러가 났습니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            try {
                val index: Int = MainActivity.favorite.indexOf(pcId)
                MainActivity.favorite.removeAt(index)

                pcItem.removeAt(position)
                pcListAdapter.setItem(pcItem)

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
        var favoriteList = ""
        for (i in MainActivity.favorite.indices) {
            if (i != 0) favoriteList += "," + MainActivity.favorite[i] else favoriteList = MainActivity.favorite[i].toString()
        }

        url = MainActivity.server + "pclist_search.php?"
        url += "code=2&favorite=$favoriteList"

        importData(url)
    }

    // 데이터 이름으로 검색
    private fun nameSearch() {
        var favoriteList = ""
        for (i in MainActivity.favorite.indices) {
            if (i != 0) favoriteList += "," + MainActivity.favorite[i] else favoriteList = MainActivity.favorite[i].toString()
        }

        url = MainActivity.server + "pclist_search.php?"
        url += "code=2&favorite=$favoriteList"
        url += "&namesearch=" + editpc3.text

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
                        if (temp.isNull("distance")) {
                            pc.dist = temp.getDouble("distance")
                        }
                        pc.isCard = temp.getInt("card") != 0
                        pcItem.add(pc)
                    }

                    pcListAdapter.setItem(pcItem)
                }

                pcListAdapter.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}