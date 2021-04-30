package studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks

import studio.eyesthetics.sbdelivery.data.database.entities.ReviewEntity
import studio.eyesthetics.sbdelivery.ui.base.DiffCallback

class ReviewDiffCallback : DiffCallback<ReviewEntity>() {
    override fun sameId(oldItem: ReviewEntity, newItem: ReviewEntity): Boolean = oldItem.id == newItem.id

    override fun sameItem(oldItem: ReviewEntity, newItem: ReviewEntity): Boolean = oldItem == newItem
}