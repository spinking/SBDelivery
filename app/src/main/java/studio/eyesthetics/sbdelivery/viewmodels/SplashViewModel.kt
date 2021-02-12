package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.data.repositories.categories.ICategoryRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class SplashViewModel(
    handle: SavedStateHandle,
    private val categoryRepository: ICategoryRepository
) : BaseViewModel<SplashState>(handle, SplashState()) {

    init {
        //TODO start work manager
        launchSafety {
            categoryRepository.loadsCategoriesFromNetwork(0, 10)
        }
    }
}

class SplashViewModelFactory @Inject constructor(
    private val categoryRepository: ICategoryRepository
) : IViewModelFactory<SplashViewModel> {
    override fun create(handle: SavedStateHandle): SplashViewModel {
        return SplashViewModel(handle, categoryRepository)
    }
}

class SplashState : IViewModelState