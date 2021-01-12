package studio.eyesthetics.sbdelivery.ui.home

import androidx.fragment.app.viewModels
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
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

    override fun setupViews() {

    }
}