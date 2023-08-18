package com.danidev.movierover

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

/**
 * This Activity class creates a screen where all the UI is placed using [setContentView]. It is a
 * set of actions and components combined with one theme, such as an Activity of the home screen,
 * Activity of the settings screen, favorites, etc.
 *
 * The inheritance hierarchy of the Activity class has the following chain: [android.content.Context]
 * --> [android.app.Activity] --> [MainActivity].
 */
class MainActivity : AppCompatActivity() {

    /** Value contains the object of [NavController] for managing app navigation. */
    lateinit var navController: NavController

    /**
     * Carries out the initialization when the Activity is starting.
     *
     * @param savedInstanceState is [Bundle] that contains the data, if the [android.app.Activity]
     * is being re-initialized after previously being shut down then this [Bundle].
     *
     * @see setupNavigation
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d(CREATE_LOG_MESSAGE)

        navController = Navigation.findNavController(this, R.id.fragment_placeholder)
        setupNavigation()
        activityInstance = this
    }

    /**
     * Setups a [Toolbar] for [com.danidev.movierover.fragments.DetailsFragment].
     *
     * Enables a back arrow via [androidx.appcompat.app.ActionBar.setDisplayHomeAsUpEnabled].
     */
    fun setupDetailsToolbar() {
        findViewById<Toolbar>(R.id.topAppBar).apply {
            this.navigationIcon = ContextCompat.getDrawable(
                this@MainActivity, R.drawable.ic_round_arrow_back)
            menu.findItem(R.id.search).isVisible = false
            menu.findItem(R.id.more).isVisible = false
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            this.setNavigationOnClickListener {
                navController.navigate(R.id.action_detailsFragment_to_homeFragment)
                this.navigationIcon = ContextCompat.getDrawable(
                    this@MainActivity, R.drawable.ic_round_menu)
            }
        }
    }

    /**
     * Setups [Toolbar] items and a listener for Burger Menu.
     *
     * Pressing [AlertDialog.Builder.setPositiveButton] will close the application.
     *
     * @param menu is the options menu in which the items are placed.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_toolbar, menu)

        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        topAppBar.setNavigationOnClickListener {
            AlertDialog.Builder(ContextThemeWrapper(this, R.style.MyDialog)).apply {
                setTitle(DIALOG_TITLE)
                setMessage(resources.getString(R.string.app_bar_layout_text))
                setIcon(R.drawable.ic_round_collections)
                setPositiveButton(POSITIVE_BUTTON_TEXT) { _, _ -> finish() }
                setNegativeButton(NEGATIVE_BUTTON_TEXT) { _, _ -> }
                setNeutralButton(NEUTRAL_BUTTON_TEXT) { _, _ ->
                    Toast.makeText(context, TOAST_TEXT, Toast.LENGTH_SHORT).show()
                }
                show()
            }
        }
        topAppBar.navigationIcon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_round_menu)
        return true
    }

    /**
     * Listens to the [Toolbar] items.
     *
     * @param item is the menu item that was selected.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search, R.id.more -> {
                Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    /**
     * Setups [BottomNavigationView] and sets top [Toolbar] as a support [androidx.appcompat.app.ActionBar].
     */
    private fun setupNavigation() {
        fun initBottomNavigation() {
            val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNavigation.setupWithNavController(navController)
        }
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(topAppBar)
        initBottomNavigation()
    }

    /**
     * The following **_five_** methods represent Activity Lifecycle.
     */
    override fun onStart() {
        super.onStart()
        Timber.d(START_LOG_MESSAGE)
    }
    override fun onResume() {
        super.onResume()
        Timber.d(RESUME_LOG_MESSAGE)
    }
    override fun onPause() {
        super.onPause()
        Timber.d(PAUSE_LOG_MESSAGE)
    }
    override fun onStop() {
        super.onStop()
        Timber.d(STOP_LOG_MESSAGE)
    }
    override fun onDestroy() {
        super.onDestroy()
        Timber.d(DESTROY_LOG_MESSAGE)
    }

    companion object {
        /** Value represents an activity instance used to get access to its variables from Fragments. */
        lateinit var activityInstance: MainActivity

        /** The following **_five_** constants are related to [onCreateOptionsMenu]. */
        private const val DIALOG_TITLE = "Welcome to burger menu!"
        private const val POSITIVE_BUTTON_TEXT = "Ok"
        private const val NEGATIVE_BUTTON_TEXT = "Back"
        private const val NEUTRAL_BUTTON_TEXT = "When?"
        private const val TOAST_TEXT = "One day definitely!"

        /** The following **_six_** constants represent log/debug messages to log this Activity Lifecycle. */
        private const val CREATE_LOG_MESSAGE = "onCreate()"
        private const val START_LOG_MESSAGE = "onStart()"
        private const val RESUME_LOG_MESSAGE = "onResume()"
        private const val PAUSE_LOG_MESSAGE = "onPause()"
        private const val STOP_LOG_MESSAGE = "onStop()"
        private const val DESTROY_LOG_MESSAGE = "onDestroy()"
    }
}