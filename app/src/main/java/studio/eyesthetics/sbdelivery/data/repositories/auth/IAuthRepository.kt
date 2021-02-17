package studio.eyesthetics.sbdelivery.data.repositories.auth

import retrofit2.http.POST
import studio.eyesthetics.sbdelivery.data.models.auth.*

interface IAuthRepository {
    suspend fun login(loginRequest: LoginRequest)
    suspend fun registration(registrationRequest: RegistrationRequest)
    suspend fun recoverySendEmail(recoveryEmailRequest: RecoveryEmailRequest)
    suspend fun recoverySendCode(recoveryCodeRequest: RecoveryCodeRequest)
    suspend fun recoverySendPassword(recoveryPasswordRequest: RecoveryPasswordRequest)
}