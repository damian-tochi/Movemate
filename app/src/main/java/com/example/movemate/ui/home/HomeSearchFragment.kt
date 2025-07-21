package com.example.movemate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movemate.BaseFragmentBinding
import com.example.movemate.databinding.HomeSearchBinding
import com.example.movemate.ui.component.SearchHistoryAdapter


class HomeSearchFragment: BaseFragmentBinding<HomeSearchBinding>() {


    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): HomeSearchBinding {
        return HomeSearchBinding.inflate(inflater, container, false)
    }

    override fun initialize(view: View, savedInstanceState: Bundle?) {

        val recyclerView = binding.searchHistoryRecycler
        val adapter = SearchHistoryAdapter(homeViewModel.historyList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        homeViewModel.query.observe(viewLifecycleOwner) { query ->
            adapter.filter(query.orEmpty())
            recyclerView.invalidateItemDecorations()

        }
    }
}