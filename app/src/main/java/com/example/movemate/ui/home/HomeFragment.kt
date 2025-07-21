package com.example.movemate.ui.home


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.movemate.BaseFragmentBinding
import com.example.movemate.R
import com.example.movemate.databinding.FragmentHomeBinding
import com.google.android.material.appbar.AppBarLayout


class HomeFragment : BaseFragmentBinding<FragmentHomeBinding>() {

    private lateinit var toolbar: Toolbar

    private lateinit var closeSearch: ImageButton

    private lateinit var appBar: AppBarLayout

    private val homeViewModel: HomeViewModel by activityViewModels()


    override fun bindView(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initialize(view: View, savedInstanceState: Bundle?) {
        val appBarContainer = requireActivity().findViewById<ViewGroup>(R.id.appBarContainer)
        appBarContainer.removeAllViews()

        val appBarView =
            layoutInflater.inflate(R.layout.home_fragment_appbar, appBarContainer, false)
        appBarContainer.addView(appBarView)

        toolbar = appBarView.findViewById(R.id.toolbar)
        closeSearch = appBarView.findViewById(R.id.closeSearch)
        appBar = appBarView.findViewById(R.id.appbar)

        closeSearch.visibility = View.GONE

        if (childFragmentManager.fragments.isEmpty()) {
            showHomeFragment()
        }

        childFragmentManager.addOnBackStackChangedListener {
            if (childFragmentManager.backStackEntryCount == 0) {
                showHomeFragment()
            }
        }

        val searchInput: EditText = appBarView.findViewById(R.id.searchInput)
        searchInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showSearchFragment()
            } else {
                searchInput.setText("")
            }
        }

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                homeViewModel.query.value = query
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        closeSearch.setOnClickListener {
            searchInput.clearFocus()
            showHomeFragment()
        }

        animateHomeEntrance()

        childFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {

            override fun onFragmentViewCreated(
                fm: FragmentManager,
                fragment: Fragment,
                v: View,
                savedInstanceState: Bundle?
            ) {
                if (fragment is HomeSearchFragment) {
                    searchFragmentVisible()
                }
            }

            override fun onFragmentViewDestroyed(fm: FragmentManager, fragment: Fragment) {
                if (fragment is HomeSearchFragment) {
                    searchInput.clearFocus()
                    searchFragmentRemoved()
                }
            }
        }, true)

    }

    private fun searchFragmentVisible() {
        searchFragmentVisibleExtra()
    }

    private fun searchFragmentRemoved() {
        slideDown(appBar,400)
        searchFragmentRemovedExtra()
    }

    private fun searchFragmentVisibleExtra() {
        toolbar.animate()
            .translationY(-10f)
            .alpha(0f)
            .setDuration(300)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                slideUp(appBar,300)
                toolbar.visibility = View.GONE
                closeSearch.alpha = 0f
                closeSearch.visibility = View.VISIBLE
                closeSearch.translationX = -50f
                closeSearch.animate().alpha(1f).translationX(1f).setDuration(500).start()
            }.start()
    }

    private fun searchFragmentRemovedExtra() {
        closeSearch.animate()
            .alpha(0f)
            .setDuration(50)
            .translationX(-50f)
            .setInterpolator(AccelerateDecelerateInterpolator())
            .withEndAction {
                closeSearch.visibility = View.GONE
                toolbar.alpha = 0f
                toolbar.visibility = View.VISIBLE
                toolbar.animate().translationY(0f).alpha(1f).setInterpolator(AccelerateDecelerateInterpolator()).setDuration(100).start()
            }.start()
    }


    private fun animateHomeEntrance() {
        val duration = 500L

        appBar.apply {
            visibility = View.VISIBLE
            translationY = -height.toFloat()
            animate()
                .translationY(0f)
                .setDuration(duration)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }

    }

    private fun clearChildBackStack() {
        val count = childFragmentManager.backStackEntryCount
        for (i in 0 until count) {
            childFragmentManager.popBackStackImmediate()
        }
    }

    private fun showHomeFragment() {
        hideKeyboard()
        val tag = HomeContentFragment::class.java.name
        val currentFragment = childFragmentManager.findFragmentById(R.id.home_container)

        if (currentFragment == null || currentFragment::class.java.name != tag) {
            clearChildBackStack()
            childFragmentManager.beginTransaction()
                .replace(R.id.home_container, HomeContentFragment(), tag)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun showSearchFragment() {
        val tag = HomeSearchFragment::class.java.name
        val currentFragment = childFragmentManager.findFragmentById(R.id.home_container)

        if (currentFragment == null || currentFragment::class.java.name != tag) {
            clearChildBackStack()
            childFragmentManager.beginTransaction()
                .replace(R.id.home_container, HomeSearchFragment(), tag)
                .addToBackStack(null)
                .commit()
        }
    }


}
