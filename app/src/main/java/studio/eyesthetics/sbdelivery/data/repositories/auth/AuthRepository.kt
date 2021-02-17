package studio.eyesthetics.sbdelivery.data.repositories.auth

import studio.eyesthetics.sbdelivery.data.models.auth.*
import studio.eyesthetics.sbdelivery.data.network.IAuthApi
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: IAuthApi
) : IAuthRepository {
    override suspend fun login(loginRequest: LoginRequest) {
        authApi.login(loginRequest)
    }

    override suspend fun registration(registrationRequest: RegistrationRequest) {
        authApi.register(registrationRequest)
    }

    override suspend fun recoverySendEmail(recoveryEmailRequest: RecoveryEmailRequest) {
        authApi.recoveryByEmail(recoveryEmailRequest)
    }

    override suspend fun recoverySendCode(recoveryCodeRequest: RecoveryCodeRequest) {
        authApi.checkRecoveryCode(recoveryCodeRequest)
    }

    override suspend fun recoverySendPassword(recoveryPasswordRequest: RecoveryPasswordRequest) {
        authApi.changePassword(recoveryPasswordRequest)
    }
}