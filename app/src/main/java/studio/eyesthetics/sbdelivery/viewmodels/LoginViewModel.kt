package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.data.models.auth.LoginRequest
import studio.eyesthetics.sbdelivery.data.repositories.auth.IAuthRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class LoginViewModel(
    handle: SavedStateHandle,
    private val authRepository: IAuthRepository
) : BaseViewModel<LoginState>(handle, LoginState()) {

    fun login(login: String, password: String) {
        launchSafety {
            authRepository.login(LoginRequest(login, password))
            //TODO sync favorite dishes
            //TODO transition to previous destination
        }
    }
}

class LoginViewModelFactory @Inject constructor(
    private val authRepository: IAuthRepository
) : IViewModelFactory<LoginViewModel> {
    override fun create(handle: SavedStateHandle): LoginViewModel {
        return LoginViewModel(handle, authRepository)
    }
}

class LoginState : IViewModelState