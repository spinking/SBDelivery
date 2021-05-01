package studio.eyesthetics.sbdelivery.data.repositories.basket

import androidx.lifecycle.LiveData
import studio.eyesthetics.sbdelivery.data.database.entities.Basket
import studio.eyesthetics.sbdelivery.data.database.entities.BasketItemEntity
import studio.eyesthetics.sbdelivery.data.models.basket.BasketItemShort

interface IBasketRepository {

    fun getCachedBasket(): Basket
    fun getLiveTotal(): LiveData<Int>
    suspend fun loadBasketFromNetwork()
    suspend fun updateBasket(basketItem: BasketItemShort)
    suspend fun updateLocalBasket(basketItem: BasketItemEntity)
    suspend fun deleteBasketItem(itemId: String)
}