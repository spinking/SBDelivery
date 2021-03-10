package studio.eyesthetics.sbdelivery.ui.base

import android.os.Bundle
import android.view.*
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.ui.MainActivity
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.Loading
import studio.eyesthetics.sbdelivery.viewmodels.base.Notify

abstract class BaseComposeFragment<T : BaseViewModel<out IViewModelState>> : Fragment() {
    val main: MainActivity
        get() = activity as MainActivity
    open val binding: Binding? = null
    protected abstract val viewModel: T
    open val applicationBackground: Int = R.color.color_secondary

    open val prepareToolbar: (ToolbarBuilder.() -> Unit)? = null

    val toolbar
        get() = main.toolbar

    abstract fun setupViews()
    abstract fun setupLayout(): ComposeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = setupLayout()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //restore state
        viewModel.restoreState()

        //owner it is view
        viewModel.observeState(viewLifecycleOwner) { binding?.bind(it) }
        //bind default values if viewmodel not loaded data
        if(binding?.isInflated == false) binding?.onFinishInflate()

        viewModel.observeNotifications(viewLifecycleOwner) { main.renderNotification(it) }
        viewModel.observeNavigation(viewLifecycleOwner) { main.viewModel.navigate(it) }
        viewModel.observeLoading(viewLifecycleOwner) { renderLoading(it) }
        binding?.restoreUi(savedInstanceState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        main.toolbarBuilder
            .invalidate()
            .prepare(prepareToolbar)
            .build(main)

        toolbar.setNavigationOnClickListener {
            main.navController.popBackStack()
        }
        updateApplicationBackground(applicationBackground)
        setupViews()

        binding?.rebind()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveState()
        binding?.saveUi(outState)
        super.onSaveInstanceState(outState)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if(main.toolbarBuilder.items.isNotEmpty()) {
            for((index, menuHolder) in main.toolbarBuilder.items.withIndex()) {
                val item = menu.add(0, menuHolder.menuId, index, menuHolder.title)
                item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS or MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW)
                    .setOnMenuItemClickListener {
                        menuHolder.clickListener?.invoke(it)?.let { true } ?: false
                    }
                if (menuHolder.icon != null)
                    item.setIcon(menuHolder.icon)
                if(menuHolder.actionViewLayout != null) item.setActionView(menuHolder.actionViewLayout)
            }
        } else menu.clear()
        super.onPrepareOptionsMenu(menu)
    }

    open fun renderNotification(notify: Notify) {
        main.renderNotification(notify)
    }

    private fun updateApplicationBackground(resourceId: Int = R.color.color_secondary) {
        main.container.background = ResourcesCompat.getDrawable(resources, resourceId, null)
    }

    private fun renderLoading(loadingState: Loading) {
        main.renderLoading(loadingState)
    }
}