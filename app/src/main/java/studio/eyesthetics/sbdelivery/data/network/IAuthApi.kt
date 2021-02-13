package studio.eyesthetics.sbdelivery.data.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import studio.eyesthetics.sbdelivery.data.models.auth.EnterRequest
import studio.eyesthetics.sbdelivery.data.models.auth.EnterResponse
import studio.eyesthetics.sbdelivery.data.models.auth.RefreshTokenRequest
import studio.eyesthetics.sbdelivery.data.models.auth.RefreshTokenResponse

interface IAuthApi {
    @POST("auth/login")
    suspend fun login(
        @Body enterRequest: EnterRequest
    ): EnterResponse

    @POST("auth/refresh")
    fun refreshToken(
        @Body refreshRequest: RefreshTokenRequest
    ): Call<RefreshTokenResponse>
}