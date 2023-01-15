package com.danidev.movierover.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.danidev.movierover.model.Film

/**
 * DiffUtil - one of methods of notification about data changes in the adapter
 */
class FilmDiff(private val oldList: ArrayList<Film>, private val newList: ArrayList<Film> ): DiffUtil.Callback() {

    // the elements are equal if their 'ids' are equal
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    // the content is equal if all the fields are equal
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFilm = oldList[oldItemPosition]
        val newFilm = newList[newItemPosition]
        return oldFilm.title == newFilm.title &&
                oldFilm.description == newFilm.description &&
                oldFilm.poster == newFilm.poster
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size
}