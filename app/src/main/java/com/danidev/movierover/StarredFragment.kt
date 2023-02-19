package com.danidev.movierover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danidev.movierover.model.Film
import com.danidev.movierover.model.Item
import com.danidev.movierover.recyclerview.FilmDelegateAdapter
import com.danidev.movierover.recyclerview.ItemListRecyclerAdapter
import com.danidev.movierover.recyclerview.TopSpacingItemDecoration

class StarredFragment : Fragment() {

    private lateinit var filmsAdapter: ItemListRecyclerAdapter

    companion object {
        var favoritesFilmBase: ArrayList<Item> = arrayListOf()
        var saveStarredPositionLast = 0
    }

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

            val anim = AnimationUtils.loadLayoutAnimation(requireActivity(), R.anim.recyclerview_favorites_layout_animator)
            layoutAnimation = anim
            scheduleLayoutAnimation()

            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
        filmsAdapter.items = favoritesFilmBase
    }

    override fun onPause() {
        super.onPause()
        // save the element position from RV
        saveStarredPositionLast = (view?.findViewById<RecyclerView>(R.id.favorites_recycler)?.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
    }
}