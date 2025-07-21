package com.example.movemate.ui.calculate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import com.example.movemate.BaseFragmentBinding
import com.example.movemate.ui.confirm.ConfirmationFragment
import com.example.movemate.R
import com.example.movemate.databinding.FragmentCalculateBinding
import com.google.android.material.button.MaterialButton


class CalculateFragment : BaseFragmentBinding<FragmentCalculateBinding>() {

    private var selectedButton: MaterialButton? = null

    private val calculateViewModel: CalculateViewModel by activityViewModels()

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentCalculateBinding {
        return FragmentCalculateBinding.inflate(inflater, container, false)
    }

    override fun initialize(view: View, savedInstanceState: Bundle?) {
        val submitBtn: Button = binding.btnCalculate
        submitBtn.setOnClickListener {
            animateButtonClick(it)
            val appBarContainer = requireActivity().findViewById<ViewGroup>(R.id.appBarContainer)
            appBarContainer.removeAllViews()

            openFragmentPage(ConfirmationFragment())

            val fromColor = requireActivity().window.statusBarColor
            val toColor = ContextCompat.getColor(requireContext(), R.color.colorFragmentBackground)

            animateStatusBarColor(fromColor, toColor, 300)
        }

        val spinner: Spinner = binding.packageTypeSpinner
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_item, calculateViewModel.options)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinner.adapter = adapter

        spinner.setSelection(0)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                calculateViewModel.options[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        val appBarContainer = requireActivity().findViewById<ViewGroup>(R.id.appBarContainer)
        appBarContainer.removeAllViews()

        val appBarView = layoutInflater.inflate(R.layout.calculate_fragment_appbar, appBarContainer, false)
        appBarContainer.addView(appBarView)

        val fragTitle = appBarView.findViewById<TextView>(R.id.title)
        bottomSlideUpTitle(view, fragTitle)
        val returnHome = appBarView.findViewById<ImageButton>(R.id.closeSearch)
        leftSlideIn(view, returnHome)
        returnHome.setOnClickListener{
            backToPrevious()
        }

        setupCategoryButtons()

        binding.categoriesTxt.translationY = 93f
        binding.categoriesDescribeTxt.translationY = 80f
        binding.packageDescribeCardView.translationY = 310f
        binding.packagingTxt.translationY = 250f
        binding.packageDescribeTxt.translationY = 200f
        binding.destCardView.translationY = 430f
        binding.destTxt.translationY = 340f

        controlledBottomSlideIn(binding.categoriesTxt, binding.categoriesDescribeTxt, binding.packageDescribeCardView, binding.packagingTxt, binding.packageDescribeTxt,
            binding.destCardView, binding.destTxt, duration = 600L)

        delayedBottomSlideIn(view, binding.btnCalculate, delay = 500)

        animateFlexboxList(binding.categoriesContainer)

    }

    private fun setupCategoryButtons() {
        val buttonIds = listOf(
            R.id.btn_documents,
            R.id.btn_glass,
            R.id.btn_liquid,
            R.id.btn_food,
            R.id.btn_electronic,
            R.id.btn_product,
            R.id.btn_others
        )

        buttonIds.forEach { id ->
            val button = binding.root.findViewById<MaterialButton>(id)
            button.setOnClickListener {

                selectedButton?.let { prevButton ->
                    setCategorySelected(prevButton, false)
                }

                selectedButton = button.apply {
                    setCategorySelected(button, true)
                }
            }
        }
    }


}