package studio.eyesthetics.sbdelivery.data.models.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileResponse(
    val id: String,
    val firstName: String?,
    val lastName: String?,
    val email: String?
) : Parcelable