package studio.eyesthetics.sbdelivery.data.network.errors

import java.lang.IllegalArgumentException

class TokenInvalidError(override val message: String) : IllegalArgumentException(message)