package com.danidev.movierover.customs

import android.app.Activity
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import java.util.concurrent.Executors
import kotlin.math.hypot
import kotlin.math.roundToInt

/**
 * Animation for fragment appearance with Circular Reveal Animation.
 */
object AnimationHelper {

    /** Value is to allow the manifestation circle to diverge from the navigation menu icon */
    private const val MENU_ITEMS = 4

    /** The following **_four_** constants are related to [performFragmentCircularRevealAnimation]. */
    private const val ITEM_AMOUNT_MULTIPLIER = 2
    private const val ITEM_CENTER_MULTIPLIER = 2
    private const val POSITION_SUBTRAHEND = 1
    private const val START_RADIUS_DEFAULT_VALUE = 0

    /** Value represents the length of the animation, in milliseconds. */
    private const val ANIMATION_DURATION = 500L

    /**
     * Performs the animation.
     *
     * Creates a new thread. Then, in an infinite loop check when this animated [View] is "attached"
     * to the screen. When it's attached, executes the code. After, returns to the main thread to
     * perform the animation. Afterwards, some maths for calculation of the animation start occurs.
     * Subsequently, creates the animation itself. In the end, sets the visibility of the element.
     *
     * @param rootView is a container and an animation object at the same time.
     * @param activity is an activity for returning a new thread to a UI thread.
     * @param position is a position in the navigation menu for the manifestation circle to diverge
     * from the icon of the navigation menu.
     */
    fun performFragmentCircularRevealAnimation(rootView: View, activity: Activity, position: Int) {
        Executors.newSingleThreadExecutor().execute {
            while (true) {
                if (rootView.isAttachedToWindow) {
                    activity.runOnUiThread {
                        val itemCenter = rootView.width / (MENU_ITEMS * ITEM_AMOUNT_MULTIPLIER)
                        val step = (itemCenter * ITEM_CENTER_MULTIPLIER) *
                                (position - POSITION_SUBTRAHEND) + itemCenter

                        val x: Int = step
                        val y: Int = rootView.y.roundToInt() + rootView.height

                        val startRadius = START_RADIUS_DEFAULT_VALUE
                        val endRadius = hypot(rootView.width.toDouble(), rootView.height.toDouble())

                        ViewAnimationUtils.createCircularReveal(rootView, x, y, startRadius.toFloat(),
                            endRadius.toFloat()).apply {
                            duration = ANIMATION_DURATION
                            interpolator = AccelerateDecelerateInterpolator()
                            start()
                        }
                        rootView.visibility = View.VISIBLE
                    }
                    return@execute
                }
            }
        }
    }
}