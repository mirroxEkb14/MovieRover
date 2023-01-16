package com.danidev.movierover

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import com.danidev.movierover.model.Film

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        acceptDelivery()
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
