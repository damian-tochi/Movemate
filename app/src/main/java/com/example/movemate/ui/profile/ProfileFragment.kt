package com.example.movemate.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.movemate.BaseFragmentBinding
import com.example.movemate.R
import com.example.movemate.databinding.FragmentProfileBinding


class ProfileFragment : BaseFragmentBinding<FragmentProfileBinding>() {

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }

    override fun initialize(view: View, savedInstanceState: Bundle?) {
        val appBarContainer = requireActivity().findViewById<ViewGroup>(R.id.appBarContainer)
        appBarContainer.removeAllViews()

        val appBarView = layoutInflater.inflate(R.layout.calculate_fragment_appbar, appBarContainer, false)
        appBarContainer.addView(appBarView)

        val fragTitle = appBarView.findViewById<TextView>(R.id.title)
        fragTitle.text = "User Profile"
        bottomSlideUpTitle(view, fragTitle)

        val returnHome = appBarView.findViewById<ImageButton>(R.id.closeSearch)
        leftSlideIn(view, returnHome)

        returnHome.setOnClickListener{
            requireActivity().onBackPressed()
        }

        binding.profileAvatar.translationY = 300F
        binding.rating.translationY = 200F
        binding.projectsCount.translationY = 300F
        binding.followersCount.translationY = 400F

        controlledBottomSlideIn(
            binding.profileAvatar,
            binding.rating,
            binding.projectsCount,
            binding.followersCount, duration = 400L)

        binding.rating.animateCount(49, 2500L)
        binding.projectsCount.animateCount(100, 2500L)
        binding.followersCount.animateCount(63, 2500L)
    }

}