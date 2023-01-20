package com.danidev.movierover.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.danidev.movierover.App
import com.danidev.movierover.R
import com.danidev.movierover.model.Film
import com.danidev.movierover.model.Item
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

class FilmDelegateAdapter(private val clickListener: OnItemClickListener) :
    AbsListItemAdapterDelegate<Film, Item, FilmDelegateAdapter.FilmViewHolder>() {

    /**
     * ViewHolder is a wrapper around the View, in its turn, is a list element.
     * It provides a visual part (how a list element will be rendered) and
     * allows accessing the View instance.
     */
    inner class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.title)
        private val poster = itemView.findViewById<ImageView>(R.id.poster)
        private val description = itemView.findViewById<TextView>(R.id.description)

        // put the data from Film object to our View (film_item.xml)
        fun bind(film: Film) {
            title.text = film.title
            poster.apply {
                setImageResource(film.poster)
                transitionName = App.BUNDLE_TRANSITION_KEY + App.rvItemsCounter
            }
            description.text = film.description

            App.rvItemsCounter += 1
        }
    }

    // is the item the type of Film?
    override fun isForViewType(item: Item, items: MutableList<Item>, position: Int): Boolean {
        return item is Film
    }

    override fun onCreateViewHolder(parent: ViewGroup): FilmViewHolder {
        return FilmViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false))
    }

    // bind the fields from Film to the View
    override fun onBindViewHolder(item: Film, holder: FilmViewHolder, payloads: MutableList<Any>) {
        holder.bind(item)
        holder.itemView.findViewById<CardView>(R.id.film_item_container).setOnClickListener {
            clickListener.click(item)
        }
    }

    // interface for processing the clicks
    interface OnItemClickListener {
        fun click(film: Film)
    }
}