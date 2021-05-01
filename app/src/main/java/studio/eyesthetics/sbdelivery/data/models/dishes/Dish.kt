package studio.eyesthetics.sbdelivery.data.models.dishes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dish(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    val oldPrice: Int?,
    val price: Int,
    val rating: Float,
    val likes: Int,
    val category: String,
    val commentsCount: Int,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable