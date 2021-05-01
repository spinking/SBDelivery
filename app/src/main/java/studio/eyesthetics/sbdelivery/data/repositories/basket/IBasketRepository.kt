package studio.eyesthetics.sbdelivery.data.repositories.basket

import androidx.lifecycle.LiveData
import studio.eyesthetics.sbdelivery.data.database.entities.Basket
import studio.eyesthetics.sbdelivery.data.models.basket.BasketRequest

interface IBasketRepository {

    fun getCachedBasket(): LiveData<Basket>

    suspend fun loadBasketFromNetwork()
    suspend fun updateBasket(basketRequest: BasketRequest)
}