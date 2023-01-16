package com.danidev.movierover

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.danidev.movierover.model.Film
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

class MainActivity : AppCompatActivity() {

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

    fun launchDetailsFragment(film: Film) {
        val bundle = Bundle() // create a 'parcel'
        bundle.putParcelable(App.BUNDLE_KEY, film) // put the Film in a 'parcel'
        val fragment = DetailsFragment() // put DetailsFragment in variable
        fragment.arguments = bundle // attach the 'parcel' to Fragment

        // launch the Fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
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