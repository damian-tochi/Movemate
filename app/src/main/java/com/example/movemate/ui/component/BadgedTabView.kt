package com.example.movemate.ui.component

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.TextView
import com.example.movemate.R
import com.google.android.material.tabs.TabLayout


class BadgedTabLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : TabLayout(context, attrs) {

    private val selectedTextColor = context.getColor(R.color.white)
    private val unselectedTextColor = context.getColor(R.color.colorTextHint)
    private val selectedBadgeColor = R.drawable.badge_background_selected
    private val unselectedBadgeColor = R.drawable.badge_background

    init {
        setSelectedTabIndicatorColor(context.getColor(R.color.colorOrange))
        setBackgroundColor(context.getColor(R.color.colorPrimary))
        setTabTextColors(unselectedTextColor, selectedTextColor)
    }

    fun setBadge(position: Int, count: Int) {
        val tab = getTabAt(position) ?: return

        if (tab.customView == null) {
            val container = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity = Gravity.CENTER
            }

            val title = TextView(context).apply {
                text = tab.text
                setTextColor(if (tab.isSelected) selectedTextColor else unselectedTextColor)
                setPadding(8, 0, 8, 0)
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            }

            val badge = TextView(context).apply {
                tag = "badge"
                setBackgroundResource(unselectedBadgeColor)
                setTextColor(Color.WHITE)
                setTextSize(10f)
                visibility = View.GONE
            }

            container.addView(title)
            container.addView(badge)

            val slideInFromRight = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f
            ).apply {
                duration = 600
                interpolator = DecelerateInterpolator()
            }

            container.startAnimation(slideInFromRight)

            tab.customView = container
        }

        val badge = tab.customView?.findViewWithTag<TextView>("badge") ?: return
        badge.text = count.toString()
        badge.visibility = if (count > 0) View.VISIBLE else View.GONE
        badge.setBackgroundResource(if (tab.isSelected) selectedBadgeColor else unselectedBadgeColor)
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                tab?.customView?.let {
                    val title = (it as ViewGroup).getChildAt(0) as TextView
                    val badge = it.getChildAt(1) as TextView
                    title.setTextColor(selectedTextColor)
                    badge.setBackgroundResource(selectedBadgeColor)
                }
            }

            override fun onTabUnselected(tab: Tab?) {
                tab?.customView?.let {
                    val title = (it as ViewGroup).getChildAt(0) as TextView
                    val badge = it.getChildAt(1) as TextView
                    title.setTextColor(unselectedTextColor)
                    badge.setBackgroundResource(unselectedBadgeColor)
                }
            }

            override fun onTabReselected(tab: Tab?) {}
        })
    }
}
