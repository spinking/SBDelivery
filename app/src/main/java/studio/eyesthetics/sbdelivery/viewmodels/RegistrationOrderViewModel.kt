package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.data.models.orders.CreateOrderRequest
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class RegistrationOrderViewModel(
    handle: SavedStateHandle
) : BaseViewModel<RegistrationOrderState>(handle, RegistrationOrderState()) {
    fun handleCreateOrder(createOrderRequest: CreateOrderRequest) {
        launchSafety(isShowBlockingLoading = true) {
            //TODO create order
        }
    }

    fun handleChangeAddress(address: String) {
        updateState { it.copy(address = address) }
    }
}

class RegistrationOrderViewModelFactory @Inject constructor(

) : IViewModelFactory<RegistrationOrderViewModel> {
    override fun create(handle: SavedStateHandle): RegistrationOrderViewModel {
        return RegistrationOrderViewModel(handle)
    }
}

data class RegistrationOrderState(
    val isOrderButtonEnabled: Boolean = false,
    val address: String = ""
) : IViewModelState