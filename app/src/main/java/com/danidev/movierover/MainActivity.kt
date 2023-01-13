package com.danidev.movierover

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavigation()
        setContextualTopAppbar()
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

        val posterContainer = findViewById<GridLayout>(R.id.center_bar)
        posterContainer.children.forEach { child ->
            child.setOnLongClickListener {
                when (actionMode == null) {
                    true -> {
                        actionMode = startActionMode(object: ActionMode.Callback {
                            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                                when (item?.itemId) {
                                    R.id.ios_share, R.id.file_copy -> Toast.makeText(this@MainActivity, item.title, Toast.LENGTH_SHORT).show()
                                }
                                return true
                            }

                            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                                val inflater = mode?.menuInflater
                                inflater?.inflate(R.menu.contextual_toolbar_poster, menu)
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