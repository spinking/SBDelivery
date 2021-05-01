package studio.eyesthetics.sbdelivery.ui.basket

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_basket.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.models.BasketDelegateItem
import studio.eyesthetics.sbdelivery.data.models.basket.PromoItem
import studio.eyesthetics.sbdelivery.extensions.formatToRub
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.BasketDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.PromoDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks.BasketDiffCallback
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.Binding
import studio.eyesthetics.sbdelivery.ui.base.DelegationAdapter
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.ui.delegates.RenderProp
import studio.eyesthetics.sbdelivery.viewmodels.BasketState
import studio.eyesthetics.sbdelivery.viewmodels.BasketViewModel
import studio.eyesthetics.sbdelivery.viewmodels.BasketViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class BasketFragment : BaseFragment<BasketViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@BasketFragment)
    }

    @Inject
    internal lateinit var basketViewModelFactory: BasketViewModelFactory

    override val layout: Int = R.layout.fragment_basket
    override val viewModel: BasketViewModel by viewModels {
        SavedStateViewModelFactory(basketViewModelFactory, this)
    }

    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {
        this.setTitle(getString(R.string.basket_label))
            .setBackButtonVisible(false)
    }
    override val binding: BasketBinding by lazy { BasketBinding() }
    private val basketDiffCallback = BasketDiffCallback()
    private val basketAdapter by lazy { DelegationAdapter(basketDiffCallback) }

    override fun setupViews() {
        initAdapter()

        viewModel.observeBasket(viewLifecycleOwner) {
            val basketItems: MutableList<BasketDelegateItem> = it.items.toMutableList()
            viewModel.handleEmptyBasket(it.items.isEmpty())
            basketItems.add(PromoItem(it.basketInfo.promocode, it.basketInfo.promotext))
            basketAdapter.items = it.items
            tv_sum.text = it.basketInfo.total.formatToRub()
        }
    }

    private fun initAdapter() {
        basketAdapter.delegatesManager.apply {
            addDelegate(BasketDelegate {
                //TODO delete item
            })
            addDelegate(PromoDelegate())
        }

        rv_basket.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = basketAdapter
        }
    }

    inner class BasketBinding : Binding() {
        var isEmptyBasket: Boolean by RenderProp(false) {
            tv_empty.isVisible = it
            gp_sum.isVisible = it.not()
        }
        override fun bind(data: IViewModelState) {
            data as BasketState
            isEmptyBasket = data.isEmptyBasket
        }
    }
}