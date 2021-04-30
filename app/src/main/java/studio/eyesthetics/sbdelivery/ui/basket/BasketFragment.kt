package studio.eyesthetics.sbdelivery.ui.basket

import androidx.fragment.app.viewModels
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.viewmodels.BasketViewModel
import studio.eyesthetics.sbdelivery.viewmodels.BasketViewModelFactory
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

    override fun setupViews() {

    }
}