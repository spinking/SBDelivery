package studio.eyesthetics.sbdelivery.data.models.auth

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RefreshTokenRequest(
    val refreshToken: String
) : Parcelable