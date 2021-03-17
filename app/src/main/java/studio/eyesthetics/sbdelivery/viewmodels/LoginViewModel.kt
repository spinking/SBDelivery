package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.Notify
import javax.inject.Inject

class LoginViewModel(
    handle: SavedStateHandle
) : BaseViewModel<LoginState>(handle, LoginState()) {

    fun showMessage(message: String) {
        notify(Notify.TextMessage(message))
    }
}

class LoginViewModelFactory @Inject constructor(

) : IViewModelFactory<LoginViewModel> {
    override fun create(handle: SavedStateHandle): LoginViewModel {
        return LoginViewModel(handle)
    }
}

class LoginState : IViewModelState