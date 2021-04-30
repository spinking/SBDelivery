package studio.eyesthetics.sbdelivery.data.repositories.basket

import studio.eyesthetics.sbdelivery.data.models.basket.BasketRequest

interface IBasketRepository {
    suspend fun loadBasketFromNetwork()
    suspend fun updateBasket(basketRequest: BasketRequest)
}