package studio.eyesthetics.sbdelivery.data.models.basket

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BasketItemShort(
    val id: String,
    val amount: Int
) : Parcelable