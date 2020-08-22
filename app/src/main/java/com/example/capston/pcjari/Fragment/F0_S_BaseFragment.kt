package com.example.capston.pcjari.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.capston.pcjari.Activity.A3_InformationActivity
import com.example.capston.pcjari.Activity.A2_MainActivity
import com.example.capston.pcjari.PC.PCListAdapter
import com.example.capston.pcjari.PC.PCListManager
import com.example.capston.pcjari.R
import com.example.capston.pcjari.DB.DataBaseTables

open class F0_S_BaseFragment : F0_BaseFragment() {
    lateinit var main : A2_MainActivity

    interface RefreshListener {
        fun done(name: String?)
    }
    lateinit var mListener: RefreshListener

    val pcListManager = PCListManager()
    protected lateinit var pcListAdapter : PCListAdapter

    protected lateinit var search_name: EditText
    protected lateinit var search_button: Button

    protected lateinit var swipe: SwipeRefreshLayout
    protected lateinit var listview: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        main = activity as A2_MainActivity
        pcListAdapter = PCListAdapter(main)

        return view
    }

    protected open fun setListener() {
        // 키보드 엔터 리스너
        search_name.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val done = when (v.id) {
                    R.id.address_search_name -> true
                    R.id.gps_search_name -> true
                    R.id.favorite_search_name -> true
                    else -> false
                }

                if (done)
                    mListener.done(search_name.text.toString())
            }

            return@setOnEditorActionListener false
        }

        // 검색 버튼 리스너
        search_button.setOnClickListener {
            mListener.done(search_name.text.toString())
        }

        // 새로고침 리스너
        swipe.setOnRefreshListener {
            mListener.done(null)
            swipe.isRefreshing = false
        }

        // 리스트뷰 리스너너
       listview.adapter = pcListAdapter
        listview.onItemClickListener = listshortListener
        listview.onItemLongClickListener = listlongListener
    }

    //리스트 아이템 클릭했을 때 나오는 이벤트
    protected var listshortListener: AdapterView.OnItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
        main.pc = pcListAdapter.getItem(position)

        val intent = Intent(activity, A3_InformationActivity::class.java)
        intent.putExtra(A3_InformationActivity.POSITION, position)
        intent.putExtra(A3_InformationActivity.PCITEM, main.pc)
        startActivity(intent)
    }

    // 리스트 아이템 꾹 눌렀을 때 나오는 이벤트
    protected var listlongListener: AdapterView.OnItemLongClickListener = AdapterView.OnItemLongClickListener { parent, view, position, id ->
        val pc = pcListAdapter.getItem(position)

        if (!main.favorite.contains(pc.pcID)) {
            try {
                main.favorite.add(pc.pcID)

                val sql = ("INSERT INTO " + DataBaseTables.CreateDB_favorite._TABLENAME + " VALUES(" + pc.pcID + ");")
                main.db.execSQL(sql)

                Toast.makeText(context, "즐겨찾기에 추가 되었습니다.", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "즐겨찾기를 추가하던 도중 에러가 났습니다.", Toast.LENGTH_SHORT).show()
            }
        } else {
            try {
                val index: Int = main.favorite.indexOf(pc.pcID)
                main.favorite.removeAt(index)

                val sql = ("DELETE FROM " + DataBaseTables.CreateDB_favorite._TABLENAME + " WHERE _ID=" + pc.pcID + ";")
                main.db.execSQL(sql)

                Toast.makeText(context, "즐겨찾기에서 삭제 되었습니다.", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "즐겨찾기를 하던 도중 에러가 났습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        pcListAdapter.notifyDataSetChanged()
        true
    }
}