package com.example.movemate.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.doOnLayout
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.example.movemate.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class BottomNavView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val navView: BottomNavigationView
    private val indicator: View

    private var currentIndex = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.bottom_navigation_view_item, this, true)
        navView = findViewById(R.id.bottomNav)
        indicator = findViewById(R.id.indicator)

        navView.setOnItemSelectedListener { item ->
            val index = navView.menu.findItemIndex(item.itemId)
            if (index != -1) animateIndicatorTo(index)
            itemSelectedListener?.invoke(item.itemId)
            true
        }

        navView.doOnLayout {
            animateIndicatorTo(navView.menu.findItemIndex(navView.selectedItemId), animate = false)
        }
    }

    private var itemSelectedListener: ((Int) -> Unit)? = null

    fun setOnItemSelectedListener(listener: (Int) -> Unit) {
        itemSelectedListener = listener
    }

    fun setMenu(menuResId: Int) {
        navView.menu.clear()
        navView.inflateMenu(menuResId)
    }

    fun setSelectedItem(itemId: Int) {
        navView.selectedItemId = itemId
        animateIndicatorTo(navView.menu.findItemIndex(itemId))
    }

    fun setupWithNavController(navController: NavController) {
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val index = navView.menu.findItemIndex(destination.id)
            if (index != -1) animateIndicatorTo(index)
        }
    }

    private fun animateIndicatorTo(index: Int, animate: Boolean = true) {
        if (index == -1 || navView.width == 0) return

        val itemWidth = navView.width / navView.menu.size()
        val targetX = itemWidth * index.toFloat()

        if (animate) {
            indicator.animate().translationX(targetX).setDuration(250).start()
        } else {
            indicator.translationX = targetX
        }

        currentIndex = index
    }

    private fun android.view.Menu.findItemIndex(itemId: Int): Int {
        for (i in 0 until size()) {
            if (getItem(i).itemId == itemId) return i
        }
        return -1
    }

    private fun android.view.Menu.size(): Int = this.size()
}