package com.danidev.movierover

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.danidev.movierover.model.Film
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : Fragment() {

    lateinit var film: Film

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // handle the transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        acceptDelivery()
        setFloatingActionBtnSnackbar()
        setToolbarBackground()
    }

    // toolbar coloring according to the image background
    private fun setToolbarBackground() {
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, App.currentDetailsPoster!!)
        Palette.from(bitmap).generate { palette ->
            if (palette != null){
                view?.findViewById<CollapsingToolbarLayout>(R.id.details_toolbar_layout)?.setContentScrimColor(palette.getVibrantColor(
                    com.google.android.material.R.attr.colorAccent))
            }
        }
    }

    // show a snackbar when pressing on a floating action button
    private fun setFloatingActionBtnSnackbar() {
        view?.findViewById<FloatingActionButton>(R.id.details_fab_share)?.setOnClickListener {
            val snackbar = Snackbar.make(requireView().findViewById<CoordinatorLayout>(R.id.details_main_layout), "Snackbar!", Snackbar.LENGTH_SHORT)
            snackbar.setAction("Action") {
                Toast.makeText(requireContext(), "Toast!", Toast.LENGTH_SHORT).show()
            }
            snackbar.show()
        }
    }

    // get Film from the bundle and set the View according to Film attributes
    private fun acceptDelivery() {
        film = arguments?.get(App.BUNDLE_ITEM_KEY) as Film

        view?.findViewById<Toolbar>(R.id.details_toolbar)?.title = film.title
        view?.findViewById<ImageView>(R.id.details_poster)?.apply {
            setImageResource(film.poster)
            transitionName = arguments?.getString(App.BUNDLE_TRANSITION_KEY)
        }
        view?.findViewById<TextView>(R.id.details_description)?.text = film.description
    }
}
