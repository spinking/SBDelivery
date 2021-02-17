package studio.eyesthetics.sbdelivery.ui.base

import androidx.recyclerview.widget.DiffUtil

abstract class DiffCallback<T> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean = sameId(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = sameItem(oldItem, newItem)

    abstract fun sameId(oldItem: T, newItem: T): Boolean
    abstract fun sameItem(oldItem: T, newItem: T): Boolean
}