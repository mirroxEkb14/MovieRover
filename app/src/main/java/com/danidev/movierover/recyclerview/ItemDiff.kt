package com.danidev.movierover.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.danidev.movierover.model.*
import java.lang.IllegalArgumentException

/**
 * [DiffUtil] is a class that allows an adapter to automatically determine which elements in the list
 * need to be updated. DiffUtil follows the indexes of the elements and solves performance problem
 * using mathematical algorithms, using calls of [androidx.recyclerview.widget.RecyclerView.Adapter.notifyItemChanged].
 * It is only needed to implement [DiffUtil.Callback] and calculate the Diff and inform the Adapter.
 *
 * One of the methods of notification about data changes in the Adapter.
 *
 * @property oldList is an old list of elements.
 * @property newList is a new list of elements.
 */
class ItemDiff(private val oldList: ArrayList<Item>, private val newList: ArrayList<Item> )
    : DiffUtil.Callback() {

    /** Value representing an exception message used in [areContentsTheSame]. */
    private val wrongViewType = "invalid View type."

    /**
     * Is called by the [DiffUtil] to decide whether two object represent the same [Item].
     *
     * @param oldItemPosition is the position of the item in the old list.
     * @param newItemPosition is the position of the item in the new list.
     *
     * @return `true`, if the ids of these items are equal, `false` otherwise.
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    /**
     * Is called by the [DiffUtil] checks whether two items have the same data. DiffUtil uses this
     * information to detect if the contents of an item has changed.
     *
     * @param oldItemPosition the position of the item in the old list.
     * @param newItemPosition The position of the item in the new list which replaces the `oldItem`.
     *
     * @return `true`, if the contents of the items (its fields) are the same, `false` otherwise.
     */
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
            else -> throw IllegalArgumentException(wrongViewType)
        }
    }

    /**
     * Returns the size of the old list.
     *
     * @return the size of the old list.
     */
    override fun getOldListSize(): Int = oldList.size

    /**
     * Returns the size of the new list.
     *
     * @return the size of the new list.
     */
    override fun getNewListSize(): Int = newList.size
}