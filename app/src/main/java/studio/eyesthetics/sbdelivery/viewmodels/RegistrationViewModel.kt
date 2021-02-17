package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.models.auth.RegistrationRequest
import studio.eyesthetics.sbdelivery.data.repositories.auth.IAuthRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.*
import javax.inject.Inject

class RegistrationViewModel(
    handle: SavedStateHandle,
    private val authRepository: IAuthRepository
) : BaseViewModel<RegistrationState>(handle, RegistrationState()) {

    fun postRegistration(registrationRequest: RegistrationRequest) {
        if (currentState.buttonIsActivated)
            launchSafety {
                authRepository.registration(registrationRequest)
                navigate(NavigationCommand.To(R.id.loginFragment))
            }
    }

    fun handleValidEmail(isValid: Boolean) {
        updateState { it.copy(isValidEmail = isValid, buttonIsActivated = isValid && currentState.isValidPassword && currentState.isValidName && currentState.isValidSurname) }
    }

    fun handleValidPassword(isValid: Boolean) {
        updateState { it.copy(isValidPassword = isValid, buttonIsActivated = currentState.isValidEmail && isValid && currentState.isValidName && currentState.isValidSurname) }
    }

    fun handleValidName(isValid: Boolean) {
        updateState { it.copy(isValidName = isValid, buttonIsActivated = currentState.isValidEmail && currentState.isValidPassword && currentState.isValidSurname && isValid) }
    }

    fun handleValidSurname(isValid: Boolean) {
        updateState { it.copy(isValidSurname = isValid, buttonIsActivated = currentState.isValidEmail && currentState.isValidPassword && currentState.isValidName && isValid) }
    }
}

class RegistrationViewModelFactory @Inject constructor(
    private val authRepository: IAuthRepository
) : IViewModelFactory<RegistrationViewModel> {
    override fun create(handle: SavedStateHandle) : RegistrationViewModel {
        return RegistrationViewModel(handle, authRepository)
    }
}

data class RegistrationState(
    val isValidEmail: Boolean = false,
    val isValidPassword: Boolean = false,
    val isValidName: Boolean = false,
    val isValidSurname: Boolean = false,
    val buttonIsActivated: Boolean = false
) : IViewModelState