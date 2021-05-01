package studio.eyesthetics.sbdelivery.data.models.basket

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BasketResponse(
    val id: Long? = null,
    val promocode: String,
    val promotext: String,
    val total: Int,
    val items: List<BasketItem>
) : Parcelable