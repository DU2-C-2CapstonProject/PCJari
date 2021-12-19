package com.example.capston.pcjari.activity.a100_main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capston.pcjari.R
import com.example.capston.pcjari.activity.A200InfoActivity
import com.example.capston.pcjari.activity.a111_address.A111AddressActivity
import com.example.capston.pcjari.activity.a111_address.A111AddressActivity.Companion.LOCATION_INFO
import com.example.capston.pcjari.activity.a111_address.address.AddressListItem
import com.example.capston.pcjari.base.BaseFragment
import com.example.capston.pcjari.databinding.F110FragmentAddressBinding
import com.example.capston.pcjari.pc.PCListAdapter
import com.example.capston.pcjari.util.Preferences
import kotlinx.android.synthetic.main.f110_fragment_address.view.*
import kotlinx.android.synthetic.main.include_pc_list.view.*

/**
 * Created by KangSeungho on 2017-10-27.
 */
class F110MainAddressFragment : BaseFragment<F110FragmentAddressBinding>() {
    override fun getLayoutResId() = R.layout.f110_fragment_address

    private val vm: F110MainAddressFragmentViewModel by viewModels()

    private val pcListAdapter : PCListAdapter = PCListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = vm

        activity?.title = getString(R.string.title_search_by_address)

        initUI()
        initObserver()
    }

    private fun initUI() {
        // 지역 선택
        binding.addressLocation.setOnClickListener {
            val intent = Intent(context, A111AddressActivity::class.java)
            startActivityForResult(intent, 0)
        }

        // 검색 입력 완료 이벤트
        binding.addressSearchName.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
                vm.getPCList()

            return@setOnEditorActionListener false
        }

        // 검색 버튼
        binding.locationSearchButton.setOnClickListener { vm.getPCList() }

        // 새로고침
        binding.pcListLayout.pcListSwipeLayout.setOnRefreshListener {
            vm.getPCList()
            binding.pcListLayout.pcListSwipeLayout.isRefreshing = false
        }

        pcListAdapter.setOnItemClickListener {
            val intent = Intent(activity, A200InfoActivity::class.java)
            intent.putExtra(A200InfoActivity.PCITEM, it)
            startActivity(intent)
        }

        pcListAdapter.setOnItemLongClickListener {
            when(!Preferences.favorite_list.contains(it.pcID)) {
                true -> {
                    Preferences.addFavorite(it.pcID)
                    Toast.makeText(context, "즐겨찾기에 추가 되었습니다.", Toast.LENGTH_SHORT).show()
                }
                false -> {
                    Preferences.removeFavorite(it.pcID)
                    Toast.makeText(context, "즐겨찾기에서 삭제 되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            pcListAdapter.notifyDataSetChanged()
        }

        binding.pcListLayout.pcListview.layoutManager = LinearLayoutManager(activity)
        binding.pcListLayout.pcListview.adapter = pcListAdapter
    }

    private fun initObserver() {
        vm.address.observe(viewLifecycleOwner) {
            vm.getPCList()
        }

        vm.pcList.observe(viewLifecycleOwner) {
            pcListAdapter.run {
                setItem(it)
                notifyDataSetChanged()
            }
        }
    }

    // intent 이후 되돌아올 때 실행되는 메소드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0 && data != null) {
                vm.setLocation(data.getSerializableExtra(LOCATION_INFO) as AddressListItem)
            }
        }
    }
}