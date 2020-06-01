package studio.eyesthetics.sbdelivery.data.models.basket

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BasketRequest(
    val promocode: String,
    val items: List<BasketItemShort>
) : Parcelable