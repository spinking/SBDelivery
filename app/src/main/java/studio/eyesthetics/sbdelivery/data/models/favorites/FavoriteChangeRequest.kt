package studio.eyesthetics.sbdelivery.data.models.favorites

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteChangeRequest(
    val dishId: Int,
    val favorite: Boolean
) : Parcelable