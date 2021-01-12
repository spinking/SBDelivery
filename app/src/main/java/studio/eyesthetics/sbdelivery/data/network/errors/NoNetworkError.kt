package studio.eyesthetics.sbdelivery.data.network.errors

import java.io.IOException

class NoNetworkError(override val message: String = "Network not available") : IOException(message)