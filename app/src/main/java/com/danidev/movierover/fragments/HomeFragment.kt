package com.danidev.movierover.fragments

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
import com.danidev.movierover.customs.AnimationHelper
import com.danidev.movierover.App
import com.danidev.movierover.MainActivity
import com.danidev.movierover.R
import com.danidev.movierover.model.*
import com.danidev.movierover.recyclerview.FilmDelegateAdapter
import com.danidev.movierover.recyclerview.ItemListRecyclerAdapter
import com.danidev.movierover.recyclerview.TopSpacingItemDecoration
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.collections.ArrayList

/**
 * This class represents the fragment of Starred display.
 *
 * [Fragment] is a part of the user interface, has its own life cycle and also its own event handler.
 * Fragments are designed to solve the problem of content placement on large screens - at that time
 * began to appear tablets. Since the tablet has a much larger screen than the phone, therefore, the
 * content should fit more. Before the Fragments, this could be solved with a different layout, that
 * is, each element of the UI had to be represented according to a large screen. And if it was needed
 * to open up, for example, the list and the contents of the list, it was also needed to run two
 * Activites and configure the complex interaction between them. With the help of a Fragment, as many
 * Fragments as it is needed can be opened and easily exchanged information between them.
 */
class HomeFragment : Fragment() {

    private lateinit var filmsAdapter: ItemListRecyclerAdapter

    /** Value represents a list with all the movies contained in [RecyclerView]. */
    private lateinit var filmsDataBase: ArrayList<Item>

    /** Value represents how much time passed from the first click. */
    private var backPressedTime = 0L

