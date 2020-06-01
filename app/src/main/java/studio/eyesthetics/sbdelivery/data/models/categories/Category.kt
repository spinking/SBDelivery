package studio.eyesthetics.sbdelivery.data.models.categories

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val order: Int,
    val icon: String,
    val parent: Int,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
) : Parcelable