package studio.eyesthetics.sbdelivery.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.database.entities.DishEntity
import studio.eyesthetics.sbdelivery.extensions.dpToPx
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.RecommendDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.decorators.ItemDecorator
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.DelegationAdapter
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.viewmodels.HomeViewModel
import studio.eyesthetics.sbdelivery.viewmodels.HomeViewModelFactory
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

    private val recommendAdapter by lazy { DelegationAdapter<DishEntity>() }
    private val bestAdapter by lazy { DelegationAdapter<DishEntity>() }
    private val popularAdapter by lazy { DelegationAdapter<DishEntity>() }

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
            //TODO transition to dish
        }, {
            //TODO add dish to basket

        }) {
            //TODO add ot favorite
        })

        bestAdapter.delegatesManager.addDelegate(RecommendDelegate(displayWidth, {
            //TODO transition to dish
        }, {
            //TODO add dish to basket

        }) {
            //TODO add ot favorite
        })

        popularAdapter.delegatesManager.addDelegate(RecommendDelegate(displayWidth, {
            //TODO transition to dish
        }, {
            //TODO add dish to basket

        }) {
            //TODO add ot favorite
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