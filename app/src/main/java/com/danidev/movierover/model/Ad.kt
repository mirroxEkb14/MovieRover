package com.danidev.movierover.model

/**
 * This class represents an [Ad] object that is stored in one [androidx.recyclerview.widget.RecyclerView]
 * with [Film] objects.
 */
class Ad(override val id: Int, val title: String, val content: String) : Item