package studio.eyesthetics.sbdelivery.data.network.interceptors

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import studio.eyesthetics.sbdelivery.data.models.auth.RefreshTokenRequest
import studio.eyesthetics.sbdelivery.data.network.IAuthApi
import studio.eyesthetics.sbdelivery.data.storage.Pref
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val authApi: IAuthApi,
    private val pref: Pref
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            pref.accessToken = ""
            val refreshRes = authApi.refreshToken(RefreshTokenRequest(pref.refreshToken)).execute()
            return if (refreshRes.isSuccessful) {
                pref.accessToken = refreshRes.body()!!.accessToken
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${refreshRes.body()!!.accessToken}")
                    .build()
            } else {
                null
            }
        }
        return null
    }
}