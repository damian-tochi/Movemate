package com.example.movemate.ui.shipments


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movemate.BaseFragmentBinding
import com.example.movemate.R
import com.example.movemate.databinding.FragmentShipmentBinding
import com.example.movemate.ui.component.BadgedTabLayout
import com.example.movemate.ui.component.ShipmentHistoryAdapter
import com.google.android.material.tabs.TabLayout


class ShipmentsFragment : BaseFragmentBinding<FragmentShipmentBinding>() {


    private val shipmentsViewModel: ShipmentsViewModel by activityViewModels()

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentShipmentBinding {
        return FragmentShipmentBinding.inflate(inflater, container, false)
    }

    override fun initialize(view: View, savedInstanceState: Bundle?) {
        val appBarContainer = requireActivity().findViewById<ViewGroup>(R.id.appBarContainer)
        appBarContainer.removeAllViews()

        val appBarView = layoutInflater.inflate(R.layout.shipment_fragment_appbar, appBarContainer, false)
        appBarContainer.addView(appBarView)

        val returnHome = appBarView.findViewById<ImageButton>(R.id.closeSearch)
        leftSlideIn(view, returnHome)

        val fragTitle = appBarView.findViewById<TextView>(R.id.title)
        bottomSlideUpTitle(view, fragTitle)

        returnHome.setOnClickListener{
            backToPrevious()
        }

        bottomSlideIn(view, binding.shipmentTitle)

        val tabLayout = appBarView.findViewById<BadgedTabLayout>(R.id.tabLayout)

        shipmentsViewModel.tabTitles.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }

        tabLayout.setBadge(0, 12)
        tabLayout.setBadge(1, 5)
        tabLayout.setBadge(2, 3)
        tabLayout.setBadge(3, 4)
        tabLayout.setBadge(4, 0)

        val recyclerView = binding.searchHistoryRecycler

        val adapter = ShipmentHistoryAdapter(shipmentsViewModel.historyList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter


        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                if (TextUtils.equals( tab.text, "In progress")) {
                    adapter.filter(("in-progress").toString())
                } else if (TextUtils.equals( tab.text, "Pending order")) {
                    adapter.filter(("pending").toString())
                } else {
                    adapter.filter((tab.text).toString())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

    }

}