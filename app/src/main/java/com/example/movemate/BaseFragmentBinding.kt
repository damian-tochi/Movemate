package com.example.movemate


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.view.animation.ScaleAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.transition.AutoTransition
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.viewbinding.ViewBinding
import com.example.movemate.databinding.FragmentCalculateBinding
import com.example.movemate.ui.confirm.ConfirmationFragment
import com.google.android.flexbox.FlexboxLayout


abstract class BaseFragmentBinding<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindView(inflater, container)
        return _binding!!.root
    }

    protected abstract fun bindView(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize(view, savedInstanceState)
    }

    protected abstract fun initialize(view: View, savedInstanceState: Bundle?)


    fun controlledBottomSlideIn(vararg views: View, duration: Long) {
        views.forEach { v ->
            v.animate()
                .translationY(0f)
                .setDuration(duration)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }
    }

    fun popIn(vararg views: View, duration: Long) {
        views.forEach { v ->
            v.scaleX = 0f
            v.scaleY = 0f
            v.alpha = 0f
            v.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setInterpolator(OvershootInterpolator())
                .setDuration(duration)
                .start()
        }
    }

    fun bottomSlideIn(view: View, item: View) {
        item.visibility = View.INVISIBLE
        item.translationY = 300f
        view.post {
            item.visibility = View.VISIBLE
            item.animate()
                .translationY(0f)
                .setDuration(500L)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }
    }

    fun delayedBottomSlideIn(view: View, item: View, delay: Long = 0L) {
        item.visibility = View.INVISIBLE
        item.translationY = 300f
        Handler(Looper.getMainLooper()).postDelayed({
            view.post {
                item.visibility = View.VISIBLE
                item.animate()
                    .translationY(0f)
                    .setDuration(500L)
                    .setInterpolator(DecelerateInterpolator())
                    .start()
            }
        }, delay)
    }

    fun bottomSlideUpTitle(view: View, item: View) {
        item.visibility = View.INVISIBLE
        item.translationY = 30f
        view.post {
            item.visibility = View.VISIBLE
            item.animate()
                .translationY(0f)
                .setDuration(700L)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }
    }

    fun leftSlideIn(view: View, item: View) {
        item.visibility = View.INVISIBLE
        item.translationX = -item.width.toFloat() - 100f
        view.post {
            item.visibility = View.VISIBLE
            item.animate()
                .translationX(0f)
                .setDuration(500L)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }
    }

    fun TextView.animateSum(targetAmount: Int, duration: Long = 2500L) {
        val animator = ValueAnimator.ofInt(0, targetAmount)
        animator.duration = duration
        animator.interpolator = DecelerateInterpolator()

        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            this.text = "$${String.format("%,d", value)} USD"
        }

        animator.start()
    }

    fun TextView.animateCount(targetAmount: Int, duration: Long = 2500L) {
        val animator = ValueAnimator.ofInt(0, targetAmount)
        animator.duration = duration
        animator.interpolator = DecelerateInterpolator()

        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            this.text = "${String.format("%,d", value)}"
        }

        animator.start()
    }

    fun animateButtonClick(view: View) {
        val scaleDown = ScaleAnimation(
            1.0f, 0.95f,
            1.0f, 0.95f,
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot X
            Animation.RELATIVE_TO_SELF, 0.5f  // Pivot Y
        )
        scaleDown.duration = 80
        scaleDown.fillAfter = true

        val scaleUp = ScaleAnimation(
            0.95f, 1.0f,
            0.95f, 1.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        scaleUp.duration = 80
        scaleUp.fillAfter = true
        scaleUp.startOffset = 80

        val animation = AnimationSet(true)
        animation.addAnimation(scaleDown)
        animation.addAnimation(scaleUp)

        view.startAnimation(animation)
    }

    fun animateFlexboxList(view: FlexboxLayout) {
        val delay = 60L

        for (i in 0 until view.childCount) {
            val child = view.getChildAt(i)
            child.translationX = 300f
            child.alpha = 0f

            child.animate()
                .translationX(0f)
                .alpha(1f)
                .setStartDelay(i * delay)
                .setDuration(300)
                .setInterpolator(DecelerateInterpolator())
                .start()
        }
    }

    fun delayedTransition(view: View, duration: Long = 300L, delay: Long = 200L) {
        view.postDelayed({
            val transition = AutoTransition()
            transition.duration = duration
            TransitionManager.beginDelayedTransition(view as ViewGroup, transition)
        }, delay)
    }

    fun delayedVisibility(view: View, duration: Long = 50L) {
        val transition = AutoTransition()
        transition.duration = duration
        TransitionManager.beginDelayedTransition(view as ViewGroup, transition)
        view.visibility = View.VISIBLE
        view.alpha = 1f
    }

    fun slideDown(view: View, duration: Long = 400L) {
        view.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(duration)
            .start()
    }

    fun slideUp(view: View, duration: Long = 400L) {
        view.alpha = 0f
        view.translationY = 50f
        view.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(duration)
            .start()
    }

    fun setCategorySelected(button: Button, selected: Boolean) {
        val parent = button.parent as? ViewGroup ?: return
        TransitionManager.beginDelayedTransition(parent)

        button.isSelected = selected

        if (selected) {
            val checkIcon = ContextCompat.getDrawable(context, R.drawable.ic_check)
            button.setCompoundDrawablesWithIntrinsicBounds(checkIcon, null, null, null)
        } else {
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }
    }

    fun animateStatusBarColor(fromColor: Int, toColor: Int, duration: Long = 300L) {
        val window = requireActivity().window
        val colorAnimation = ValueAnimator.ofArgb(fromColor, toColor)
        colorAnimation.duration = duration

        colorAnimation.addUpdateListener { animator ->
            window.statusBarColor = animator.animatedValue as Int
        }
        colorAnimation.start()
    }

    fun openFragmentPage(fragment: Fragment) {
        fragment.enterTransition = Fade()
        fragment.exitTransition = Fade()

        requireActivity().supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.nav_host_fragment_activity_main, fragment)
            .addToBackStack(null)
            .commit()

    }

    fun backToPrevious() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    fun backToRoot() {
        requireActivity().supportFragmentManager.popBackStackImmediate(
            null,
            android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
