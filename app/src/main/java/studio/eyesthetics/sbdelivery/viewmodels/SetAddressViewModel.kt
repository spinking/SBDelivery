package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class SetAddressViewModel(
    handle: SavedStateHandle
) : BaseViewModel<SetAddressState>(handle, SetAddressState()) {

    fun handleChangeAddress(query: String) {
        updateState { it.copy(address = query) }
        launchSafety {
            //TODO check address
        }
    }
}

class SetAddressViewModelFactory @Inject constructor(

) : IViewModelFactory<SetAddressViewModel> {
    override fun create(handle: SavedStateHandle): SetAddressViewModel {
        return SetAddressViewModel(handle)
    }
}

data class SetAddressState(
    val address: String = ""
) : IViewModelState