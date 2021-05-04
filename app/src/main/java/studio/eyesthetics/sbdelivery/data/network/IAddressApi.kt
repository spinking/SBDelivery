package studio.eyesthetics.sbdelivery.data.network

import retrofit2.http.Body
import retrofit2.http.POST
import studio.eyesthetics.sbdelivery.data.models.address.AddressResponse
import studio.eyesthetics.sbdelivery.data.models.address.CheckAddressRequest
import studio.eyesthetics.sbdelivery.data.models.address.CoordinateRequest

interface IAddressApi {
    @POST("address/input")
    suspend fun checkAddress(
        @Body addressRequest: CheckAddressRequest
    ): List<AddressResponse>

    @POST("address/coordinates")
    suspend fun checkAddress(
        @Body addressRequest: CoordinateRequest
    ): AddressResponse
}