package com.example.movemate

import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.movemate.databinding.ActivityMainBinding
import com.example.movemate.ui.component.BottomNavView
import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.FontAwesomeModule


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Iconify.with(FontAwesomeModule())

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavView = binding.navView

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_home -> showBottomNav()
                else -> hideBottomNav()

            }
        }
    }

    private fun showBottomNav() {
        val navView = binding.navView
        navView.visibility = View.VISIBLE
        navView.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(600)
            .setInterpolator(DecelerateInterpolator())
            .start()
    }

    private fun hideBottomNav() {
        val navView = binding.navView
        if (navView.visibility == View.VISIBLE) {
            Handler(Looper.getMainLooper()).postDelayed({
                navView.animate()
                    .translationY(navView.height.toFloat())
                    .alpha(0f)
                    .setDuration(350)
                    .setInterpolator(AccelerateDecelerateInterpolator())
                    .withEndAction {
                        navView.visibility = View.GONE
                    }
            }, 300)
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val view = currentFocus ?: View(this)
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    hideKeyboard()
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController

        val currentFragment = navHostFragment.childFragmentManager.primaryNavigationFragment
        val childFragmentManager = currentFragment?.childFragmentManager

        if ((childFragmentManager?.backStackEntryCount ?: 0) > 0) {
            childFragmentManager?.popBackStack()
            return
        }

        if (navController.currentDestination?.id == R.id.navigation_home) {
            super.onBackPressed()
        } else {
            navController.navigateUp()
        }
    }

}