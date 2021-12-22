package com.example.capston.pcjari.activity.a100_main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capston.pcjari.R
import com.example.capston.pcjari.activity.A200InfoActivity
import com.example.capston.pcjari.base.BaseFragment
import com.example.capston.pcjari.databinding.F120FragmentGpsBinding
import com.example.capston.pcjari.pc.PCListAdapter
import com.example.capston.pcjari.pc.PCListItem
import com.example.capston.pcjari.util.Preferences
import kotlinx.android.synthetic.main.f120_fragment_gps.view.*
import kotlinx.android.synthetic.main.include_pc_list.view.*
import java.util.*

/**
 * Created by KangSeungho on 2017-10-27.
 */
class F120MainGpsFragment : BaseFragment<F120FragmentGpsBinding>() {
    override fun getLayoutResId() = R.layout.f120_fragment_gps

    private val vm: F120MainGpsFragmentViewModel by viewModels()

    private val pcListAdapter : PCListAdapter = PCListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = vm

        activity?.title = getString(R.string.title_search_by_me)

        initUI()
        initObserver()

        vm.updateLocation()
    }

    fun initUI() {
        // 검색 입력 완료 이벤트
        binding.gpsSearchName.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
                vm.updateLocation()

            return@setOnEditorActionListener false
        }

        // 검색 버튼
        binding.gpsSearchButton.setOnClickListener { vm.updateLocation() }

        // 새로고침
        binding.pcListLayout.pcListSwipeLayout.setOnRefreshListener {
            vm.updateLocation()
            binding.pcListLayout.pcListSwipeLayout.isRefreshing = false
        }

        pcListAdapter.setOnItemClickListener {
            val intent = Intent(activity, A200InfoActivity::class.java)
            intent.putExtra(A200InfoActivity.PCITEM, it)
            startActivity(intent)
        }

        pcListAdapter.setOnItemLongClickListener{ position: Int, Item: PCListItem ->
            when(!Preferences.favorite_list.contains(Item.pcID)) {
                true -> {
                    Preferences.addFavorite(Item.pcID)
                    Toast.makeText(context, "즐겨찾기에 추가 되었습니다.", Toast.LENGTH_SHORT).show()
                }
                false -> {
                    Preferences.removeFavorite(Item.pcID)
                    Toast.makeText(context, "즐겨찾기에서 삭제 되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            pcListAdapter.notifyItemChanged(position)
        }

        binding.pcListLayout.pcListview.layoutManager = LinearLayoutManager(activity)
        binding.pcListLayout.pcListview.adapter = pcListAdapter
    }

    fun initObserver() {
        vm.location.observe(viewLifecycleOwner) {
            vm.getPCList(it)
        }

        vm.pcList.observe(viewLifecycleOwner) {
            pcListAdapter.submitList(it)
        }
    }
}