package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.data.database.entities.SuggestionEntity
import studio.eyesthetics.sbdelivery.data.repositories.suggestion.ISuggestionRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class SearchViewModel(
    handle: SavedStateHandle,
    private val suggestionRepository: ISuggestionRepository
) : BaseViewModel<SearchState>(handle, SearchState()) {

    private val suggestions = suggestionRepository.getSuggestions()

    fun observeSuggestions(owner: LifecycleOwner, onchange: (List<SuggestionEntity>) -> Unit) {
        suggestions.observe(owner, Observer { onchange(it.take(5))
        })
    }

    fun handleDeleteSuggestion(suggestion: SuggestionEntity) {
        suggestionRepository.deleteSuggestion(suggestion)
    }

    fun handleInsertSuggestion(suggestion: String) {
        suggestionRepository.insertSuggestion(SuggestionEntity(suggestion))
    }

    fun getSearchResult(query: String) {
        //TODO get categories and dishes
    }

}

class SearchViewModelFactory @Inject constructor(
    private val suggestionRepository: ISuggestionRepository
) : IViewModelFactory<SearchViewModel> {
    override fun create(handle: SavedStateHandle): SearchViewModel {
        return SearchViewModel(handle, suggestionRepository)
    }
}

class SearchState : IViewModelState