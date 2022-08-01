package com.niks.githubapp.recyclerview

import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewRowPresenterManager<ID, T : RecyclerViewItemVo<ID>> {

    private var presenters: SparseArrayCompat<RecyclerViewRowPresenter<ID, T>> = SparseArrayCompat()

    fun addViewPresenter(presenter: RecyclerViewRowPresenter<ID, T>) {
        val viewType = presenters.size()
        presenters.put(viewType, presenter)
    }

    fun getItemViewType(item: T?, position: Int): Int {
        val delegatesCount = presenters.size()
        for (i in 0 until delegatesCount) {
            val delegate = presenters.valueAt(i)
            if (delegate.isForViewType(item, position)) {
                return presenters.keyAt(i)
            }
        }
        throw NullPointerException(
            "No presenter found fot this " +
                    "position=$position"
        )
    }

    internal fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val presenter = getPresenter(viewType)
            ?: throw NullPointerException(
                "No presenter found fot this " +
                        "viewType=$viewType"
            )
        return presenter.onCreateViewHolder(parent, viewType)
    }

    internal fun onBindViewHolder(
        item: T,
        position: Int,
        viewHolder: RecyclerView.ViewHolder,
        vararg payloads: Any?
    ) {
        val presenter = getPresenter(viewType = viewHolder.itemViewType)
            ?: throw NullPointerException(
                "No presenter found for item at " +
                        "position =  $position for " +
                        "viewType = ${viewHolder.itemViewType}"
            )
        presenter.onBindViewHolder(item, position, viewHolder, payloads)
    }

    private fun getPresenter(viewType: Int): RecyclerViewRowPresenter<ID, T>? {
        return presenters.get(viewType)
    }
}