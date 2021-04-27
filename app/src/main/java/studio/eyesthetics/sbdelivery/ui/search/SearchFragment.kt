package studio.eyesthetics.sbdelivery.ui.search

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_search.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.SearchCategoryDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.decorators.VerticalItemDecorator
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks.SearchDiffCallback
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.DelegationAdapter
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
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

    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {
        //TODO add search
    }

    override fun setupViews() {
        initSearchAdapter()
        initSuggestionAdapter()
    }

    private fun initSuggestionAdapter() {

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
}