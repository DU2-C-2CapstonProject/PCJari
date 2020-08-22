package com.example.capston.pcjari.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capston.pcjari.R
import kotlinx.android.synthetic.main.f3_s_fragment_favorite.view.*

/**
 * Created by KangSeungho on 2017-10-27.
 */
class F3_S_FavoriteFragment : F0_S_BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        requireActivity().title = "즐겨찾기"
        val view = inflater.inflate(R.layout.f3_s_fragment_favorite, container, false)

        mListener = object : RefreshListener {
            override fun done(name: String?) {
                val pcList = pcListManager.getPCListByFavorite(main.favorite, name)

                pcListAdapter.setItem(pcList)
                pcListAdapter.notifyDataSetChanged()
            }
        }

        initUI(view)
        setListener()

        mListener.done(null)

        return view
    }

    private fun initUI(view : View) {
        search_name = view.favorite_search_name
        search_button = view.favorite_search_button

        swipe = view.favorite_swipe
        listview = view.favorite_listview
    }
}