package com.danidev.movierover.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danidev.movierover.R
import com.danidev.movierover.model.Film

/**
 * ViewHolder is a wrapper around the View, in its turn, is a list element.
 * It provides a visual part (how a list element will be rendered) and
 * allows accessing the View instance.
 */
class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val title = itemView.findViewById<TextView>(R.id.title)
    private val poster = itemView.findViewById<ImageView>(R.id.poster)
    private val description = itemView.findViewById<TextView>(R.id.description)

    // put the data from Film object to our View (film_item.xml)
    fun bind(film: Film) {
        title.text = film.title
        poster.setImageResource(film.poster)
        description.text = film.description
    }
}