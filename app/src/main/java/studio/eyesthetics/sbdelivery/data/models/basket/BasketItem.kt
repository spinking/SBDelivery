package studio.eyesthetics.sbdelivery.data.models.basket

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BasketItem(
    val id: String,
    val amount: Int,
    val price: Int
) : Parcelable