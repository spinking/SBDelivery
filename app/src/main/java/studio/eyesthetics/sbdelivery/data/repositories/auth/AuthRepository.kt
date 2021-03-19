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

    override suspend fun recoverySendCode(recoveryCode: String) {
        pref.recoveryCode = recoveryCode
        authApi.checkRecoveryCode(RecoveryCodeRequest(pref.profile?.email ?: "", recoveryCode))
    }

    override suspend fun recoverySendPassword(newPassword: String) {
        authApi.changePassword(RecoveryPasswordRequest(
            pref.profile?.email ?: "",
            pref.recoveryCode,
            newPassword
        ))
    }

    override fun isAuth(): LiveData<Boolean> = pref.isAuthLive
}