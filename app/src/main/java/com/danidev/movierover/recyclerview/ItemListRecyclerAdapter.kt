package com.danidev.movierover.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.danidev.movierover.model.Item
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

/**
 * Adapter provides the data to the RV
 */
class ItemListRecyclerAdapter(clickListener: FilmDelegateAdapter.OnItemClickListener) : ListDelegationAdapter<List<Item>>() {

    init {
        delegatesManager.addDelegate(FilmDelegateAdapter(clickListener))
        delegatesManager.addDelegate(AdDelegateAdapter())
    }

    override fun setItems(items: List<Item>?) {
        super.setItems(items)

        fun updateData(newList: ArrayList<Item>) {
            val oldList = super.items // get the old list
            val productDiff = ItemDiff(oldList as ArrayList<Item>, newList)
            val diffResult = DiffUtil.calculateDiff(productDiff)
            super.items = newList // set a new list
            diffResult.dispatchUpdatesTo(this) // the data changed in Adapter
        }
        updateData(items as ArrayList<Item>)
    }
}
