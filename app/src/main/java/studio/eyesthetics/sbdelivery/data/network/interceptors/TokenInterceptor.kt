package studio.eyesthetics.sbdelivery.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import studio.eyesthetics.sbdelivery.data.network.errors.TokenInvalidError
import studio.eyesthetics.sbdelivery.data.storage.Pref

class TokenInterceptor (
    private val pref: Pref
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        try {
            request = request.newBuilder().addHeader("Authorization", /*pref.accessToken*/"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwMmMwYWVjYmQ5N2UwMDAzYzRiNmE5MyIsImlhdCI6MTYxMzQ5OTExNiwiZXhwIjoxNjEzNTAwMzE2fQ.Gjor8Yb-hRD57zShOKIvF8iX0nNyA3iWbcx9b9jhgoo").build()
        } catch (e: IllegalArgumentException) {
            throw TokenInvalidError(e.message ?: "")
        }
        return chain.proceed(request)
    }
}