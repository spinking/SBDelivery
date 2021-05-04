package studio.eyesthetics.sbdelivery.data.network.errors

import java.io.IOException

sealed class ApiError(override val message:String): IOException(message) {
    class BadRequest(message: String?) : ApiError(message?: "Bad Request")
    class Unauthorized(message: String?) : ApiError(message ?:"Authorization token required")
    class Forbidden(message: String?) : ApiError(message?:"Access denied")
    class NotFound(message: String? ) : ApiError(message ?: "Not found")
    class InternalServerError(message: String?) : ApiError(message ?: "Internal server error")
    class UnknownError(message: String?) : ApiError(message ?: "Unknown error" )
    class NotUniqueEmail(message: String?) : ApiError("Not unique email")
    class BasketNotEquals(message: String?) : ApiError(message ?: "Корзина была изменена")
}

data class Error(
    val error: ErrorBody
)

data class ErrorBody(
    val status: String,
    val message:String,
    val detail: String
)