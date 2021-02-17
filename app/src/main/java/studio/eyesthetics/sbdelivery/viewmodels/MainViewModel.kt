package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.data.repositories.dishes.IDishesRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class MainViewModel(
    private val handle: SavedStateHandle,
    private val dishesRepository: IDishesRepository
) : BaseViewModel<RootState>(handle, RootState()) {

    init {
        launchSafety {
            dishesRepository.loadRecommendIdsFromNetwork()
        }
    }
}

class MainViewModelFactory @Inject constructor(
    private val dishesRepository: IDishesRepository
) : IViewModelFactory<MainViewModel> {
    override fun create(handle: SavedStateHandle): MainViewModel {
        return MainViewModel(handle, dishesRepository)
    }
}

data class RootState(
    val isAuth: Boolean = false
) : IViewModelState