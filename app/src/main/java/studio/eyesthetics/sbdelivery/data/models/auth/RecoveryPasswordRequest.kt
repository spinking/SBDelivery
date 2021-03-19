package studio.eyesthetics.sbdelivery.data.models.auth

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecoveryPasswordRequest(
    val email: String,
    val code: String,
    val password: String
) : Parcelable