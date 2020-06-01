package studio.eyesthetics.sbdelivery.data.models.map

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckInputResponse(
    val city: String,
    val street: String,
    val house: String
) : Parcelable