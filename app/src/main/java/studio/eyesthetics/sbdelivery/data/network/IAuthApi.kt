package studio.eyesthetics.sbdelivery.data.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import studio.eyesthetics.sbdelivery.data.models.auth.*

interface IAuthApi {
    @POST("auth/login")
    suspend fun login(
        @Body enterRequest: EnterRequest
    ): EnterResponse

    @POST("auth/register")
    suspend fun register(
        @Body registrationRequest: RegistrationRequest
    ): RegistrationResponse

    @POST("auth/recovery/email")
    suspend fun recoveryByEmail(
        @Body recoveryEmailRequest: RecoveryEmailRequest
    )

    @POST("auth/recovery/code")
    suspend fun checkRecoveryCode(
        @Body recoveryCodeRequest: RecoveryCodeRequest
    )

    @POST("auth/recovery/password")
    suspend fun changePassword(
        @Body recoveryPasswordRequest: RecoveryPasswordRequest
    )

    @POST("auth/refresh")
    fun refreshToken(
        @Body refreshRequest: RefreshTokenRequest
    ): Call<RefreshTokenResponse>
}