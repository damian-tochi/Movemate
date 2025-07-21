package com.example.movemate.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movemate.BaseFragmentBinding
import com.example.movemate.databinding.HomeContentBinding
import com.example.movemate.ui.component.AvailableVehiclesAdapter

class HomeContentFragment : BaseFragmentBinding<HomeContentBinding>() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): HomeContentBinding {
        return HomeContentBinding.inflate(inflater, container, false)
    }

    override fun initialize(view: View, savedInstanceState: Bundle?) {
        binding.titleTxt.translationY = 300f
        binding.contentCard.translationY = 202f
        binding.availableTitle.translationY = 470f
        binding.availableVehiclesRecycler.translationY = 340f

        controlledBottomSlideIn(binding.titleTxt, binding.contentCard, binding.availableTitle, binding.availableVehiclesRecycler, duration = 400)

        val recyclerView = binding.availableVehiclesRecycler
        val adapter = AvailableVehiclesAdapter(homeViewModel.vehicleList, requireContext())
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

    }

}
