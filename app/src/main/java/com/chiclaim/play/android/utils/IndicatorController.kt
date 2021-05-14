package com.chiclaim.play.android.utils

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import com.chiclaim.play.android.R

/**
 * ActionBarIndicator 和 DrawerLayout 交互
 *
 * @see androidx.appcompat.app.ActionBarDrawerToggle
 * @author by chiclaim@google.com
 */
class IndicatorController private constructor(private val activity: AppCompatActivity) {


    companion object {
        fun of(activity: AppCompatActivity) = IndicatorController(activity)
    }


    private var arrowDrawable: DrawerArrowDrawable? = null
    private var arrowAnimator: ValueAnimator? = null

    private fun setNavigationIcon(
        drawable: DrawerArrowDrawable,
        @StringRes desc: Int
    ) {
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val delegate: ActionBarDrawerToggle.Delegate? = activity.drawerToggleDelegate
        delegate?.setActionBarUpIndicator(
            drawable,
            desc
        )
    }


    fun setActionBarUpIndicator(showAsDrawerIndicator: Boolean) {
        var animate = true
        if (arrowDrawable == null) {
            arrowDrawable = DrawerArrowDrawable(activity).apply {
                color = activity.resources.getColor(R.color.colorOnPrimary)
            }
            animate = false
        }

        val arrow = arrowDrawable ?: return

        setNavigationIcon(
            arrowDrawable!!,
            if (showAsDrawerIndicator) R.string.nav_app_bar_open_drawer_description
            else R.string.nav_app_bar_navigate_up_description
        )
        val endValue = if (showAsDrawerIndicator) 0f else 1f
        if (animate) {
            val startValue: Float = arrow.progress
            arrowAnimator?.cancel()
            arrowAnimator = ObjectAnimator.ofFloat(
                arrow, "progress", startValue, endValue
            )
            arrowAnimator?.start()
        } else {
            arrow.progress = endValue
        }
    }

}