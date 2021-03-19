package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.data.repositories.auth.IAuthRepository
import studio.eyesthetics.sbdelivery.data.repositories.dishes.IDishesRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import javax.inject.Inject

class MainViewModel(
    private val handle: SavedStateHandle,
    private val dishesRepository: IDishesRepository,
    private val authRepository: IAuthRepository
) : BaseViewModel<RootState>(handle, RootState()) {
    //TODO set destinations that needs authorization
    private val privateRoutes = listOf<Int>()

    init {
        subscribeOnDataSource(authRepository.isAuth()) { isAuth, currentState ->
            currentState.copy(isAuth = isAuth)
        }

        launchSafety {
            dishesRepository.loadRecommendIdsFromNetwork()
        }
    }

    override fun navigate(navigationCommand: NavigationCommand) {
        when (navigationCommand) {
            is NavigationCommand.To -> {
                if (privateRoutes.contains(navigationCommand.destination) && currentState.isAuth.not()) {
                    super.navigate(NavigationCommand.StartLogin(navigationCommand.destination))
                } else {
                    super.navigate(navigationCommand)
                }
            }
            else -> super.navigate(navigationCommand)
        }
    }
}

class MainViewModelFactory @Inject constructor(
    private val dishesRepository: IDishesRepository,
    private val authRepository: IAuthRepository
) : IViewModelFactory<MainViewModel> {
    override fun create(handle: SavedStateHandle): MainViewModel {
        return MainViewModel(handle, dishesRepository, authRepository)
    }
}

data class RootState(
    val isAuth: Boolean = false
) : IViewModelState