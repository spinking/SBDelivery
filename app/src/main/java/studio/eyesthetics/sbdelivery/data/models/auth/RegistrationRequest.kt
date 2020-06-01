package studio.eyesthetics.sbdelivery.data.models.auth

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RegistrationRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
) : Parcelable