package studio.eyesthetics.sbdelivery.data.models.reviews

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddReviewRequest(
    val rating: Int,
    val text: String
) : Parcelable