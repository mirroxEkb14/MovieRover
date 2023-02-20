package com.danidev.movierover

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.danidev.movierover.model.*
import com.danidev.movierover.recyclerview.FilmDelegateAdapter
import com.danidev.movierover.recyclerview.ItemListRecyclerAdapter
import com.danidev.movierover.recyclerview.TopSpacingItemDecoration
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private lateinit var filmsAdapter: ItemListRecyclerAdapter
    private lateinit var filmsDataBase: ArrayList<Item>

    // how much time passed from the first click
    private var backPressedTime = 0L

    companion object {
        var saveHomePositionLast = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedCallback()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showNavigation()
        initRecyclerView()
        setupSearchView()
        animateFragmentAppearance()
    }

    private fun initRecyclerView() {
        fun getFilms(): ArrayList<Item> {
            return arrayListOf(
                Film(1, "The Chronicles of Narnia", R.drawable.the_chronicles_of_narnia, "Four kids travel through a wardrobe to the land of Narnia and learn of their destiny to free it with the guidance of a mystical lion."),
                Film(2, "The Lord of the Rings: The Fellowship of the Ring", R.drawable.the_lord_of_the_rings_the_fellowship_of_the_ring, "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron."),
                Ad(1, "Useful ad", "see our movie-bot in Telegram: https://t.me/cornapy_bot"),
                Film(3, "Requiem for a Dream", R.drawable.requiem_for_a_dream, "The drug-induced utopias of four Coney Island people are shattered when their addictions run deep."),
                Film(4, "Collateral Beauty", R.drawable.collateral_beauty, "Retreating from life after a tragedy, a man questions the universe by writing to Love, Time, and Death. Receiving unexpected answers, he begins to see how these things interlock and how even loss can reveal moments of meaning and beauty."),
                Film(5, "The Big Lebowski", R.drawable.the_big_lebowski, "Ultimate L.A. slacker Jeff \"The Dude\" Lebowski, mistaken for a millionaire of the same name, seeks restitution for a rug ruined by debt collectors, enlisting his bowling buddies for help while trying to find the millionaire's missing wife."),
                Ad(2, "Useful ad", "see our movie-bot in Telegram: https://t.me/cornapy_bot"),
                Film(6, "The Hateful Eight", R.drawable.the_hateful_eight, "In the dead of a Wyoming winter, a bounty hunter and his prisoner find shelter in a cabin currently inhabited by a collection of nefarious characters."),
                Film(7, "Carlito's Way", R.drawable.carlitos_way, "A Puerto Rican former convict, just released from prison, pledges to stay away from drugs and violence despite the pressure around him and lead on to a better life outside of N.Y.C."),
                Film(8, "The Silence of the Lambs", R.drawable.the_silence_of_the_lambs, "A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.")
            )
        }

        filmsDataBase = getFilms()
        view?.findViewById<RecyclerView>(R.id.main_recycler)?.apply {
            filmsAdapter = ItemListRecyclerAdapter(object : FilmDelegateAdapter.OnItemClickListener {
                override fun click(film: Film) {
                    App.currentDetailsPoster = film.poster

                    // setup a toolbar for DetailsFragment and launch the fragment
                    (activity as MainActivity).setupDetailsToolbar()
                    val filmContainer = findViewById<CardView>(R.id.film_item_container)
                    val extras = FragmentNavigatorExtras(filmContainer to filmContainer.transitionName)
                    val argsBundle = getHomeFragmentBundle(film, filmContainer)
                    (activity as MainActivity).navController.navigate(R.id.action_homeFragment_to_detailsFragment, argsBundle, null, extras)
                }
            })

            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireActivity())

            recycledViewPool.setMaxRecycledViews(R.id.ad_item_container, 2)
            recycledViewPool.setMaxRecycledViews(R.id.film_item_container, 8)

            scrollToPosition(saveHomePositionLast)

            PagerSnapHelper().attachToRecyclerView(this)

            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
        filmsAdapter.items = filmsDataBase
    }

    private fun setupSearchView() {
        view?.findViewById<SearchView>(R.id.search_view).apply {
            this?.isIconified = false // now the user can click everywhere in SearchView, not only on the lope
            this?.clearFocus() // the keyboard not to pop up automatically at the app start
            this?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isEmpty()) {
                        filmsAdapter.updateDataInefficient(filmsDataBase)
                        return true
                    }
                    val result = filmsDataBase.filter {
                        it is Film && it.title.lowercase(Locale.getDefault()).contains(newText.lowercase(Locale.getDefault()))
                    }
                    filmsAdapter.updateDataInefficient(result)
                    return true
                }
            })
        }
    }

    /**
     * Create a bundle that contains information about the movie user clicked on
     *
     * @param film              Film instance from RV
     * @param filmContainer     Film container
     */
    fun getHomeFragmentBundle(film: Film, filmContainer: CardView): Bundle {
        return Bundle().apply {
            putParcelable(App.BUNDLE_ITEM_KEY, film) // put the Film in a 'parcel'
            putString(App.BUNDLE_TRANSITION_KEY, filmContainer.transitionName) // send transitionName of the current imageView
        }
    }

    private fun animateFragmentAppearance() {
        val homeFragmentRoot = requireView().findViewById<CoordinatorLayout>(R.id.home_fragment_root)
        AnimationHelper.performFragmentCircularRevealAnimation(homeFragmentRoot, requireActivity(), 1)
    }

    // double tap for exit the app
    private fun onBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (backPressedTime + App.TIME_INTERVAL > System.currentTimeMillis()) {
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            } else {
                Toast.makeText(requireActivity(), "Double tap for exit", Toast.LENGTH_SHORT).show()
            }
            backPressedTime = System.currentTimeMillis()
        }
    }

    // set the visibility of Toolbar and BottomNavigation to VISIBLE after Splash Screen
    private fun showNavigation() {
        MainActivity.activityInstance.findViewById<AppBarLayout>(R.id.app_bar_layout).visibility = View.VISIBLE
        MainActivity.activityInstance.findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        // save the element position from RV
        saveHomePositionLast = (view?.findViewById<RecyclerView>(R.id.main_recycler)?.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
    }
}