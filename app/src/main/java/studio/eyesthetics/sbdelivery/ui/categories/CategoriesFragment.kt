package studio.eyesthetics.sbdelivery.ui.categories

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_categories.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.CategoryDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks.CategoryDiffCallback
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.DelegationAdapter
import studio.eyesthetics.sbdelivery.ui.base.MenuItemHolder
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.viewmodels.CategoriesViewModel
import studio.eyesthetics.sbdelivery.viewmodels.CategoriesViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class CategoriesFragment : BaseFragment<CategoriesViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@CategoriesFragment)
    }

    @Inject
    internal lateinit var categoriesViewModelFactory: CategoriesViewModelFactory

    override val layout: Int = R.layout.fragment_categories
    override val viewModel: CategoriesViewModel by viewModels {
        SavedStateViewModelFactory(categoriesViewModelFactory, this)
    }

    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {
        this.addMenuItem(MenuItemHolder(
            getString(R.string.menu_search),
            R.id.menu_search,
            R.drawable.ic_search,
            clickListener = { viewModel.navigate(NavigationCommand.To(R.id.searchFragment)) }
        ))
    }

    private val diffCallback = CategoryDiffCallback()
    private val categoriesAdapter by lazy { DelegationAdapter(diffCallback) }

    override fun setupViews() {
        setHasOptionsMenu(true)
        initAdapter()

        viewModel.observeCategories(viewLifecycleOwner) {
            categoriesAdapter.items = it
        }
    }

    private fun initAdapter() {
        val displayWidth = resources.displayMetrics.widthPixels

        categoriesAdapter.delegatesManager.addDelegate(CategoryDelegate(displayWidth) {
            val action = CategoriesFragmentDirections.actionCategoriesFragmentToCategoryFragment(it)
            viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))
        })

        rv_categories.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = categoriesAdapter
        }
    }
}