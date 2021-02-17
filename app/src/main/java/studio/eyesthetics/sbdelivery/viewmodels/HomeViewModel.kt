package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.*
import studio.eyesthetics.sbdelivery.data.database.entities.DishItem
import studio.eyesthetics.sbdelivery.data.models.favorites.FavoriteChangeRequest
import studio.eyesthetics.sbdelivery.data.repositories.dishes.IDishesRepository
import studio.eyesthetics.sbdelivery.data.repositories.favorite.IFavoriteRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class HomeViewModel(
    handle: SavedStateHandle,
    private val dishesRepository: IDishesRepository,
    private val favoriteRepository: IFavoriteRepository
) : BaseViewModel<HomeState>(handle, HomeState()) {

    private val recommendDishes = dishesRepository.getRecommendDishes()
    private val bestDishes = dishesRepository.getBestDishes()
    private val popularDishes = dishesRepository.getPopularDishes()

    fun observeRecommend(owner: LifecycleOwner, onChange: (List<DishItem>) -> Unit) {
        recommendDishes.observe(owner, Observer { onChange(it) })
    }

    fun observeBest(owner: LifecycleOwner, onChange: (List<DishItem>) -> Unit) {
        bestDishes.observe(owner, Observer { onChange(it) })
    }

    fun observePopular(owner: LifecycleOwner, onChange: (List<DishItem>) -> Unit) {
        popularDishes.observe(owner, Observer { onChange(it) })
    }

    fun handleFavorite(dishId: String, isChecked: Boolean) {
        launchSafety {
            favoriteRepository.changeToFavorite(FavoriteChangeRequest(dishId, isChecked))
        }
    }
}

class HomeViewModelFactory @Inject constructor(
    private val dishesRepository: IDishesRepository,
    private val favoriteRepository: IFavoriteRepository
) : IViewModelFactory<HomeViewModel> {
    override fun create(handle: SavedStateHandle): HomeViewModel {
        return HomeViewModel(handle, dishesRepository, favoriteRepository)
    }
}

class HomeState : IViewModelState