package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class SearchViewModel(
    handle: SavedStateHandle
) : BaseViewModel<SearchState>(handle, SearchState()) {

    private val suggestions = MutableLiveData<List<String>>()

    fun observeSuggestions(owner: LifecycleOwner, onchange: (List<String>) -> Unit) {
        suggestions.observe(owner, Observer { onchange(it) })
    }

    fun showSuggestions() {
        //TODO remove mocks
        suggestions.value = listOf("Первый", "Второй", "Третий","Четвертый", "Пятый")
    }

    fun handleDeleteSuggestion(suggestion: String) {
        //TODO delete suggestion from db
    }

}

class SearchViewModelFactory @Inject constructor(

) : IViewModelFactory<SearchViewModel> {
    override fun create(handle: SavedStateHandle): SearchViewModel {
        return SearchViewModel(handle)
    }
}

class SearchState : IViewModelState