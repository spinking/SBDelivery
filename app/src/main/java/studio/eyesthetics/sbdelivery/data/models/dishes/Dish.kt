package studio.eyesthetics.sbdelivery.data.models.dishes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dish(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val ondPrice: Int,
    val price: Int,
    val rating: Float,
    val likes: Int,
    val category: String,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable