package com.danidev.movierover.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.danidev.movierover.customs.AnimationHelper
import com.danidev.movierover.R

/**
 * This class represents the Fragment of Watch Later display.
 */
class WatchLaterFragment : Fragment() {

    /** Value is a position in the navigation menu for the manifestation circle to diverge
     * from the icon of the navigation menu. */
    private val animationPosition = 3

    /**
     * Inflates the layout for this Fragment.
     *
     * @param inflater is the [LayoutInflater] object that is used to inflate [View] in this Fragment.
     * @param savedInstanceState is a mapping from [String] keys to various [kotlinx.android.parcel.Parcelize]
     * values. If non-null, this Fragment is being re-constructed from a previous saved state as given
     * here.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_watch_later, container, false)
    }

    /**
     * Is called after [onCreateView] has returned. Calls the method to animate the appearance of
     * this Fragment.
     *
     * @param view is [View] returned by [onCreateView].
     * @param savedInstanceState is the state if non-null. What means this fragment is being
     * re-constructed.
     *
     * @see animateFragmentAppearance
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animateFragmentAppearance()
    }

    /**
     * Animates this Fragment's appearance.
     */
    private fun animateFragmentAppearance() {
        val watchlaterFragmentRoot = requireView().findViewById<FrameLayout>(R.id.watchlater_fragment_root)
        AnimationHelper.performFragmentCircularRevealAnimation(
            watchlaterFragmentRoot,
            requireActivity(),
            animationPosition
        )
    }
}