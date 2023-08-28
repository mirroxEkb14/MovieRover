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

/**
 * This class represents the Fragment of Details display.
 */
class DetailsFragment : Fragment() {

    /** Variable contains [Film] object. */
    private lateinit var film: Film

    /**
     * Is called for the initial creation of a Fragment, after [onAttach] and before [onCreateView].
     *
     * Handles the transition.
     *
     * @param savedInstanceState is the state if the Fragment is being re-created from a previous
     * saved state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sharedElementEnterTransition = AutoTransition().setDuration(ANIMATION_DURATION)
        }
    }

    /**
     * Inflates the layout and is called after [onCreate].
     *
     * @param inflater is the [LayoutInflater] object that is used to inflate the layout.
     * @param container is the parent [View] that the fragment's UI should be attached to.
     * @param savedInstanceState is a mapping from [String] keys to various [kotlinx.android.parcel.Parcelize]
     * values. If non-null, this Fragment is being re-constructed from a previous saved state as given
     * here.
     */
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    /**
     * Is used for graphical view hierarchy and final initialization when [onCreate] is complete and
     * after [onCreateView] has returned.
     *
     * @param view is [View] returned by [onCreateView].
     * @param savedInstanceState is a mapping from [String] keys to various [kotlinx.android.parcel.Parcelize]
     * values. If non-null, this Fragment is being re-constructed from a previous saved state as given
     * here.
     *
     * @see acceptDelivery
     * @see setFloatingActionBtnSnackbar
     * @see setToolbarBackground
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        acceptDelivery()
        setFloatingActionBtnSnackbar()
        setToolbarBackground()
    }

    /**
     * Colors the [Toolbar] according to the image background.
     */
    private fun setToolbarBackground() {
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, App.currentDetailsPoster!!)
        Palette.from(bitmap).generate { palette ->
            if (palette != null){
                view?.findViewById<CollapsingToolbarLayout>(R.id.details_toolbar_layout)
                    ?.setContentScrimColor(palette.getVibrantColor(com.google.android.material.R.attr.colorAccent))
            }
        }
    }

    /**
     * Shows a [com.google.android.material.snackbar.Snackbar], when pressing on a
     * [com.google.android.material.floatingactionbutton.FloatingActionButton].
     *
     * Contains the logic of setting the desired icon at the start of the fragment
     * [FloatingActionButton.setOnClickListener]: to make it clear that the film is either in or not
     * in "favorites" - an empty and filled hearts are chosen. Then, [View.setOnClickListener]
     * processes the button itself. In the end, implements the "share" button.
     */
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
                    SHARE_BUTTON_DATA_VALUE.format(film.title, film.description)
                )
                type = DATA_MIME_TYPE
                startActivity(Intent.createChooser(this, BUTTON_TITLE))
            }
        }
    }

    /**
     * Gets [Film] from the [Bundle] and sets the [View] according to [Film] attributes.
     */
    private fun acceptDelivery() {
        film = arguments?.get(App.BUNDLE_ITEM_KEY) as Film

        view?.findViewById<Toolbar>(R.id.details_toolbar)?.title = film.title
        view?.findViewById<ImageView>(R.id.details_poster)?.apply {
            Glide.with(requireView())
                .load(film.poster)
                .centerCrop()
                .into(this)
        }
        view?.findViewById<CoordinatorLayout>(R.id.details_fragment_root)
            ?.transitionName = arguments?.getString(App.BUNDLE_TRANSITION_KEY)
        view?.findViewById<TextView>(R.id.details_description)?.text = film.description
    }

    companion object {
        /** Value represents the length of the animation used in [onCreate], in milliseconds. */
        private const val ANIMATION_DURATION = 800L

        /** The following **_three_** constants are related to [setFloatingActionBtnSnackbar]. */
        private const val SHARE_BUTTON_DATA_VALUE = "Check out this film: %s \n\n %s"
        private const val DATA_MIME_TYPE = "text/plain"
        private const val BUTTON_TITLE = "Share To:"
    }
}
