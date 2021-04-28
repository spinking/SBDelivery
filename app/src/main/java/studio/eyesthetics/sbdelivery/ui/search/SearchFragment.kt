package studio.eyesthetics.sbdelivery.ui.search

import android.view.Menu
import android.view.MenuInflater
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.DishSearchDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.EmptySearchItemDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.SearchCategoryDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.SuggestionDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.decorators.VerticalItemDecorator
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks.SearchDiffCallback
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks.SuggestionDiffCallback
import studio.eyesthetics.sbdelivery.ui.base.*
import studio.eyesthetics.sbdelivery.ui.delegates.RenderProp
import studio.eyesthetics.sbdelivery.viewmodels.SearchState
import studio.eyesthetics.sbdelivery.viewmodels.SearchViewModel
import studio.eyesthetics.sbdelivery.viewmodels.SearchViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class SearchFragment : BaseFragment<SearchViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@SearchFragment)
    }

    @Inject
    internal lateinit var searchViewModelFactory: SearchViewModelFactory

    override val layout: Int = R.layout.fragment_search
    override val viewModel: SearchViewModel by viewModels {
        SavedStateViewModelFactory(searchViewModelFactory, this)
    }
    override val binding: SearchBinding by lazy { SearchBinding() }
    private val searchDiffCallback = SearchDiffCallback()
    private val suggestionDiffCallback = SuggestionDiffCallback()
    private val searchAdapter by lazy { DelegationAdapter(searchDiffCallback) }
    private val suggestionAdapter by lazy { DelegationAdapter(suggestionDiffCallback) }
    lateinit var autocomplete: AutoCompleteTextView

    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {
        this.setTitle(null)
            .addMenuItem(MenuItemHolder(
            getString(R.string.menu_search),
            R.id.action_search,
            R.drawable.ic_search,
            R.layout.layout_search_view
        ))
    }

    override fun setupViews() {
        setHasOptionsMenu(true)
        main.supportActionBar?.setDisplayShowTitleEnabled(false)
        initSearchAdapter()
        initSuggestionAdapter()

        viewModel.observeSuggestions(viewLifecycleOwner) {
            suggestionAdapter.items = it
        }

        viewModel.observeSearchItems(viewLifecycleOwner) {
            searchAdapter.items = it
        }
    }

    private fun initSuggestionAdapter() {
        suggestionAdapter.delegatesManager.addDelegate(SuggestionDelegate({
            viewModel.handleDeleteSuggestion(it)
        }) {
            viewModel.getSearchResult(it.suggestion)
        })
        rv_suggestion.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = suggestionAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search_open, menu)
        val searchItem = menu.findItem(R.id.action_search)

        val searchView = searchItem?.actionView as SearchView
        searchView.apply {
            setIconifiedByDefault(false)
            queryHint = getString(R.string.menu_search)
        }

        val closeButton =
            searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
        closeButton.apply {
            setImageResource(R.drawable.ic_clear_gray)
        }

        autocomplete = searchView.findViewById<AutoCompleteTextView>(R.id.search_src_text).apply {
            setDropDownBackgroundResource(android.R.color.transparent)
            dropDownWidth = Int.MAX_VALUE
            setTextColor(resources.getColor(R.color.color_secondary, null))
            setHintTextColor(resources.getColor(R.color.color_hint_gray, null))
            threshold = 1
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.handleQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.handleQuery(newText)
                return true
            }
        })
    }

    private fun initSearchAdapter() {
        val displayWidth = resources.displayMetrics.widthPixels
        searchAdapter.delegatesManager.apply {
            addDelegate(SearchCategoryDelegate(displayWidth) {
                viewModel.handleInsertSuggestion()
                val action = SearchFragmentDirections.actionSearchFragmentToCategoryFragment(it.id)
                viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))
            })

            addDelegate(DishSearchDelegate(displayWidth, { dishItem ->
                viewModel.handleInsertSuggestion()
                //TODO dish click listener
            }, {
                //TODO add to basket click listener
            }) { dishId, isChecked ->
                //TODO add to favorite click listener
            })

            addDelegate(EmptySearchItemDelegate())
        }

        rv_search.apply {
            addItemDecoration(VerticalItemDecorator())
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = searchAdapter
        }
    }

    inner class SearchBinding : Binding() {
        private var isSuggestionsVisible: Boolean by RenderProp(true) {
            rv_suggestion.isVisible = it
        }
        override fun bind(data: IViewModelState) {
            data as SearchState
            isSuggestionsVisible = data.isSuggestionsVisible
        }
    }
}