package studio.eyesthetics.sbdelivery.data.repositories.basket

import androidx.lifecycle.LiveData
import studio.eyesthetics.sbdelivery.data.database.entities.Basket
import studio.eyesthetics.sbdelivery.data.database.entities.BasketItemEntity

interface IBasketRepository {

    fun getCachedBasket(): LiveData<Basket>
    fun getLiveTotal(): LiveData<Int>
    suspend fun loadBasketFromNetwork()
    suspend fun updateBasket()
    suspend fun updateLocalBasket(basketItem: BasketItemEntity)
    suspend fun updateLocalBasketPromo(promo: String)
    suspend fun deleteBasketItem(itemId: String)
}