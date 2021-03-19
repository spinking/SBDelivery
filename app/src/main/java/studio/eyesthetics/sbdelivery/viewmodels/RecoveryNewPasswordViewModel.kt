package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.repositories.auth.IAuthRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import javax.inject.Inject

class RecoveryNewPasswordViewModel(
    handle: SavedStateHandle,
    private val authRepository: IAuthRepository
) : BaseViewModel<RecoveryNewPasswordState>(handle, RecoveryNewPasswordState()) {

    fun recoveryPassword(newPassword: String, repeatedPassword: String) {
        if (newPassword == repeatedPassword)
            launchSafety {
                authRepository.recoverySendPassword(newPassword)
                navigate(NavigationCommand.PopUpToDestination(R.id.loginFragment))
            }
    }
}

class RecoveryNewPasswordViewModelFactory @Inject constructor(
    private val authRepository: IAuthRepository
) : IViewModelFactory<RecoveryNewPasswordViewModel> {
    override fun create(handle: SavedStateHandle): RecoveryNewPasswordViewModel {
        return RecoveryNewPasswordViewModel(handle, authRepository)
    }
}

class RecoveryNewPasswordState : IViewModelState