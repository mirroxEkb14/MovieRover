package com.danidev.movierover

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    // how much time passed from the first click
    private var backPressedTime = 0L

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate()")

        navController = Navigation.findNavController(this, R.id.fragment_placeholder)
        initNavigation()
    }

    private fun initNavigation() {
        fun initTopToolbar() {
            val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
            topAppBar.setNavigationOnClickListener {
                AlertDialog.Builder(ContextThemeWrapper(this, R.style.MyDialog)).apply {
                    setTitle("Welcome to burger menu!")
                    setMessage(resources.getString(R.string.app_bar_layout_text))
                    setIcon(R.drawable.ic_round_collections)
                    setPositiveButton("Ok") { _, _ ->
                        finish() // close the app
                    }
                    setNegativeButton("Back") { _, _ ->

                    }
                    setNeutralButton("When?") { _, _ ->
                        Toast.makeText(context, "One day definitely!", Toast.LENGTH_SHORT).show()
                    }
                    show()
                }
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

                    R.id.starred -> {
                        setupFavoritesToolbar()
                        navController.navigate(R.id.action_homeFragment_to_favoritesFragment)
                        true
                    }
                    R.id.watch_later, R.id.picks -> {
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

    private fun setupFavoritesToolbar() {
        findViewById<Toolbar>(R.id.topAppBar).apply {
            this.navigationIcon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_round_arrow_back)
            setSupportActionBar(this) // set the toolbar as a support action bar
            supportActionBar?.setDisplayHomeAsUpEnabled(true) // now we have a back arrow
            this.setNavigationOnClickListener {
                setupHomeToolbar()
                navController.navigate(R.id.action_favoritesFragment_to_homeFragment)
            }
        }
    }

    fun setupDetailsToolbar() {
        findViewById<Toolbar>(R.id.topAppBar).apply {
            this.navigationIcon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_round_arrow_back)
            setSupportActionBar(this) // set the toolbar as a support action bar
            supportActionBar?.setDisplayHomeAsUpEnabled(true) // now we have a back arrow
            this.setNavigationOnClickListener {
                setupHomeToolbar()
                navController.navigate(R.id.action_detailsFragment_to_homeFragment)
            }
        }
    }

    private fun setupHomeToolbar() {
        findViewById<MaterialToolbar>(R.id.topAppBar).apply {
            this.navigationIcon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_round_menu)
            inflateMenu(R.menu.top_toolbar)
            initNavigation()
        }
    }

    // double tap for exit the app
    override fun onBackPressed() {
        // check if we're in HomeFragment
        if (supportFragmentManager.backStackEntryCount == 1) {
            if (backPressedTime + App.TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed()
                finish()
            } else {
                Toast.makeText(this, "Double tap for exit", Toast.LENGTH_SHORT).show()
            }
            backPressedTime = System.currentTimeMillis()

        } else {
            super.onBackPressed()
        }
    }

    /*
        Activity Lifecycle
     */

    override fun onStart() {
        super.onStart()
        Timber.d("onStart()")
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume()")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause()")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy()")
    }
}