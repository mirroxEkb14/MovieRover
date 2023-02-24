package com.danidev.movierover.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.transition.AutoTransition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.danidev.movierover.App
import com.danidev.movierover.R
import com.danidev.movierover.model.Film
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailsFragment : Fragment() {

    lateinit var film: Film

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // handle the transition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = AutoTransition().setDuration(800L)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
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
        view?.findViewById<FloatingActionButton>(R.id.details_fab_favorites)?.apply {
            setImageResource (
                if (film.isInFavorites) R.drawable.ic_round_favorite
                else R.drawable.ic_round_favorite_border
            )
            setOnClickListener {
                if (!film.isInFavorites) {
                    // add a movie to RV
                    StarredFragment.favoritesFilmBase.add(film)

                    this.setImageResource(R.drawable.ic_round_favorite)
                    film.isInFavorites = true
                } else {
                    this.setImageResource(R.drawable.ic_round_favorite_border)
                    film.isInFavorites = false
                }
            }
        }

        view?.findViewById<FloatingActionButton>(R.id.details_fab_share)?.setOnClickListener {
            Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Check out this film: ${film.title} \n\n ${film.description}"
                )
                type = "text/plain"
                startActivity(Intent.createChooser(this, "Share To:"))
            }
        }
    }

    // get Film from the bundle and set the View according to Film attributes
    private fun acceptDelivery() {
        film = arguments?.get(App.BUNDLE_ITEM_KEY) as Film

        view?.findViewById<Toolbar>(R.id.details_toolbar)?.title = film.title
        view?.findViewById<ImageView>(R.id.details_poster)?.apply {
            Glide.with(requireView())
                .load(film.poster)
                .centerCrop()
                .into(this)
        }
        view?.findViewById<CoordinatorLayout>(R.id.details_fragment_root)?.transitionName = arguments?.getString(
            App.BUNDLE_TRANSITION_KEY
        )
        view?.findViewById<TextView>(R.id.details_description)?.text = film.description
    }
}