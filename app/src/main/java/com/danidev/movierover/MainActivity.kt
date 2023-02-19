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

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Timber.d("onCreate()")

        navController = Navigation.findNavController(this, R.id.fragment_placeholder)
        setupNavigation()
    }

    fun setupDetailsToolbar() {
        findViewById<Toolbar>(R.id.topAppBar).apply {
            this.navigationIcon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_round_arrow_back)
            menu.findItem(R.id.search).isVisible = false
            menu.findItem(R.id.more).isVisible = false
            supportActionBar?.setDisplayHomeAsUpEnabled(true) // now we have a back arrow
            this.setNavigationOnClickListener {
                navController.navigate(R.id.action_detailsFragment_to_homeFragment)
                this.navigationIcon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_round_menu)
            }
        }
    }

    // setup Toolbar items and a listener for burger menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_toolbar, menu)

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
        topAppBar.navigationIcon = ContextCompat.getDrawable(this@MainActivity, R.drawable.ic_round_menu)
        return true
    }

    // listener for Toolbar items
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search, R.id.more -> {
                Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
            }
        }
        return false
    }

    // setup bottom navigation and set top Toolbar as a support action bar
    private fun setupNavigation() {
        fun initBottomNavigation() {
            val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
            bottomNavigation.setupWithNavController(navController)
        }

        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(topAppBar)
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