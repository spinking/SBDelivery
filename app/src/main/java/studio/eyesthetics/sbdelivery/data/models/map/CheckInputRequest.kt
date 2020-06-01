package studio.eyesthetics.sbdelivery.data.models.map

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CheckInputRequest(
    val address: String
) : Parcelable