package studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks

import studio.eyesthetics.sbdelivery.data.models.BasketDelegateItem
import studio.eyesthetics.sbdelivery.ui.base.DiffCallback

class BasketDiffCallback : DiffCallback<BasketDelegateItem>() {
    override fun sameId(oldItem: BasketDelegateItem, newItem: BasketDelegateItem): Boolean = oldItem == newItem

    override fun sameItem(oldItem: BasketDelegateItem, newItem: BasketDelegateItem): Boolean = oldItem == newItem
}