package com.danidev.movierover.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danidev.movierover.R
import com.danidev.movierover.model.Film

/**
 * Adapter provides the data to the RV
 */
class FilmListRecyclerAdapter(private var items: ArrayList<Film>, private val clickListener: OnItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FilmViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.film_item, parent, false))
    }

    // bind the fields from Film to the View
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilmViewHolder -> {
                holder.bind(items[position])
            }
        }
    }

    override fun getItemCount(): Int = items.size

    fun addItems(adapter: FilmListRecyclerAdapter) {

        fun updateData(newList: ArrayList<Film>, adapter: FilmListRecyclerAdapter) {
            val oldList = adapter.items // get the old list
            val productDiff = FilmDiff(oldList, newList)
            val diffResult = DiffUtil.calculateDiff(productDiff)
            adapter.items = newList // set a new list
            diffResult.dispatchUpdatesTo(adapter) // the data changed in Adapter
        }

        val newList = arrayListOf<Film>()
        newList.addAll(adapter.items)
        updateData(newList, adapter)
    }

    // interface for processing the clicks
    interface OnItemClickListener {
        fun click(film: Film)
    }
}
