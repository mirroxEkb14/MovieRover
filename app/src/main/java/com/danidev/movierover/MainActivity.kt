package com.danidev.movierover

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initHomeClicks()
    }

    private fun initHomeClicks() {
        val homeButtons = listOf<Button>(
            findViewById(R.id.btn_menu),
            findViewById(R.id.btn_starred),
            findViewById(R.id.btn_watch_later),
            findViewById(R.id.btn_picks),
            findViewById(R.id.btn_settings)
        )
        homeButtons.forEach { button ->
            button.setOnClickListener {
                Toast.makeText(this, button.text, Toast.LENGTH_SHORT).show()
            }
        }
    }
}