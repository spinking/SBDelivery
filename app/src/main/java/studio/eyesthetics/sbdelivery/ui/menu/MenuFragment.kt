package studio.eyesthetics.sbdelivery.ui.menu

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.nav_header.*
import studio.eyesthetics.sbdelivery.App
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.MenuDelegate
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks.MenuDiffCallback
import studio.eyesthetics.sbdelivery.ui.base.BaseFragment
import studio.eyesthetics.sbdelivery.ui.base.Binding
import studio.eyesthetics.sbdelivery.ui.base.DelegationAdapter
import studio.eyesthetics.sbdelivery.ui.base.ToolbarBuilder
import studio.eyesthetics.sbdelivery.ui.delegates.RenderProp
import studio.eyesthetics.sbdelivery.viewmodels.MenuState
import studio.eyesthetics.sbdelivery.viewmodels.MenuViewModel
import studio.eyesthetics.sbdelivery.viewmodels.MenuViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
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
    override val binding: MenuBinding by lazy { MenuBinding() }
    override val prepareToolbar: (ToolbarBuilder.() -> Unit) = {}

    private val diffCallback = MenuDiffCallback()
    private val menuAdapter by lazy { DelegationAdapter(diffCallback) }

    override fun setupViews() {
        initViews()
        initAdapter()

        viewModel.observeMenuItems(viewLifecycleOwner) {
            menuAdapter.items = it
        }
    }

    private fun initViews() {
        iv_logout.setOnClickListener {
            viewModel.handleLogout(getString(R.string.menu_dialog_description), "", {})
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

    inner class MenuBinding : Binding() {
        private var isAuth: Boolean by RenderProp(false) {
            iv_logout.isVisible = it
        }
        private var name: String by RenderProp("") {
            tv_name.text = it
        }
        private var email: String by RenderProp("") {
            tv_email.text = it
        }
        override fun bind(data: IViewModelState) {
            data as MenuState
            isAuth = data.isAuth
            name = data.name
            email = data.email
        }
    }
}