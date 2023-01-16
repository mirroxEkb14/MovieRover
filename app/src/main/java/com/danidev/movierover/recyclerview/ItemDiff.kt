package com.danidev.movierover.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.danidev.movierover.model.*
import java.lang.IllegalArgumentException

/**
 * DiffUtil - one of methods of notification about data changes in the adapter
 */
class ItemDiff(private val oldList: ArrayList<Item>, private val newList: ArrayList<Item> ): DiffUtil.Callback() {

    // the elements are equal if their 'ids' are equal
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    // the content is equal if all the fields are equal
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return when (oldItem) {
            is Film-> {
                oldItem.title == (newItem as Film).title &&
                oldItem.description == newItem.description &&
                oldItem.poster == newItem.poster
            }
            is Ad -> {
                oldItem.title == (newItem as Ad).title &&
                oldItem.content == newItem.content
            }
            else -> throw IllegalArgumentException("invalid View type.")
        }
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size
}