package com.danidev.movierover

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.danidev.movierover.model.Film
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        acceptDelivery()
        setFloatingActionBtnSnackbar()
        setToolbarBackground()
    }

    // toolbar coloring according to the image background
    private fun setToolbarBackground() {
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, Film.currentDetailsPoster!!)
        Palette.from(bitmap).generate { palette ->
            if (palette != null){
                findViewById<CollapsingToolbarLayout>(R.id.details_toolbar_layout).setContentScrimColor(palette.getVibrantColor(
                    com.google.android.material.R.attr.colorAccent))
            }
        }
    }

    // show a snackbar when pressing on a floating action button
    private fun setFloatingActionBtnSnackbar() {
        findViewById<FloatingActionButton>(R.id.details_fab_share).setOnClickListener {
            val snackbar = Snackbar.make(findViewById<CoordinatorLayout>(R.id.details_main_layout), "Snackbar!", Snackbar.LENGTH_SHORT)
            snackbar.setAction("Action") {
                Toast.makeText(this, "Toast!", Toast.LENGTH_SHORT).show()
            }
            snackbar.show()
        }
    }

    // get Film from the bundle and set the View according to Film attributes
    private fun acceptDelivery() {
        val film = intent.extras?.get(BUNDLE_KEY) as Film

        findViewById<Toolbar>(R.id.details_toolbar).title = film.title
        findViewById<AppCompatImageView>(R.id.details_poster).setImageResource(film.poster)
        findViewById<TextView>(R.id.details_description).text = film.description
    }

    companion object {
        const val BUNDLE_KEY = "film"
    }
}
