package com.danidev.movierover

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.danidev.movierover.model.Film
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    // how much time passed from the first click
    private var backPressedTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate()")

        initNavigation()
        launchHomeFragment()
    }

    private fun launchHomeFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_placeholder, HomeFragment())
            .addToBackStack(null)
            .commit()
    }

    fun launchDetailsFragment(film: Film, imageView: ImageView) {
        val bundle = Bundle() // create a 'parcel'
        bundle.putParcelable(App.BUNDLE_ITEM_KEY, film) // put the Film in a 'parcel'
        bundle.putString(App.BUNDLE_TRANSITION_KEY, imageView.transitionName) // send transitionName of the current imageView
        val fragment = DetailsFragment() // put DetailsFragment in variable
        fragment.arguments = bundle // attach the 'parcel' to Fragment

        // launch the Fragment
        supportFragmentManager
            .beginTransaction()
            .addSharedElement(imageView, imageView.transitionName)
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
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