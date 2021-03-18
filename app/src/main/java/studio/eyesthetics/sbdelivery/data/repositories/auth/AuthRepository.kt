package studio.eyesthetics.sbdelivery.data.repositories.auth

import androidx.lifecycle.LiveData
import studio.eyesthetics.sbdelivery.data.mappers.LoginResponseToProfileMapper
import studio.eyesthetics.sbdelivery.data.models.auth.*
import studio.eyesthetics.sbdelivery.data.network.IAuthApi
import studio.eyesthetics.sbdelivery.data.storage.Pref
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: IAuthApi,
    private val pref: Pref,
    private val profileMapper: LoginResponseToProfileMapper
) : IAuthRepository {
    override suspend fun login(loginRequest: LoginRequest) {
        val response = authApi.login(loginRequest)
        pref.apply {
            profile = profileMapper.mapFromEntity(response)
            accessToken = response.accessToken
            refreshToken = response.refreshToken
        }
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

    override fun isAuth(): LiveData<Boolean> = pref.isAuthLive
}