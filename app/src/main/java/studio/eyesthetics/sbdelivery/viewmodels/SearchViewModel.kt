package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class SearchViewModel(
    handle: SavedStateHandle
) : BaseViewModel<SearchState>(handle, SearchState()) {

}

class SearchViewModelFactory @Inject constructor(

) : IViewModelFactory<SearchViewModel> {
    override fun create(handle: SavedStateHandle): SearchViewModel {
        return SearchViewModel(handle)
    }
}

class SearchState : IViewModelState