package studio.eyesthetics.sbdelivery.ui.search

import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.extensions.dpToPx
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.SearchCategoryDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.decorators.VerticalItemDecorator
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks.SearchDiffCallback
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.DelegationAdapter
import studio.eyesthetics.sbdelivery.ui.base.MenuItemHolder
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.ui.custom.CustomAutoCompleteAdapter
import studio.eyesthetics.sbdelivery.viewmodels.SearchViewModel
import studio.eyesthetics.sbdelivery.viewmodels.SearchViewModelFactory
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
    private val searchDiffCallback = SearchDiffCallback()
    private val searchAdapter by lazy { DelegationAdapter(searchDiffCallback) }
    lateinit var autocomplete: AutoCompleteTextView
    lateinit var suggestionAdapter: CustomAutoCompleteAdapter

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

        viewModel.observeSuggestions(viewLifecycleOwner) {
            showSuggestions(it)
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

        searchView.findViewById<View>(autocomplete.dropDownAnchor)
            ?.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                val screenWidthPixel = resources.displayMetrics.widthPixels
                autocomplete.dropDownWidth = screenWidthPixel
                autocomplete.dropDownVerticalOffset = SUGGESTION_TOP_MARGIN.dpToPx()
            }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                searchView.setQuery(suggestionAdapter.getItem(position), false)
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                searchView.setQuery(suggestionAdapter.getItem(position), false)
                return true
            }
        })

        //костыль, иначе suggestions не отображаются
        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.showSuggestions()
        }, 100)
    }

    private fun initSearchAdapter() {
        val displayWidth = resources.displayMetrics.widthPixels
        searchAdapter.delegatesManager.addDelegate(SearchCategoryDelegate(displayWidth) {

        })

        rv_search.apply {
            addItemDecoration(VerticalItemDecorator())
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = searchAdapter
        }
    }

    private fun showSuggestions(suggestions: List<String>) {
        suggestionAdapter = CustomAutoCompleteAdapter(requireContext(), R.layout.item_suggestion, suggestions) {
            viewModel.handleDeleteSuggestion(it)
        }
        if (this::autocomplete.isInitialized) {
            autocomplete.setAdapter(suggestionAdapter)
            autocomplete.showDropDown()
        }
    }

    companion object {
        private const val SUGGESTION_TOP_MARGIN = 20
    }
}