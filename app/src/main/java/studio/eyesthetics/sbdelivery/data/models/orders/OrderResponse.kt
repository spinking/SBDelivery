package studio.eyesthetics.sbdelivery.data.models.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderResponse(
    val id: String,
    val total: Int,
    val address: String,
    val statusId: Int,
    val active: Boolean,
    val completed: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
    val items: List<OrderItem>
) : Parcelable