package studio.eyesthetics.sbdelivery.data.models.favorites

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteChangeRequest(
    val dishId: String,
    val favorite: Boolean
) : Parcelable