package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.*
import studio.eyesthetics.sbdelivery.data.database.entities.Basket
import studio.eyesthetics.sbdelivery.data.repositories.auth.IAuthRepository
import studio.eyesthetics.sbdelivery.data.repositories.basket.IBasketRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class BasketViewModel(
    handle: SavedStateHandle,
    private val basketRepository: IBasketRepository,
    private val authRepository: IAuthRepository
) : BaseViewModel<BasketState>(handle, BasketState()) {

    init {
        subscribeOnDataSource(authRepository.isAuth()) { isAuth, state ->
            if (isAuth) loadBasketFromNetwork()
            state.copy(isAuth = isAuth)
        }
    }

    private val basket: LiveData<Basket> = basketRepository.getCachedBasket()

    private fun loadBasketFromNetwork() {
        launchSafety {
            loadBasketFromNetwork()
        }
    }

    fun observeBasket(owner: LifecycleOwner, onChange: (Basket) -> Unit) {
        basket.observe(owner, Observer { if (it != null) onChange(it) else updateState { state -> state.copy(isEmptyBasket = true) } })
    }

    fun handleEmptyBasket(isEmpty: Boolean) {
        updateState { it.copy(isEmptyBasket = isEmpty) }
    }

}

class BasketViewModelFactory @Inject constructor(
    private val basketRepository: IBasketRepository,
    private val authRepository: IAuthRepository
) : IViewModelFactory<BasketViewModel> {
    override fun create(handle: SavedStateHandle): BasketViewModel {
        return BasketViewModel(handle, basketRepository, authRepository)
    }
}

data class BasketState(
    val isAuth: Boolean = false,
    val isEmptyBasket: Boolean = false
) : IViewModelState