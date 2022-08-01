package com.niks.githubapp.recyclerview

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewPagingDataAdapter<ID, T : RecyclerViewItemVo<ID>>(
    private var presenterManager: RecyclerViewRowPresenterManager<ID, T>
) :
    PagingDataAdapter<T, RecyclerView.ViewHolder>(recyclerViewItemDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return presenterManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null)
            presenterManager.onBindViewHolder(item, position, holder, null)

    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return presenterManager.getItemViewType(item, position)
    }


}


