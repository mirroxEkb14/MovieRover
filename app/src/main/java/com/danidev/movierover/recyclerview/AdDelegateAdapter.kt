package com.danidev.movierover.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.danidev.movierover.R
import com.danidev.movierover.model.Ad
import com.danidev.movierover.model.Item
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate

/**
 * This delegate class represents a [com.hannesdorfmann.adapterdelegates4.AdapterDelegate] object
 * that is responsible for providing data.
 */
class AdDelegateAdapter : AbsListItemAdapterDelegate<Ad, Item, AdDelegateAdapter.AdViewHolder>() {

    /**
     * The ViewHolder class is inherited from [RecyclerView.ViewHolder]. An instance of this class
     * must return the [onCreateViewHolder] method. The ViewHolder constructor accepts the created
     * [View], so the return command is preceded by the process of creating a View from the ad_item.xml.
     *
     * @param itemView is the root [View] of the inflated XML file.
     */
    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.ad_title)
        val textContent: TextView = itemView.findViewById(R.id.ad_content)
    }

    /**
     * Determines whether this `AdapterDelegate` is the responsible for the given item in the list
     * or not. Is this particular item in the list represents `Ad` or not.
     *
     * @param item is an item from the dataset (list) at the given `position`.
     * @param items are items from the adapter dataset.
     * @param position is an index with the help of which it is possible to get data from the dataset
     * (list) of the model.
     *
     * @return `true` if this `AdapterDelegate` is responsible for the given item, `false` otherwise.
     */
    override fun isForViewType(item: Item, items: MutableList<Item>, position: Int): Boolean {
        return item is Ad
    }

    /**
     * Creates [AdViewHolder] and returns it.
     *
     * @param parent is the [ViewGroup] parent of the given datasource.
     *
     * @return newly created [AdViewHolder] object.
     */
    override fun onCreateViewHolder(parent: ViewGroup): AdViewHolder {
        return AdViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.ad_item, parent, false))
    }

    /**
     * Binds the fields from [Ad] to the [View].
     *
     * @param item is the data item.
     * @param holder is the [AdViewHolder].
     * @param payloads are the payloads used to update a certain list element..
     */
    override fun onBindViewHolder(item: Ad, holder: AdViewHolder, payloads: MutableList<Any>) {
        holder.textTitle.text = item.title
        holder.textContent.text = item.content
    }
}