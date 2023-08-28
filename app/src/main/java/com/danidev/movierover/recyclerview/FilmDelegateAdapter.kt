package com.danidev.movierover.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danidev.movierover.App
import com.danidev.movierover.R
import com.danidev.movierover.customs.RatingDonutView
import com.danidev.movierover.model.Film
import com.danidev.movierover.model.Item
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

/**
 * This delegate class represents a [com.hannesdorfmann.adapterdelegates4.AdapterDelegate] object
 * that displays the data of [RecyclerView]. It adapts user data for displaying it in the list. Here
 * the model of data, the way of their rendering and the way of transferring data from the list to
 * the representation.
 *
 * @param clickListener is a listener for processing clicks from the Fragment classes.
 */
class FilmDelegateAdapter(private val clickListener: OnItemClickListener) :
    AbsListItemAdapterDelegate<Film, Item, FilmDelegateAdapter.FilmViewHolder>() {

    /**
     * This inner class represents a [RecyclerView.ViewHolder] that is a wrapper around [View], in
     * its turn, is a list element. It provides a visual part (how a list element will be rendered)
     * and allows accessing a [View] instance.
     *
     * Since [RecyclerView.ViewHolder] is an abstract class, it is needed to create an inner class
     * inside the [RecyclerView.Adapter] that inherits the abstract [RecyclerView.ViewHolder].
     *
     * @param itemView is the root [View] of the inflated XML file.
     */
    inner class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /** Here the [View] objects from the layout are bound to the variables. */
        private val title = itemView.findViewById<TextView>(R.id.title)
        private val poster = itemView.findViewById<ImageView>(R.id.poster)
        private val description = itemView.findViewById<TextView>(R.id.description)
        private val filmContainer = itemView.findViewById<CardView>(R.id.film_item_container)
        private val ratingDonut = itemView.findViewById<RatingDonutView>(R.id.rating_donut)

        /**
         * Puts the data from the [Film] object to the [View] objects, previously bound from the layout.
         *
         * Sets the title. Then, sets the poster: loads the resource itself, centers the image, sets
         * [ImageView] where the image will load into. After that, sets the description. In the end,
         * sets the rating.
         *
         * @param film is a [Film] object.
         */
        fun bind(film: Film) {
            title.text = film.title
            poster.apply {
                Glide.with(itemView)
                    .load(film.poster)
                    .centerCrop()
                    .into(this)
            }
            filmContainer.transitionName = App.BUNDLE_TRANSITION_KEY + App.rvItemsCounter
            description.text = film.description
            ratingDonut.setProgress((film.rating * RATING_MULTIPLIER).toInt())

            App.rvItemsCounter += ITEM_COUNTER_SUMMAND
        }
    }

    /**
     * Checks either the passed [item] is a [Film] object or not.
     *
     * @param item is an [Item] from the list at the given position that is to be checked.
     * @param items are items from the adapter dataset (list).
     * @param position is items position from the dataset (lsit).
     *
     * @return `true`, if the passed [item] is a [Film] object; `false` otherwise.
     */
    override fun isForViewType(item: Item, items: MutableList<Item>, position: Int): Boolean {
        return item is Film
    }

    /**
     * Creates and returns a [RecyclerView.ViewHolder] object.
     *
     * @param parent is the [ViewGroup] parent of the given datasource.
     *
     * @return [RecyclerView.ViewHolder] object.
     */
    override fun onCreateViewHolder(parent: ViewGroup): FilmViewHolder {
        return FilmViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.film_item, parent, false))
    }

    /**
     * Binds the fields from [Film] to [View].
     *
     * This method is responsible for transferring data from model to representation.
     *
     * @param item is a data item, a [Film] object in this case.
     * @param holder is this custom [FilmViewHolder].
     * @param payloads are the payloads used to update a certain list element.
     */
    override fun onBindViewHolder(item: Film, holder: FilmViewHolder, payloads: MutableList<Any>) {
        holder.bind(item)
        holder.itemView.findViewById<CardView>(R.id.film_item_container).setOnClickListener {
            clickListener.click(item)
        }
    }

    /**
     * This interface is for processing the clicks.
     */
    interface OnItemClickListener {
        /**
         * Performs certain logic after clicking on a [Film] inside [RecyclerView].
         *
         * @param film is a [Film] object that was clicked on.
         */
        fun click(film: Film)
    }

    companion object {
        /** The following **_two_** constants are related to [FilmDelegateAdapter.FilmViewHolder.bind] */
        private const val RATING_MULTIPLIER = 10
        private const val ITEM_COUNTER_SUMMAND = 1
    }
}