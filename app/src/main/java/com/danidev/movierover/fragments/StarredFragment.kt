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

class StarredFragment : Fragment() {

    private lateinit var filmsAdapter: ItemListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_starred, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFavoritesRecyclerView()
        animateFragmentAppearance()
    }

    private fun initFavoritesRecyclerView() {
        view?.findViewById<RecyclerView>(R.id.favorites_recycler)?.apply {
            filmsAdapter = ItemListRecyclerAdapter(object : FilmDelegateAdapter.OnItemClickListener {
                override fun click(film: Film) {
                    // no logic when clicking on RV films in StarredFragment
                }
            })

            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireActivity())

            scrollToPosition(saveStarredPositionLast)

            val anim = AnimationUtils.loadLayoutAnimation(requireActivity(),
                R.anim.recyclerview_favorites_layout_animator
            )
            layoutAnimation = anim
            scheduleLayoutAnimation()

            val decorator = TopSpacingItemDecoration(RV_DECORATOR_SPACING)
            addItemDecoration(decorator)
        }
        filmsAdapter.items = favoritesFilmBase
    }

    private fun animateFragmentAppearance() {
        val starredFragmentRoot = requireView().findViewById<FrameLayout>(R.id.starred_fragment_root)
        println(starredFragmentRoot)
        AnimationHelper.performFragmentCircularRevealAnimation(
            starredFragmentRoot,
            requireActivity(),
            STARRED_FRAGMENT_POSITION
        )
    }

    override fun onPause() {
        super.onPause()
        // save the element position from RV
        saveStarredPositionLast = (view?.findViewById<RecyclerView>(R.id.favorites_recycler)?.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
    }

    companion object {
        private const val RV_DECORATOR_SPACING = 8
        private const val STARRED_FRAGMENT_POSITION = 2

        var favoritesFilmBase: ArrayList<Item> = arrayListOf()
        var saveStarredPositionLast = 0
    }
}