package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.data.models.auth.RecoveryEmailRequest
import studio.eyesthetics.sbdelivery.data.repositories.auth.IAuthRepository
import studio.eyesthetics.sbdelivery.extensions.isValidEmail
import studio.eyesthetics.sbdelivery.ui.auth.RecoveryPasswordFragmentDirections
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import javax.inject.Inject

class RecoveryPasswordViewModel(
    handle: SavedStateHandle,
    private val authRepository: IAuthRepository
) : BaseViewModel<RecoveryPasswordState>(handle, RecoveryPasswordState()) {

    fun recoverySendEmail(email: String) {
        if (email.isValidEmail())
            launchSafety {
                authRepository.recoverySendEmail(RecoveryEmailRequest(email))
                val destination = RecoveryPasswordFragmentDirections.actionRecoveryPasswordFragmentToRecoveryCodeFragment()
                navigate(NavigationCommand.To(destination.actionId))
            }
    }
}

class RecoveryPasswordViewModelFactory @Inject constructor(
    private val authRepository: IAuthRepository
) : IViewModelFactory<RecoveryPasswordViewModel> {
    override fun create(handle: SavedStateHandle): RecoveryPasswordViewModel {
        return RecoveryPasswordViewModel(handle, authRepository)
    }
}

class RecoveryPasswordState : IViewModelState