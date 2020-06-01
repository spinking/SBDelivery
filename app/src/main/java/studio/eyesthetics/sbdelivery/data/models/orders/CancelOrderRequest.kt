package studio.eyesthetics.sbdelivery.data.models.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CancelOrderRequest(
    val orderId: Int
) : Parcelable