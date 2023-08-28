package com.danidev.movierover.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danidev.movierover.customs.AnimationHelper
import com.danidev.movierover.R
import com.danidev.movierover.model.Film
import com.danidev.movierover.model.Item
import com.danidev.movierover.recyclerview.FilmDelegateAdapter
import com.danidev.movierover.recyclerview.ItemListRecyclerAdapter
import com.danidev.movierover.recyclerview.TopSpacingItemDecoration

/**
 * This class represents the Fragment of Starred display.
 */
class StarredFragment : Fragment() {

    private lateinit var filmsAdapter: ItemListRecyclerAdapter

    /**
     * Inflates the layout.
     *
     * @param inflater is the [LayoutInflater] object that is used to inflate [View] in the Fragment.
     * @param container is the parent [View] that the fragment's UI should be attached to.
     * @param savedInstanceState is a mapping from [String] keys to various `Parcelable` values. If
     * non-null, this Fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_starred, container, false)
    }

    /**
     * Initializes the logic of [RecyclerView].
     *
     * @param view is [View] returned by [onCreateView].
     * @param savedInstanceState is a key-value pair to pass the data from one [android.app.Activity]
     * to another. If non-null, this Fragment is being re-constructed from a previous saved state as
     * given here.
     *
     * @see initFavoritesRecyclerView
     * @see animateFragmentAppearance
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFavoritesRecyclerView()
        animateFragmentAppearance()
    }

    /**
     * Initializes [RecyclerView] with a certain animation for this Fragment.
     */
    private fun initFavoritesRecyclerView() {
        view?.findViewById<RecyclerView>(R.id.favorites_recycler)?.apply {
            filmsAdapter = ItemListRecyclerAdapter(object : FilmDelegateAdapter.OnItemClickListener {

                /**
                 * Performs certain logic, when clicking on [androidx.recyclerview.widget.RecyclerView]
                 * films in [StarredFragment].
                 *
                 * @param film is [Film] that was clicked on.
                 */
                override fun click(film: Film) {}
            })

            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireActivity())

            scrollToPosition(saveStarredPositionLast)

            val anim = AnimationUtils.loadLayoutAnimation(requireActivity(),
                R.anim.recyclerview_favorites_layout_animator
            )
            layoutAnimation = anim
            scheduleLayoutAnimation()

            val decorator = TopSpacingItemDecoration(RV_MARGIN_VALUE)
            addItemDecoration(decorator)
        }
        filmsAdapter.items = favoritesFilmBase
    }

    /**
     * Animates this fragment's appearance.
     */
    private fun animateFragmentAppearance() {
        val starredFragmentRoot = requireView().findViewById<FrameLayout>(R.id.starred_fragment_root)
        println(starredFragmentRoot)
        AnimationHelper.performFragmentCircularRevealAnimation(
            starredFragmentRoot,
            requireActivity(),
            ANIMATION_POSITION
        )
    }

    /**
     * Is called when the Fragment is no longer resumed.
     *
     * Saves the element position from [RecyclerView].
     */
    override fun onPause() {
        super.onPause()
        saveStarredPositionLast = (view?.findViewById<RecyclerView>(R.id.favorites_recycler)
            ?.layoutManager as LinearLayoutManager)
            .findLastCompletelyVisibleItemPosition()
    }

    companion object {
        /** Value represents margins between the elements in [RecyclerView]. */
        private const val RV_MARGIN_VALUE = 8

        /** Value is a position in the navigation menu for the manifestation circle to diverge from
         * the icon of the navigation menu. Used in [animateFragmentAppearance]. */
        private const val ANIMATION_POSITION = 2

        /** List of movies marked as "favorites". */
        var favoritesFilmBase: ArrayList<Item> = arrayListOf()

        /** Value contains a position where to scroll to in [RecyclerView]. */
        var saveStarredPositionLast = 0
    }

    companion object {
        private const val RV_DECORATOR_SPACING = 8
        private const val STARRED_FRAGMENT_POSITION = 2

        var favoritesFilmBase: ArrayList<Item> = arrayListOf()
        var saveStarredPositionLast = 0
    }
}