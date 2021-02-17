package studio.eyesthetics.sbdelivery.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import studio.eyesthetics.sbdelivery.data.network.errors.TokenInvalidError
import studio.eyesthetics.sbdelivery.data.storage.Pref

class ModifierInterceptor (
    private val pref: Pref
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        try {
            //TODO remove mock modifier date
            request = request.newBuilder().addHeader("If-Modified-Since", "2020-04-20T13:40:15.904+03:00").build()
        } catch (e: IllegalArgumentException) {
            throw TokenInvalidError(e.message ?: "")
        }
        return chain.proceed(request)
    }
}