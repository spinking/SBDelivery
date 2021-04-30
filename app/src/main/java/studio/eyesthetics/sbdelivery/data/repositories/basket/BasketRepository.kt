package studio.eyesthetics.sbdelivery.data.repositories.basket

import studio.eyesthetics.sbdelivery.data.database.dao.BasketDao
import studio.eyesthetics.sbdelivery.data.database.dao.BasketItemDao
import studio.eyesthetics.sbdelivery.data.mappers.BasketItemToBasketItemEntity
import studio.eyesthetics.sbdelivery.data.mappers.BasketResponseToBasketEntity
import studio.eyesthetics.sbdelivery.data.models.basket.BasketRequest
import studio.eyesthetics.sbdelivery.data.network.IBasketApi

class BasketRepository(
    private val basketApi: IBasketApi,
    private val basketDao: BasketDao,
    private val basketItemDao: BasketItemDao,
    private val basketMapper: BasketResponseToBasketEntity,
    private val basketItemMapper: BasketItemToBasketItemEntity
) : IBasketRepository {
    override suspend fun loadBasketFromNetwork() {
        val response = basketApi.getBasket()
        basketDao.insert(basketMapper.mapFromEntity(response))
        if (response.items.isNotEmpty())
            basketItemDao.upsert(basketItemMapper.mapFromListEntity(response.items))
    }

    override suspend fun updateBasket(basketRequest: BasketRequest) {
        val response = basketApi.updateBasket(basketRequest)
        basketDao.insert(basketMapper.mapFromEntity(response))
        if (response.items.isNotEmpty())
            basketItemDao.upsert(basketItemMapper.mapFromListEntity(response.items))
    }
}