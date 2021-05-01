package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.models.menu.MenuItem
import studio.eyesthetics.sbdelivery.extensions.mutableLiveData
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.Notify
import javax.inject.Inject

class MenuViewModel(
    handle: SavedStateHandle
) : BaseViewModel<MenuState>(handle, MenuState()) {

    init {
        //TODO get profile data and auth status
    }

    private val menuItems = mutableLiveData<List<MenuItem>>()

    fun observeMenuItems(owner: LifecycleOwner, onChange: (List<MenuItem>) -> Unit) {
        menuItems.observe(owner, Observer { onChange(it) })
    }

    fun getMenuItems() {
        launchSafety {
            //TODO get info for badges
            menuItems.value = createMenuItems()
        }
    }

    private fun createMenuItems(): List<MenuItem> {
        return listOf(
            MenuItem(
                R.string.menu_home,
                R.drawable.ic_home,
                -1,
                R.id.homeFragment
            ),
            MenuItem(
                R.string.menu_dishes,
                R.drawable.ic_bowl_full_of_food,
                -1,
                R.id.categoriesFragment
            ),
            MenuItem(
                R.string.menu_favorite,
                R.drawable.ic_heart,
                -1,
                R.id.favoritesFragment
            ),
            MenuItem(
                R.string.menu_basket,
                R.drawable.ic_basket,
                -1,
                R.id.basketFragment
            ),
            MenuItem(
                R.string.menu_profile,
                R.drawable.ic_user,
                -1,
                R.id.homeFragment
            ),
            MenuItem(
                R.string.menu_orders,
                R.drawable.ic_list,
                -1,
                R.id.homeFragment
            ),
            MenuItem(
                R.string.menu_notifications,
                R.drawable.ic_bell,
                -1,
                R.id.homeFragment
            )
        )
    }

    fun handleLogout(message: String, label: String, actionHandler: () -> Unit) {
        notify(Notify.DialogMessage(message, label, actionHandler))
    }
}

class MenuViewModelFactory @Inject constructor(

) : IViewModelFactory<MenuViewModel> {
    override fun create(handle: SavedStateHandle): MenuViewModel {
        return MenuViewModel(handle)
    }
}

data class MenuState(
    val isAuth: Boolean = false,
    val name: String = "",
    val email: String = ""
) : IViewModelState