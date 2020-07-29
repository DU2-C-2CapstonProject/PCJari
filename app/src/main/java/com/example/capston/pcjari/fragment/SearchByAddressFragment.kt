package com.example.capston.pcjari.fragment

import android.app.Activity
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
import kotlinx.android.synthetic.main.fragment_searchbyaddress.view.*
import org.json.JSONObject
import java.util.*

/**
 * Created by KangSeungho on 2017-10-27.
 */
class SearchByAddressFragment : Fragment(), OnEditorActionListener {
    private lateinit var pcListAdapter: PCListAdapter

    private var pcItem: ArrayList<PCListItem> = ArrayList()

    lateinit var editpc:EditText
    lateinit var listview1:ListView
    lateinit var button_search:Button
    lateinit var swipe_layout:SwipeRefreshLayout
    lateinit var textView_SearchLocation:TextView
    lateinit var dropdown_mark:ImageView

    private lateinit var gPHP: GettingPHP
    var db: SQLiteDatabase? = MainActivity.db
    var url: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        requireActivity().title = "주소로 찾기"
        val view = inflater.inflate(R.layout.fragment_searchbyaddress, container, false)

        pcListAdapter = PCListAdapter(requireActivity())

        dataSetting()

        editpc = view.editpc
        listview1 = view.listview1
        button_search = view.button_search
        swipe_layout = view.swipe_layout
        textView_SearchLocation = view.textView_SearchLocation
        dropdown_mark = view.dropdown_mark

        editpc.setOnEditorActionListener(this)

        listview1.adapter = pcListAdapter
        listview1.onItemClickListener = ListshortListener
        listview1.onItemLongClickListener = ListlongListener

        button_search.setOnClickListener(selectListener)

        swipe_layout.setOnRefreshListener(refreshListener)

        textView_SearchLocation.text = address?.get(2)
        textView_SearchLocation.setOnClickListener(addressSearch)

        dropdown_mark.setOnClickListener(addressSearch)

        return view
    }

    // 키보드로 엔터 눌렀을 때
    override fun onEditorAction(v: TextView, actionId: Int, keyEvent: KeyEvent): Boolean {
        if (v.id == R.id.editpc && actionId == EditorInfo.IME_ACTION_DONE) {
            nameSearch()
        }
        return false
    }

    // 검색 버튼
    private var selectListener: View.OnClickListener = View.OnClickListener { nameSearch() }

    // 새로고침
    private var refreshListener: OnRefreshListener = OnRefreshListener {
        dataSetting()
        swipe_layout.isRefreshing = false
    }

    //리스트 아이템 클릭했을 때 나오는 이벤트
    private var ListshortListener: OnItemClickListener = OnItemClickListener { parent, view, position, id ->
        val intent = Intent(activity, DetailedInformationActivity::class.java)
        intent.putExtra(DetailedInformationActivity.POSITION, position)
        MainActivity.pc = pcItem[position]
        startActivity(intent)
    }

    // 리스트 아이템 꾹 눌렀을 때 나오는 이벤트
    var ListlongListener: OnItemLongClickListener = OnItemLongClickListener { parent, view, position, id ->
        val pc = pcListAdapter.getItem(position)
        val pcId = pc.pcID

        if (!MainActivity.favorite.contains(pcId)) {
            try {
                MainActivity.favorite.add(pcId)

                val sql = ("INSERT INTO " + CreateDB_favorite._TABLENAME + " VALUES(" + pc.pcID + ");")
                db?.execSQL(sql)

                Toast.makeText(context, "즐겨찾기에 추가 되었습니다.", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "즐겨찾기를 추가하던 도중 에러가 났습니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            try {
                val index: Int = MainActivity.favorite.indexOf(pcId)
                MainActivity.favorite.removeAt(index)

                val sql = ("DELETE FROM " + CreateDB_favorite._TABLENAME + " WHERE _ID=" + pc.pcID + ";")
                db?.execSQL(sql)

                Toast.makeText(context, "즐겨찾기에서 삭제 되었습니다.", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "즐겨찾기를 하던 도중 에러가 났습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        pcListAdapter.notifyDataSetChanged()
        true
    }

    // 동 설정
    private var addressSearch: View.OnClickListener = View.OnClickListener {
        val intent = Intent(context, AddressSearchActivity::class.java)
        startActivityForResult(intent, 0)
    }

    // intent 이후 되돌아올 때 실행되는 메소드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                val tmp: Array<String?>? = data.getStringExtra("address")?.split(" ".toRegex())?.toTypedArray()
                tmp?.get(0)?.let { address[0] = it }

                if (tmp != null) {
                    if (tmp.size > 3) {
                        address[1] = tmp[1] + " " + tmp[2]
                        address[2] = tmp[3].toString()
                    } else {
                        address[1] = tmp[1].toString()
                        address[2] = tmp[2].toString()
                    }
                }
                textView_SearchLocation.text = address[2]
                dataSetting()
            }
        }
    }

    // 데이터 검색
    private fun dataSetting() {
        url = MainActivity.server + "pclist_search.php?"
        url += "code=0&gu=" + address[1] + "&dong=" + address[2]
        importData(url)
    }

    // 데이터 이름으로 검색
    private fun nameSearch() {
        url += "&namesearch=" + editpc.text
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

    companion object {
        private val address: Array<String> = arrayOf("경기도", "성남시 수정구", "복정동")
    }
}