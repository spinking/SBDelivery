package studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks

import studio.eyesthetics.sbdelivery.data.database.entities.BasketItemEntity
import studio.eyesthetics.sbdelivery.data.models.BasketDelegateItem
import studio.eyesthetics.sbdelivery.ui.base.DiffCallback

class BasketDiffCallback : DiffCallback<BasketDelegateItem>() {
    override fun sameId(oldItem: BasketDelegateItem, newItem: BasketDelegateItem): Boolean {
        return when(oldItem) {
            is BasketItemEntity -> oldItem.id == (newItem as BasketItemEntity).id
            else -> oldItem == newItem
        }
    }

    override fun sameItem(oldItem: BasketDelegateItem, newItem: BasketDelegateItem): Boolean = oldItem == newItem
}