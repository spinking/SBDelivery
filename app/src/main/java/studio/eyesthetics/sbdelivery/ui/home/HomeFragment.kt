package studio.eyesthetics.sbdelivery.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.database.entities.DishItem
import studio.eyesthetics.sbdelivery.extensions.dpToPx
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.RecommendDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.decorators.ItemDecorator
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks.DishDiffCallback
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.DelegationAdapter
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.viewmodels.HomeViewModel
import studio.eyesthetics.sbdelivery.viewmodels.HomeViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@HomeFragment)
    }

    @Inject
    internal lateinit var homeViewModelFactory: HomeViewModelFactory

    override val layout: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels {
        SavedStateViewModelFactory(homeViewModelFactory, this)
    }

    override val prepareToolbar: (ToolbarBuilder.() -> Unit)? = {}

    private val diffCallback = DishDiffCallback()
    private val recommendAdapter by lazy { DelegationAdapter(diffCallback) }
    private val bestAdapter by lazy { DelegationAdapter<DishItem>(diffCallback) }
    private val popularAdapter by lazy { DelegationAdapter<DishItem>(diffCallback) }

    override fun setupViews() {

        viewModel.observeRecommend(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                layout_recommend.visibility = View.VISIBLE
                recommendAdapter.items = it
            }
        }

        viewModel.observeBest(viewLifecycleOwner) {
            if (it.size >= MINIMUM_OF_BEST_DISHES) {
                layout_best.visibility = View.VISIBLE
                bestAdapter.items = it
            }
        }

        viewModel.observePopular(viewLifecycleOwner) {
            popularAdapter.items = it
        }

        initButtons()
        initAdapters()
    }

    private fun initButtons() {
        //TODO set buttons
    }

    private fun initAdapters() {
        val displayWidth = resources.displayMetrics.widthPixels

        recommendAdapter.delegatesManager.addDelegate(RecommendDelegate(displayWidth, {
            val action = HomeFragmentDirections.actionHomeFragmentToDishFragment(it)
            viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))
        }, {
            //TODO add dish to basket

        }) { dishId, isChecked ->
            viewModel.handleFavorite(dishId, isChecked)
        })

        bestAdapter.delegatesManager.addDelegate(RecommendDelegate(displayWidth, {
            val action = HomeFragmentDirections.actionHomeFragmentToDishFragment(it)
            viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))
        }, {
            //TODO add dish to basket

        }) { dishId, isChecked ->
            viewModel.handleFavorite(dishId, isChecked)
        })

        popularAdapter.delegatesManager.addDelegate(RecommendDelegate(displayWidth, {
            val action = HomeFragmentDirections.actionHomeFragmentToDishFragment(it)
            viewModel.navigate(NavigationCommand.To(action.actionId, action.arguments))
        }, {
            //TODO add dish to basket

        }) { dishId, isChecked ->
            viewModel.handleFavorite(dishId, isChecked)
        })

        rv_recommend.apply {
            addItemDecoration(ItemDecorator(DEFAULT_RIGHT_MARGIN.dpToPx()))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = recommendAdapter
        }

        rv_best.apply {
            addItemDecoration(ItemDecorator(DEFAULT_RIGHT_MARGIN.dpToPx()))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = bestAdapter
        }

        rv_popular.apply {
            addItemDecoration(ItemDecorator(DEFAULT_RIGHT_MARGIN.dpToPx()))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = popularAdapter
        }
    }

    companion object {
        private const val DEFAULT_RIGHT_MARGIN = 8
        private const val MINIMUM_OF_BEST_DISHES = 4
    }
}