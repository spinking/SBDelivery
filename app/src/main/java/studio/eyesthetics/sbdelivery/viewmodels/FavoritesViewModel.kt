package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.data.database.entities.DishItem
import studio.eyesthetics.sbdelivery.data.models.favorites.FavoriteChangeRequest
import studio.eyesthetics.sbdelivery.data.repositories.dishes.IDishesRepository
import studio.eyesthetics.sbdelivery.data.repositories.favorite.IFavoriteRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class FavoritesViewModel(
    handle: SavedStateHandle,
    private val favoriteRepository: IFavoriteRepository,
    private val dishesRepository: IDishesRepository
) : BaseViewModel<FavoritesState>(handle, FavoritesState()) {

    private val favoriteDishes = dishesRepository.getFavoriteDishes()

    fun observeFavoriteDishes(owner: LifecycleOwner, onChange: (List<DishItem>) -> Unit) {
        favoriteDishes.observe(owner, Observer { onChange(it) })
    }

    fun handleFavorite(dishId: String, isChecked: Boolean) {
        launchSafety {
            favoriteRepository.changeToFavorite(FavoriteChangeRequest(dishId, isChecked))
        }
    }
}

class FavoritesViewModelFactory @Inject constructor(
    private val favoriteRepository: IFavoriteRepository,
    private val dishesRepository: IDishesRepository
) : IViewModelFactory<FavoritesViewModel> {
    override fun create(handle: SavedStateHandle): FavoritesViewModel {
        return FavoritesViewModel(handle, favoriteRepository, dishesRepository)
    }
}

class FavoritesState : IViewModelState