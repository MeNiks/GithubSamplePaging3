package com.niks.githubapp.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewRowPresenter<ID, T : RecyclerViewItemVo<ID>> {

    abstract fun isForViewType(item: T?, position: Int): Boolean

    abstract fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

    abstract fun onBindViewHolder(
        item: T,
        position: Int,
        holder: RecyclerView.ViewHolder,
        vararg payloads: Any?
    )
}