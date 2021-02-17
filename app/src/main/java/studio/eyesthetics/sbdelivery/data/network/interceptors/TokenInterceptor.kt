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
            request = request.newBuilder().addHeader("Authorization", /*pref.accessToken*/"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjYwMmNlNGE1YmQ5N2UwMDAzYzRiNmE5NiIsImlhdCI6MTYxMzU1NDg1MywiZXhwIjoxNjEzNTU2MDUzfQ.SvbFY44ZsJRqFOOn-0FgilyFqSlUxgqn9_31T4tw8UY").build()
        } catch (e: IllegalArgumentException) {
            throw TokenInvalidError(e.message ?: "")
        }
        return chain.proceed(request)
    }
}