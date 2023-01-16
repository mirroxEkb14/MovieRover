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

class AdDelegateAdapter : AbsListItemAdapterDelegate<Ad, Item, AdDelegateAdapter.AdViewHolder>() {

    inner class AdViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.ad_title)
        val textContent: TextView = itemView.findViewById(R.id.ad_content)
    }

    // is the item the type of Ad?
    override fun isForViewType(item: Item, items: MutableList<Item>, position: Int): Boolean {
        return item is Ad
    }

    override fun onCreateViewHolder(parent: ViewGroup): AdViewHolder {
        return AdViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ad_item, parent, false))
    }

    // bind the fields from Ad to the View
    override fun onBindViewHolder(item: Ad, holder: AdViewHolder, payloads: MutableList<Any>) {
        holder.textTitle.text = item.title
        holder.textContent.text = item.content
    }
}