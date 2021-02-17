package studio.eyesthetics.sbdelivery.ui.base

import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class DelegationAdapter<T>(diffCallback: DiffCallback<T>) : AsyncListDifferDelegationAdapter<T>(diffCallback) {

    val delegatesManager: AdapterDelegatesManager<List<T>>
        get() = super.delegatesManager

    init {
        items = listOf()
    }
}