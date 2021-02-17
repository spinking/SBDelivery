package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.models.auth.RegistrationRequest
import studio.eyesthetics.sbdelivery.data.repositories.auth.IAuthRepository
import studio.eyesthetics.sbdelivery.extensions.isValidEmail
import studio.eyesthetics.sbdelivery.extensions.isValidName
import studio.eyesthetics.sbdelivery.viewmodels.base.*
import javax.inject.Inject

class RegistrationViewModel(
    handle: SavedStateHandle,
    private val authRepository: IAuthRepository
) : BaseViewModel<RegistrationState>(handle, RegistrationState()) {

    fun handleValidData(registrationRequest: RegistrationRequest) {
        updateState { it.copy(
            isNotEmptyName = registrationRequest.firstName.isNotEmpty(),
            isNotEmptySurname = registrationRequest.lastName.isNotEmpty(),
            isNotEmptyEmail = registrationRequest.email.isNotEmpty(),
            isNotEmptyPassword = registrationRequest.password.isNotEmpty(),
            isValidName = true,
            isValidSurname = true,
            isValidEmail = true
        ) }
        activateButton()

        if (currentState.buttonIsActivated) {
            updateState { it.copy(
                isValidName = registrationRequest.firstName.isValidName(),
                isValidSurname = registrationRequest.lastName.isValidName(),
                isValidEmail = registrationRequest.email.isValidEmail()
            ) }

            if (currentState.isValidName && currentState.isValidSurname && currentState.isValidEmail) {
                postRegistration(registrationRequest)
            } else {
                updateState { it.copy(buttonIsActivated = false) }
            }
        }
    }

    fun handleEmptyName(isNotEmpty: Boolean) {
        updateState { it.copy(isNotEmptyName = isNotEmpty) }
        activateButton()
    }

    fun handleEmptySurname(isNotEmpty: Boolean) {
        updateState { it.copy(isNotEmptySurname = isNotEmpty) }
        activateButton()
    }

    fun handleEmptyEmail(isNotEmpty: Boolean) {
        updateState { it.copy(isNotEmptyEmail = isNotEmpty) }
        activateButton()
    }

    fun handleEmptyPassword(isNotEmpty: Boolean) {
        updateState { it.copy(isNotEmptyPassword = isNotEmpty) }
        activateButton()
    }

    private fun postRegistration(registrationRequest: RegistrationRequest) {
        if (currentState.buttonIsActivated)
            launchSafety {
                authRepository.registration(registrationRequest)
                navigate(NavigationCommand.To(R.id.loginFragment))
            }
    }

    private fun activateButton() {
        updateState { it.copy(buttonIsActivated = currentState.isNotEmptyName &&
                currentState.isNotEmptySurname &&
                currentState.isNotEmptyEmail &&
                currentState.isNotEmptyPassword
        ) }
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
    val isNotEmptyName: Boolean = true,
    val isNotEmptySurname: Boolean = true,
    val isNotEmptyEmail: Boolean = true,
    val isNotEmptyPassword: Boolean = true,
    val isValidEmail: Boolean = true,
    val isValidName: Boolean = true,
    val isValidSurname: Boolean = true,
    val buttonIsActivated: Boolean = true
) : IViewModelState