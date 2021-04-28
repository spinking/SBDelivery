package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.data.database.entities.SuggestionEntity
import studio.eyesthetics.sbdelivery.data.models.SearchItem
import studio.eyesthetics.sbdelivery.data.models.categories.EmptySearchItem
import studio.eyesthetics.sbdelivery.data.repositories.categories.ICategoryRepository
import studio.eyesthetics.sbdelivery.data.repositories.dishes.IDishesRepository
import studio.eyesthetics.sbdelivery.data.repositories.suggestion.ISuggestionRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class SearchViewModel(
    handle: SavedStateHandle,
    private val suggestionRepository: ISuggestionRepository,
    private val categoryRepository: ICategoryRepository,
    private val dishesRepository: IDishesRepository
) : BaseViewModel<SearchState>(handle, SearchState()) {

    private val suggestions = suggestionRepository.getSuggestions()
    private val searchItems = MutableLiveData<List<SearchItem>>()

    fun observeSuggestions(owner: LifecycleOwner, onchange: (List<SuggestionEntity>) -> Unit) {
        suggestions.observe(owner, Observer { onchange(it.take(5))
        })
    }

    fun observeSearchItems(owner: LifecycleOwner, onchange: (List<SearchItem>) -> Unit) {
        searchItems.observe(owner, Observer { onchange(it) })
    }

    fun handleDeleteSuggestion(suggestion: SuggestionEntity) {
        suggestionRepository.deleteSuggestion(suggestion)
    }

    fun handleInsertSuggestion() {
        if (currentState.searchQuery.trim().isNotEmpty())
            suggestionRepository.insertSuggestion(SuggestionEntity(currentState.searchQuery))
    }

    fun handleQuery(query: String?) {
        updateState { it.copy(searchQuery = query ?: "", isSuggestionsVisible = query?.trim().isNullOrEmpty()) }
        if (currentState.isSuggestionsVisible.not()) {
            getSearchResult(currentState.searchQuery)
        }
    }

    fun getSearchResult(query: String) {
        val categories = categoryRepository.getCategoriesByName(query)
        val dishes = dishesRepository.getDishesByName(query)
        val items = mutableListOf<SearchItem>()
        items.apply {
            addAll(categories)
            if (categories.size % 2 != 0) add(EmptySearchItem())
            addAll(dishes)
        }
        searchItems.value = items
        updateState { it.copy(isSuggestionsVisible = false) }
    }
}

class SearchViewModelFactory @Inject constructor(
    private val suggestionRepository: ISuggestionRepository,
    private val categoryRepository: ICategoryRepository,
    private val dishesRepository: IDishesRepository
) : IViewModelFactory<SearchViewModel> {
    override fun create(handle: SavedStateHandle): SearchViewModel {
        return SearchViewModel(handle, suggestionRepository,categoryRepository, dishesRepository)
    }
}

data class SearchState(
    val searchQuery: String = "",
    val isSuggestionsVisible: Boolean = true
) : IViewModelState