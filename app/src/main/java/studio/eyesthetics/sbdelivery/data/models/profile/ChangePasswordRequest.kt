package studio.eyesthetics.sbdelivery.data.models.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChangePasswordRequest(
    val oldPassword: String,
    val newPassword: String
) : Parcelable