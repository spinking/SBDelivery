package studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks

import studio.eyesthetics.sbdelivery.data.database.entities.SuggestionEntity
import studio.eyesthetics.sbdelivery.ui.base.DiffCallback

class SuggestionDiffCallback : DiffCallback<SuggestionEntity>() {
    override fun sameId(oldItem: SuggestionEntity, newItem: SuggestionEntity): Boolean = oldItem == newItem

    override fun sameItem(oldItem: SuggestionEntity, newItem: SuggestionEntity): Boolean = oldItem == newItem
}