package studio.eyesthetics.sbdelivery.ui.menu

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_menu.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.MenuDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks.MenuDiffCallback
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.DelegationAdapter
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.viewmodels.MenuViewModel
import studio.eyesthetics.sbdelivery.viewmodels.MenuViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import studio.eyesthetics.sbdelivery.viewmodels.base.SavedStateViewModelFactory
import javax.inject.Inject

class MenuFragment : BaseFragment<MenuViewModel>() {

    init {
        App.INSTANCE.appComponent.inject(this@MenuFragment)
    }

    @Inject
    internal lateinit var menuViewModelFactory: MenuViewModelFactory

    override val layout: Int = R.layout.fragment_menu
    override val viewModel: MenuViewModel by viewModels {
        SavedStateViewModelFactory(menuViewModelFactory, this)
    }

    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {}

    private val diffCallback = MenuDiffCallback()
    private val menuAdapter by lazy { DelegationAdapter(diffCallback) }

    override fun setupViews() {
        initAdapter()

        viewModel.observeMenuItems(viewLifecycleOwner) {
            menuAdapter.items = it
        }
    }

    private fun initAdapter() {
        menuAdapter.delegatesManager.addDelegate(MenuDelegate {
            main.drawer_layout.closeDrawers()
            viewModel.navigate(NavigationCommand.To(it))
        })
        rv_menu.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = menuAdapter
        }

        viewModel.getMenuItems()
    }
}