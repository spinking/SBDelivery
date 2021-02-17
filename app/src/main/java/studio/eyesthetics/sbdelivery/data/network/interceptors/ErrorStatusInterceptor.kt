package studio.eyesthetics.sbdelivery.data.network.interceptors

import com.google.gson.JsonParseException
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.Response
import studio.eyesthetics.sbdelivery.data.network.errors.ApiError
import studio.eyesthetics.sbdelivery.data.network.errors.Error
import studio.eyesthetics.sbdelivery.data.network.errors.ErrorBody
import studio.eyesthetics.sbdelivery.data.storage.Pref
import javax.inject.Inject

class ErrorStatusInterceptor @Inject constructor(
    private val moshi: Moshi,
    private val pref: Pref
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val res = chain.proceed(chain.request())

        if (res.isSuccessful) return res

        var error = try {
            moshi.adapter(Error::class.java).fromJson(res.body!!.string())?.error
        } catch (e: JsonParseException) {
            ErrorBody(
                "Undefiled",
                e.message ?: "Undefined",
                ""
            )
        }

        if(error == null) error = ErrorBody(
            "Undefiled",
            "Undefined",
            ""
        )

        when (res.code) {
            400 -> throw ApiError.BadRequest("${error.message}, ${error.detail}")
            401 -> {
                //pref.accessToken = ""
                throw ApiError.Unauthorized("${error.message}, ${error.detail}")
            }
            403 -> {
                //pref.accessToken = ""
                throw ApiError.Forbidden("${error.message}, ${error.detail}")
            }
            404 -> throw ApiError.NotFound("${error.message}, ${error.detail}")
            409 -> throw ApiError.NotUniqueEmail("${error.message}, ${error.detail}")
            500 -> throw ApiError.InternalServerError("${error.message}, ${error.detail}")
            else -> throw ApiError.UnknownError("${error.message}, ${error.detail}")
        }
    }
}