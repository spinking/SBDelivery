package studio.eyesthetics.sbdelivery.data.models.auth

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RecoveryCodeRequest(
    val email: String,
    val code: String
) : Parcelable