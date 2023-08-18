package com.danidev.movierover.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.danidev.movierover.R

/**
 * This class represents a Splash Screen that us a `Window` and therefore covers an [android.app.Activity].
 */
class SplashScreenFragment : Fragment() {

    /** The delay (in milliseconds) until the [Runnable] will be executed. */
    private val MILLIS_DELAY = 1000L

    /**
     * Inflates the layout for this Fragment.
     *
     * @param inflater is the [LayoutInflater] object that is used to inflate [View] in the fragment.
     * @param savedInstanceState is the state if non-null, meaning this Fragment is being
     * re-constructed from a previous saved state as given here.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash_screen, container, false)

        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
        }, MILLIS_DELAY)

        return view
    }
}