package com.niks.githubapp.recyclerview

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

fun <T : RecyclerViewItemVo<ID>, ID> recyclerViewItemDiffUtil(): DiffUtil.ItemCallback<T> {
    return object : DiffUtil.ItemCallback<T>() {

        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.getItemId() == newItem.getItemId()
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    }
}