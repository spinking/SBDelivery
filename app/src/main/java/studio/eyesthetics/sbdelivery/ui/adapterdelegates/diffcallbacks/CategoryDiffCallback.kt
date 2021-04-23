package studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks

import studio.eyesthetics.sbdelivery.data.database.entities.CategoryEntity
import studio.eyesthetics.sbdelivery.ui.base.DiffCallback

class CategoryDiffCallback : DiffCallback<CategoryEntity>() {
    override fun sameId(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean = oldItem.id == newItem.id

    override fun sameItem(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean = oldItem == newItem
}