package com.danidev.movierover

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danidev.movierover.model.Ad
import com.danidev.movierover.model.Film
import com.danidev.movierover.model.Item
import com.danidev.movierover.recyclerview.FilmDelegateAdapter
import com.danidev.movierover.recyclerview.ItemListRecyclerAdapter
import com.danidev.movierover.recyclerview.TopSpacingItemDecoration
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var filmsAdapter: ItemListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavigation()
        setContextualTopAppbar()
        initRecyclerView()
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

        val filmsDataBase = getFilms()
        findViewById<RecyclerView>(R.id.main_recycler).apply {
            filmsAdapter = ItemListRecyclerAdapter(object : FilmDelegateAdapter.OnItemClickListener {
                override fun click(film: Film) {
                    // launch a new activity
                    val bundle = Bundle()
                    bundle.putParcelable(DetailsActivity.BUNDLE_KEY, film)
                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            })

            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)

            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)

            val anim = AnimationUtils.loadLayoutAnimation(this@MainActivity, R.anim.recyclerview_layout_animator)
            layoutAnimation = anim
            scheduleLayoutAnimation()
        }
        filmsAdapter.items = filmsDataBase
    }

    private fun setContextualTopAppbar() {
        var actionMode: ActionMode? = null

        val disclaimerText = findViewById<TextView>(R.id.disclaimer)
        disclaimerText.setOnLongClickListener {
            when (actionMode == null) {
                true -> {
                    actionMode = startActionMode(object: ActionMode.Callback {
                        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                            when (item?.itemId) {
                                R.id.create, R.id.copy, R.id.share -> Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show()
                            }
                            return true
                        }

                        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                            val inflater = mode?.menuInflater
                            inflater?.inflate(R.menu.contextual_top_toolbar, menu)
                            mode?.title = "Select Option"
                            return true
                        }

                        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                            return false
                        }

                        override fun onDestroyActionMode(mode: ActionMode?) {
                            actionMode = null
                        }
                    })
                    return@setOnLongClickListener false
                }
                false -> return@setOnLongClickListener true
            }
        }
    }

    private fun initNavigation() {
        fun initTopToolbar() {
            val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
            topAppBar.setNavigationOnClickListener {
                Toast.makeText(this, resources.getString(R.string.app_bar_layout_text), Toast.LENGTH_SHORT).show()
            }
            topAppBar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.search, R.id.more -> {
                        Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }
        fun initBottomNavigation() {
            val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNavigation.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.starred, R.id.watch_later, R.id.picks -> {
                        Toast.makeText(this, it.title, Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }

        initTopToolbar()
        initBottomNavigation()
    }
}