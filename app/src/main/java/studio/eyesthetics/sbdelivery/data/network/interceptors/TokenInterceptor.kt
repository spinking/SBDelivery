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
            request = request.newBuilder().addHeader("Authorization", /*pref.accessToken*/"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwMmNkYTI5YmQ5N2UwMDAzYzRiNmE5NCIsImlhdCI6MTYxMzU1MjE2OSwiZXhwIjoxNjEzNTUzMzY5fQ.gBNQTgRJ4wGhB2NJP6iTL2YAmY5-dwua5iK_6oqpOWY").build()
        } catch (e: IllegalArgumentException) {
            throw TokenInvalidError(e.message ?: "")
        }
        return chain.proceed(request)
    }
}