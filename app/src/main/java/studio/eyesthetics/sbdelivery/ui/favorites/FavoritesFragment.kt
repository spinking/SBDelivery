package studio.eyesthetics.sbdelivery.ui.favorites

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_favorites.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.DishDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.decorators.VerticalItemDecorator
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks.DishDiffCallback
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.DelegationAdapter
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.viewmodels.FavoritesViewModel
import studio.eyesthetics.sbdelivery.viewmodels.FavoritesViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class FavoritesFragment : BaseFragment<FavoritesViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@FavoritesFragment)
    }

    @Inject
    internal lateinit var favoritesViewModelFactory: FavoritesViewModelFactory

    override val layout: Int = R.layout.fragment_favorites
    override val viewModel: FavoritesViewModel by viewModels {
        SavedStateViewModelFactory(favoritesViewModelFactory, this)
    }

    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {
        this.setTitle(getString(R.string.favorites_label))
    }
    private val dishDiffCallback = DishDiffCallback()
    private val favoritesAdapter by lazy { DelegationAdapter(dishDiffCallback) }


    override fun setupViews() {
        initFavoriteAdapter()

        viewModel.observeFavoriteDishes(viewLifecycleOwner) {
            favoritesAdapter.items = it
        }
    }

    private fun initFavoriteAdapter() {
        val displayWidth = resources.displayMetrics.widthPixels
        favoritesAdapter.delegatesManager.addDelegate(DishDelegate(displayWidth, {
            val action = FavoritesFragmentDirections.actionFavoritesFragmentToDishFragment(it)
            viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))
        }, {
            //TODO add to basket click listener
        }) { dishId, isChecked ->
            viewModel.handleFavorite(dishId, isChecked)
        })

        rv_favorites.apply {
            addItemDecoration(VerticalItemDecorator())
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = favoritesAdapter
        }
    }
}