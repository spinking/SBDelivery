package studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks

import studio.eyesthetics.sbdelivery.data.database.entities.DishItem
import studio.eyesthetics.sbdelivery.ui.base.DiffCallback

class DishDiffCallback : DiffCallback<DishItem>() {
    override fun sameId(oldItem: DishItem, newItem: DishItem): Boolean = oldItem.id == newItem.id

    override fun sameItem(oldItem: DishItem, newItem: DishItem): Boolean = oldItem == newItem
}