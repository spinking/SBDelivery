package studio.eyesthetics.sbdelivery.data.models.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderItem(
    val name: String,
    val image: String,
    val amount: Int,
    val price: Int,
    val dishId: String
) : Parcelable