package studio.eyesthetics.sbdelivery.ui.categories.category

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_category.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.DishDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.TabCategoryDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.decorators.VerticalItemDecorator
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks.CategoryDiffCallback
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks.DishDiffCallback
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.DelegationAdapter
import studio.eyesthetics.sbdelivery.ui.base.DelegationPageListAdapter
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.viewmodels.CategoryViewModel
import studio.eyesthetics.sbdelivery.viewmodels.CategoryViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class CategoryFragment : BaseFragment<CategoryViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@CategoryFragment)
    }

    @Inject
    internal lateinit var categoryViewModelFactory: CategoryViewModelFactory

    override val layout: Int = R.layout.fragment_category
    override val viewModel: CategoryViewModel by viewModels {
        SavedStateViewModelFactory(categoryViewModelFactory, this)
    }
    private val categoriesDiffCallback = CategoryDiffCallback()
    private val dishesDiffCallback = DishDiffCallback()
    private val categoriesAdapter by lazy { DelegationAdapter(categoriesDiffCallback) }
    private val dishesAdapter by lazy { DelegationPageListAdapter(dishesDiffCallback) }
    private val args: CategoryFragmentArgs by navArgs()

    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {}

    override fun setupViews() {
        initAdapters()

        viewModel.observeDishes(viewLifecycleOwner) {
            dishesAdapter.submitList(it)
        }

        if (args.categoryId != null)
            viewModel.handleCategoryId(args.categoryId ?: "")
    }

    private fun initAdapters() {
        val displayWidth = resources.displayMetrics.widthPixels
        categoriesAdapter.delegatesManager.addDelegate(TabCategoryDelegate())
        dishesAdapter.delegatesManager.addDelegate(DishDelegate(displayWidth, {
            //TODO transition to dish
        }, {
            //TODO add dish to basket

        }) { dishId, isChecked ->
            //viewModel.handleFavorite(dishId, isChecked)
        })

        rv_categories.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }

        rv_dishes.apply {
            addItemDecoration(VerticalItemDecorator())
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = dishesAdapter
        }
    }
}