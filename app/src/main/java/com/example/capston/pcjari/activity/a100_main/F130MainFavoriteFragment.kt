package com.example.capston.pcjari.activity.a100_main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.capston.pcjari.R
import com.example.capston.pcjari.base.BaseActivity
import com.example.capston.pcjari.databinding.F130FragmentFavoriteBinding
import com.example.capston.pcjari.pc.PCListResponse
import kotlinx.android.synthetic.main.f130_fragment_favorite.view.*
import kotlinx.android.synthetic.main.include_pc_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by KangSeungho on 2017-10-27.
 */
class F130MainFavoriteFragment : MainBaseFragment<F130FragmentFavoriteBinding>(R.layout.f130_fragment_favorite) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        activity?.title = getString(R.string.title_favorite)

        val view = binding.root

        initUI(view)
        setListener()

        mListener = object : RefreshListener {
            override fun done(name: String?) {
                networkAPI.getPCListByFavorite(2, main.favorite, name)
                        .enqueue(object : Callback<PCListResponse> {
                            override fun onResponse(call: Call<PCListResponse>, response: Response<PCListResponse>) {
                                Log.d(BaseActivity.TAG, "retrofit result : " + response.body())
                                var result = response.body() as PCListResponse

                                if(result.status == "OK") {
                                    pcListAdapter.setItem(result.pcList)
                                    pcListAdapter.notifyDataSetChanged()
                                }
                            }

                            override fun onFailure(call: Call<PCListResponse>, t: Throwable) {
                                Log.e(BaseActivity.TAG, "retrofit getPCListByFavorite error")
                            }
                        })
            }
        }

        mListener.done(null)

        return view
    }

    private fun initUI(view : View) {
        search_name = view.favorite_search_name
        search_button = view.favorite_search_button

        swipe = view.pc_list_swipe_layout
        listview = view.pc_listview
    }
}