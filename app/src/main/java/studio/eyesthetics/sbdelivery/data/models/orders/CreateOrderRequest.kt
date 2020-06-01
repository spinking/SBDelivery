package studio.eyesthetics.sbdelivery.data.models.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CreateOrderRequest(
    val address: String,
    val entrance: Int,
    val floor: Int,
    val apartment: String,
    val intercom: String,
    val comment: String
) : Parcelable