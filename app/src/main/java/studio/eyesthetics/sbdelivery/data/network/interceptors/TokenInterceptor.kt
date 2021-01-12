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
            request = request.newBuilder().addHeader("token", /*pref.accessToken*/"").build()
        } catch (e: IllegalArgumentException) {
            throw TokenInvalidError(e.message ?: "")
        }
        return chain.proceed(request)
    }
}