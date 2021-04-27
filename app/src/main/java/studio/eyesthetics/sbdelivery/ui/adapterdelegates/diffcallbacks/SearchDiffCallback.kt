package studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks

import studio.eyesthetics.sbdelivery.data.models.SearchItem
import studio.eyesthetics.sbdelivery.ui.base.DiffCallback

class SearchDiffCallback : DiffCallback<SearchItem>() {
    override fun sameId(oldItem: SearchItem, newItem: SearchItem): Boolean = oldItem == newItem

    override fun sameItem(oldItem: SearchItem, newItem: SearchItem): Boolean = oldItem == newItem
}