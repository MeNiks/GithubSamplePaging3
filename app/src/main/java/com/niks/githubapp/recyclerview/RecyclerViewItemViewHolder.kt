package com.niks.githubapp.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.niks.githubapp.BR

class RecyclerViewItemViewHolder
constructor(
     val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(obj: Any) {
        binding.setVariable(BR.obj, obj)
        binding.executePendingBindings()
    }
}