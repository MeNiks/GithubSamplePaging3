package com.niks.githubapp.ui.pr

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.niks.githubapp.BR
import com.niks.githubapp.databinding.PrItemLoadingBinding
import com.niks.githubapp.recyclerview.RecyclerViewItemViewHolder

class RetryLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<RecyclerViewItemViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerViewItemViewHolder, loadState: LoadState) {
        with(holder.binding as PrItemLoadingBinding) {
            setVariable(BR.isLoading, loadState is LoadState.Loading)
            executePendingBindings()
            rootView.setOnClickListener {
                retry.invoke()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): RecyclerViewItemViewHolder {
        return RecyclerViewItemViewHolder(
            binding = PrItemLoadingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

}