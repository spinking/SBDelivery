package studio.eyesthetics.sbdelivery.ui.base

import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class DelegationAdapter<T>(diffCallback: DiffCallback<T>) : AsyncListDifferDelegationAdapter<T>(diffCallback) {

    val delegatesManager: AdapterDelegatesManager<List<T>>
        get() = super.delegatesManager

    init {
        items = listOf()
    }

    fun deleteItem(item: T) {
        val currentItems = items.toMutableList()
        currentItems.remove(item)
        items = currentItems
    }

    fun clearItems() {
        items = listOf()
    }
}