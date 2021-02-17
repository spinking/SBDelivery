package studio.eyesthetics.sbdelivery.ui.base

import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.paging.PagedListDelegationAdapter

class DelegationPageListAdapter<T>(diffCallback: DiffCallback<T>) : PagedListDelegationAdapter<T>(
    AdapterDelegatesManager(), diffCallback) {

    val delegatesManager: AdapterDelegatesManager<List<T>>
        get() = super.delegatesManager
}