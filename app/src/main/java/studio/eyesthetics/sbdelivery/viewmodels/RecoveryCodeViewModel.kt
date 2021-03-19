package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.data.repositories.auth.IAuthRepository
import studio.eyesthetics.sbdelivery.ui.auth.RecoveryCodeFragmentDirections
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import studio.eyesthetics.sbdelivery.viewmodels.base.NavigationCommand
import javax.inject.Inject

class RecoveryCodeViewModel(
    handle: SavedStateHandle,
    private val authRepository: IAuthRepository
) : BaseViewModel<RecoveryCodeState>(handle, RecoveryCodeState()) {

    fun sendRecoveryCode(recoveryCode: String) {
        launchSafety {
            authRepository.recoverySendCode(recoveryCode)
            val destination = RecoveryCodeFragmentDirections.actionRecoveryCodeFragmentToRecoveryNewPasswordFragment()
            navigate(NavigationCommand.To(destination.actionId))
        }
    }
}

class RecoveryCodeViewModelFactory @Inject constructor(
    private val authRepository: IAuthRepository
) : IViewModelFactory<RecoveryCodeViewModel> {
    override fun create(handle: SavedStateHandle): RecoveryCodeViewModel {
        return RecoveryCodeViewModel(handle, authRepository)
    }
}

class RecoveryCodeState : IViewModelState