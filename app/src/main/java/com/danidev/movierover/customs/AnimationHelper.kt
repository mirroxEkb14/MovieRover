package com.danidev.movierover.customs

import android.app.Activity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import java.util.concurrent.Executors
import kotlin.math.hypot
import kotlin.math.roundToInt

/**
 * Animation for fragment appearance with Circular Reveal Animation
 */

object AnimationHelper {

    // a variable for the circle of appearance differs from the icon of the navigation menu
    private const val MENU_ITEMS = 4

    /**
     * @param rootView  Is a container and an animation object at the same time
     * @param activity  An activity for returning a new thread to a UI thread
     * @param position  A position in navigation menu for the circle of appearance differs from the icon of the navigation menu
     */
    fun performFragmentCircularRevealAnimation(rootView: View, activity: Activity, position: Int) {

        // create a new thread
        Executors.newSingleThreadExecutor().execute {
            // In an infinite loop check when our animated view is "attached" to the screen
            while (true) {
                // when it's attached, execute the code
                if (rootView.isAttachedToWindow) {
                    // return to the main thread to perform the animation
                    activity.runOnUiThread {
                        // some maths for calculation of the animation start
                        val itemCenter = rootView.width / (MENU_ITEMS * 2)
                        val step = (itemCenter * 2) * (position - 1) + itemCenter

                        val x: Int = step
                        val y: Int = rootView.y.roundToInt() + rootView.height

                        val startRadius = 0
                        val endRadius = hypot(rootView.width.toDouble(), rootView.height.toDouble())
                        // create the animation itself
                        ViewAnimationUtils.createCircularReveal(rootView, x, y, startRadius.toFloat(), endRadius.toFloat()).apply {
                            duration = 500 // animation time
                            interpolator = AccelerateDecelerateInterpolator() // for more natural animation
                            start()
                        }
                        // set the visibility of the element
                        rootView.visibility = View.VISIBLE
                    }
                    return@execute
                }
            }
        }
    }
}