package com.danidev.movierover.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.danidev.movierover.model.Item
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

/**
 * This class represents [ListDelegationAdapter] that provides the data to the
 * [androidx.recyclerview.widget.RecyclerView]. [com.hannesdorfmann.adapterdelegates4.AdapterDelegate]
 *
 * @param clickListener .
 */
class ItemListRecyclerAdapter(clickListener: FilmDelegateAdapter.OnItemClickListener)
    : ListDelegationAdapter<List<Item>>() {

    /**
     * This initialization block adds an AdapterDelegate.
     */
    init {
        delegatesManager.addDelegate(FilmDelegateAdapter(clickListener))
        delegatesManager.addDelegate(AdDelegateAdapter())
    }

    /**
     * Sets the items/data source of this Adapter.
     *
     * @param items are the items/data source.
     */
    override fun setItems(items: List<Item>?) {
        super.setItems(items)

        /**
         * Gets the old list. Then, sets a new list. After, calls [DiffUtil.DiffResult.dispatchUpdatesTo]
         * that dispatches the update events to the given adapter.
         *
         * @param newList is a new list of elements.
         */
        fun updateData(newList: ArrayList<Item>) {
            val oldList = super.items
            val productDiff = ItemDiff(oldList as ArrayList<Item>, newList)
            val diffResult = DiffUtil.calculateDiff(productDiff)
            super.items = newList
            diffResult.dispatchUpdatesTo(this)
        }
        updateData(items as ArrayList<Item>)
    }

    /**
     * Updates all the elements from the RV
     * @param items     a new list of Films
     */
    fun updateDataInefficient(items: List<Item>) {
        super.items = items
        notifyDataSetChanged()
    }
}
