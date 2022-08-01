package com.niks.githubapp.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class ItemPresenter
constructor(
    @LayoutRes val layoutId: Int,
    val viewTypePredicate: ((item: RecyclerViewItemVo<String>?) -> Boolean)
) : RecyclerViewRowPresenter<String, RecyclerViewItemVo<String>>() {

    override fun isForViewType(item: RecyclerViewItemVo<String>?, position: Int): Boolean =
        viewTypePredicate(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false)
        return RecyclerViewItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        item: RecyclerViewItemVo<String>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        vararg payloads: Any?
    ) {
        val viewHolder: RecyclerViewItemViewHolder = holder as RecyclerViewItemViewHolder
        viewHolder.bind(item)
    }
}