package studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks

import studio.eyesthetics.sbdelivery.data.models.menu.MenuItem
import studio.eyesthetics.sbdelivery.ui.base.DiffCallback

class MenuDiffCallback : DiffCallback<MenuItem>() {
    override fun sameId(oldItem: MenuItem, newItem: MenuItem): Boolean = oldItem.titleId == newItem.titleId
    override fun sameItem(oldItem: MenuItem, newItem: MenuItem): Boolean = oldItem == newItem
}