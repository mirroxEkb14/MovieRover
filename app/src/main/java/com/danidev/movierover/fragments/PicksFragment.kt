package com.danidev.movierover.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.danidev.movierover.customs.AnimationHelper
import com.danidev.movierover.R

class PicksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animateFragmentAppearance()
    }

    private fun animateFragmentAppearance() {
        val picksFragmentRoot = requireView().findViewById<FrameLayout>(R.id.picks_fragment_root)
        AnimationHelper.performFragmentCircularRevealAnimation(
            picksFragmentRoot,
            requireActivity(),
            4
        )
    }
}