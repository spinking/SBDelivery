package studio.eyesthetics.sbdelivery.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.extensions.visible
import studio.eyesthetics.sbdelivery.viewmodels.base.*

abstract class BaseActivity<T : BaseViewModel<out IViewModelState>> : AppCompatActivity() {
    protected abstract val viewModel: T
    protected abstract val layout: Int
    lateinit var navController: NavController

    val toolbarBuilder = ToolbarBuilder()

    abstract fun subscribeOnState(state: IViewModelState)

    abstract fun renderNotification(notify: Notify)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(layout)
        setSupportActionBar(toolbar)
        viewModel.observeState(this) { subscribeOnState(it) }
        viewModel.observeNotifications(this) { renderNotification(it) }
        viewModel.observeNavigation(this) { subscribeOnNavigation(it) }
        viewModel.observeLoading(this) { renderLoading(it) }

        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.saveState()
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        viewModel.restoreState()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    open fun renderLoading(loadingState: Loading) {
        when(loadingState) {
            Loading.SHOW_LOADING -> progress.isVisible = true
            Loading.SHOW_BLOCKING_LOADING -> {
                progress.isVisible = true
                //TODO block interact with UI
            }
            Loading.HIDE_LOADING -> progress.isVisible = false
        }
    }

    private fun subscribeOnNavigation(command: NavigationCommand) {
        when (command) {
            is NavigationCommand.To -> {
                navController.navigate(
                    command.destination,
                    command.args,
                    command.options,
                    command.extras
                )
            }
            is NavigationCommand.FinishLogin -> {
                navController.navigate(R.id.finish_login)
                command.privateDestination?.let { navController.navigate(it) }
            }
            is NavigationCommand.StartLogin -> {
                navController.navigate(
                    R.id.start_login,
                    bundleOf("private_destination" to (command.privateDestination ?: -1))
                )
            }
        }
    }
}

class ToolbarBuilder {
    var title: String? = null
    var isHomeBackground: Boolean = true
    var isBackButtonVisible: Boolean = true
    var visibility: Boolean = true
    val items: MutableList<MenuItemHolder> = mutableListOf()
    private val views = mutableListOf<Int>()
    private val tempViews = mutableListOf<Int>()

    fun setTitle(title: String): ToolbarBuilder {
        this.title = title
        return this
    }

    fun setHomeBackground(isHomeBackground: Boolean): ToolbarBuilder {
        this.isHomeBackground = isHomeBackground
        return this
    }

    fun setBackButtonVisible(isVisible: Boolean): ToolbarBuilder {
        this.isBackButtonVisible = isVisible
        return this
    }

    fun addMenuItem(item: MenuItemHolder): ToolbarBuilder {
        this.items.add(item)
        return this
    }

    fun invalidate(): ToolbarBuilder {
        this.title = null
        this.isHomeBackground = false
        this.isBackButtonVisible = true
        this.visibility = true
        this.items.clear()
        views.clear()
        return this
    }

    fun addView(layoutId: Int): ToolbarBuilder {
        views.add(layoutId)
        return this
    }

    fun prepare(prepareFn: (ToolbarBuilder.() -> Unit)?): ToolbarBuilder {
        prepareFn?.invoke(this)
        return this
    }

    fun build(context: FragmentActivity) {

        with(context.toolbar) {
            toolbar.visible(this@ToolbarBuilder.visibility)

            if (this@ToolbarBuilder.visibility) {
                if (this@ToolbarBuilder.title != null) {
                    this.title = this@ToolbarBuilder.title
                }
                if (this@ToolbarBuilder.isBackButtonVisible.not()) {
                    this.navigationIcon = null
                } else {
                    this.navigationIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_back, null)
                }
/*                val backgroundId = if (this@ToolbarBuilder.isHomeBackground)
                    R.color.colorWhite
                else
                    R.drawable.background_toolbar*/

                //this.background = ResourcesCompat.getDrawable(resources, backgroundId, null)
            }
        }

        //remove temp views
        if (tempViews.isNotEmpty()) {
            tempViews.forEach {
                val view = context.container.findViewById<View>(it)
                context.container.removeView(view)
            }
            tempViews.clear()
        }

        //add new toolbar bar views
        if (views.isNotEmpty()) {
            val inflater = LayoutInflater.from(context)
            views.forEach {
                val view = inflater.inflate(it, context.container, false)
                context.container.addView(view)
                tempViews.add(view.id)
            }
        }
    }
}

data class MenuItemHolder(
    val title: String,
    val menuId: Int,
    val icon: Int? = null,
    val actionViewLayout: Int? = null,
    val clickListener: ((MenuItem) -> Unit)? = null
)