    /**
     * Inflates the layout.
     *
     * About [LayoutInflater]: using [android.content.Context] class object it is possible to create
     * a graphical object from an XML markup file. Activity is inherited from [android.content.Context],
     * so [getLayoutInflater] can be called. Now, it is possible to call [LayoutInflater.inflate]
     * method, which provides a [View] object from the specified XML layout file. This
     * [LayoutInflater.inflate] method returns [View]. A `resource` argument is responsible for link to
     * an XML markup file. The second `root` argument is responsible for a parent container. Last
     * `attachToRoot` argument asks: «It is needed to add newly created [View] to the container?».
     * Eventually, there's the following formula for this method: `inflate(<R.layout.X>, <Container>, <true / false>)`,
     * where X is the name of the markup file.
     *
     * @param inflater is [LayoutInflater] object that is used to inflate this [View] in the fragment.
     * @param savedInstanceState is a mapping from [String] keys to various `Parcelable` values. If
     * non-null, this Fragment is being re-constructed from a previous saved state as given here.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    /**
     * Performs initial creation of this Fragment.
     *
     * @param savedInstanceState is the Fragment state, if this Fragment is being re-created from a
     * previous saved state.
     *
     * @see onBackPressedCallback
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedCallback()
    }

    /**
     * Initializes navigation, [RecyclerView], [SearchView] and animates the Fragment's appearance.
     *
     * @param view is [View] returned by [onCreateView].
     * @param savedInstanceState is the state if non-null. That means this Fragment is being
     * re-constructed from a previous saved state as given here.
     *
     * @see showNavigation
     * @see initRecyclerView
     * @see setupSearchView
     * @see animateFragmentAppearance
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showNavigation()
        initRecyclerView()
        setupSearchView()
        animateFragmentAppearance()
    }

    /**
     * Initializes [RecyclerView].
     */
    private fun initRecyclerView() {
        /**
         * Fills the movie database (list) with [Film]s and [Ad]s.
         */
        fun getFilms(): ArrayList<Item> {
            return arrayListOf(
                Film(1, "The Chronicles of Narnia", R.drawable.the_chronicles_of_narnia, "Four kids travel through a wardrobe to the land of Narnia and learn of their destiny to free it with the guidance of a mystical lion.", 6.9f),
                Film(2, "The Lord of the Rings: The Fellowship of the Ring", R.drawable.the_lord_of_the_rings_the_fellowship_of_the_ring, "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.", 8.8f),
                Ad(1, "Useful ad", "see our movie-bot in Telegram: https://t.me/cornapy_bot"),
                Film(3, "Requiem for a Dream", R.drawable.requiem_for_a_dream, "The drug-induced utopias of four Coney Island people are shattered when their addictions run deep.", 8.3f),
                Film(4, "Collateral Beauty", R.drawable.collateral_beauty, "Retreating from life after a tragedy, a man questions the universe by writing to Love, Time, and Death. Receiving unexpected answers, he begins to see how these things interlock and how even loss can reveal moments of meaning and beauty.", 6.7f),
                Film(5, "The Big Lebowski", R.drawable.the_big_lebowski, "Ultimate L.A. slacker Jeff \"The Dude\" Lebowski, mistaken for a millionaire of the same name, seeks restitution for a rug ruined by debt collectors, enlisting his bowling buddies for help while trying to find the millionaire's missing wife.", 8.1f),
                Ad(2, "Useful ad", "see our movie-bot in Telegram: https://t.me/cornapy_bot"),
                Film(6, "The Hateful Eight", R.drawable.the_hateful_eight, "In the dead of a Wyoming winter, a bounty hunter and his prisoner find shelter in a cabin currently inhabited by a collection of nefarious characters.", 7.8f),
                Film(7, "Carlito's Way", R.drawable.carlitos_way, "A Puerto Rican former convict, just released from prison, pledges to stay away from drugs and violence despite the pressure around him and lead on to a better life outside of N.Y.C.", 7.9f),
                Film(8, "The Silence of the Lambs", R.drawable.the_silence_of_the_lambs, "A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.", 8.6f)
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

    /**
     * Sets [SearchView.setIconified] to false for the user to be able to click everywhere in SearchView,
     * not only on the lope. After, calls [SearchView.clearFocus] for the keyboard not to pop up
     * automatically at the app start. Then, connects the listener of changes of the entered text in \
     * the search.
     */
    private fun setupSearchView() {
        view?.findViewById<SearchView>(R.id.search_view).apply {
            this?.isIconified = false
            this?.clearFocus()
            this?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                /**
                 * Is called while pressing the "search" button on the keyboard.
                 *
                 * @param query the query text that is to be submitted.
                 *
                 * @return `true` if the query has been handled by the listener, `false` to let the
                 * [SearchView] perform the default action.
                 */
                override fun onQueryTextSubmit(query: String?): Boolean { return true }

                /**
                 * Is called on every text change.
                 *
                 * If the input is empty, inserts the entire database into the adapter. Then, filters
                 * the list to find the right combinations. After, in order for everything to work
                 * correctly, it is needed both a query and a film name to normalize to lowercase.
                 * In the end, adds to the adapter.
                 *
                 * @param newText the new content of the query text field.
                 *
                 * @return `false` if the [SearchView] should perform the default action (showing any
                 * suggestions if available), `true` if the action was handled by the listener.
                 */
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isEmpty()) {
                        filmsAdapter.updateDataInefficient(filmsDataBase)
                        return true
                    }
                    val result = filmsDataBase.filter {
                        it is Film && it.title.lowercase(
                            Locale.getDefault()).contains(newText.lowercase(Locale.getDefault()))
                    }
                    filmsAdapter.updateDataInefficient(result)
                    return true
                }
            })
        }
    }

    /**
     * Creates and returns a bundle that contains information about the movie the user clicked on.
     *
     * Puts the [Film] in a 'parcel'. Then, sends `transitionName` of the current [android.widget.ImageView].
     *
     * @param film is [Film] object from [RecyclerView].
     * @param filmContainer is [Film] container.
     *
     * @return newly created [Bundle].
     */
    fun getHomeFragmentBundle(film: Film, filmContainer: CardView): Bundle {
        return Bundle().apply {
            putParcelable(App.BUNDLE_ITEM_KEY, film)
            putString(App.BUNDLE_TRANSITION_KEY, filmContainer.transitionName)
        }
    }

    /**
     * Animates this fragment's appearance.
     */
    private fun animateFragmentAppearance() {
        val homeFragmentRoot = requireView().findViewById<CoordinatorLayout>(R.id.home_fragment_root)
        AnimationHelper.performFragmentCircularRevealAnimation(
            homeFragmentRoot,
            requireActivity(),
            ANIMATION_POSITION
        )
    }

    /**
     * Implements logic of double tap to exit the app.
     */
    private fun onBackPressedCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (backPressedTime + App.TIME_INTERVAL > System.currentTimeMillis()) {
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            } else {
                Toast.makeText(requireActivity(), TOAST_TEXT, Toast.LENGTH_SHORT).show()
            }
            backPressedTime = System.currentTimeMillis()
        }
    }

    /**
     * Sets the visibility of [AppBarLayout] and [BottomNavigationView] to [View.VISIBLE] after the
     * Splash Screen.
     */
    private fun showNavigation() {
        MainActivity.activityInstance.findViewById<AppBarLayout>(R.id.app_bar_layout).visibility = View.VISIBLE
        MainActivity.activityInstance.findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
    }

    /**
     * Is called when the Fragment is no longer resumed.
     *
     * Saves the element position from [RecyclerView].
     */
    override fun onPause() {
        super.onPause()
        saveHomePositionLast = (view?.findViewById<RecyclerView>(R.id.main_recycler)?.layoutManager
                as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
    }

    companion object {
        /** Value contains the position of [RecyclerView] last saved. */
        var saveHomePositionLast = 0

        /** Value represents a position in the navigation menu for the manifestation circle to
         * diverge from the icon of the navigation menu. */
        private const val ANIMATION_POSITION = 1

        /** Value represents a text for [Toast] message that is used in [onBackPressedCallback]. */
        private const val TOAST_TEXT = "Double tap for exit"
    }
}