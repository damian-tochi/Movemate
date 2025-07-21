package com.example.movemate.ui.confirm


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.movemate.BaseFragmentBinding
import com.example.movemate.R
import com.example.movemate.databinding.FragmentConfirmationBinding


class ConfirmationFragment : BaseFragmentBinding<FragmentConfirmationBinding>() {

    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentConfirmationBinding {
        return FragmentConfirmationBinding.inflate(inflater, container, false)
    }

    override fun initialize(view: View, savedInstanceState: Bundle?) {
        val image = binding.explosiveImage
        val text = binding.explosiveText
        val button = binding.btnReturnHome

        popIn(image, text, duration = 500L)

        bottomSlideIn(view, button)

        button.setOnClickListener {
            backToRoot()
        }

        val amountTextView = view.findViewById<TextView>(R.id.amountTextView)
        amountTextView.animateSum(1388, 2500L)

    }

    override fun onDestroy() {
        val fromColor = requireActivity().window.statusBarColor
        val toColor = ContextCompat.getColor(requireContext(), R.color.colorPrimary)
        animateStatusBarColor(fromColor, toColor, 30)

        super.onDestroy()
    }

}