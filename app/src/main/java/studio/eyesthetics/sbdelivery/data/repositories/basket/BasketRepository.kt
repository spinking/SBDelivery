package studio.eyesthetics.sbdelivery.data.repositories.basket

import androidx.lifecycle.LiveData
import studio.eyesthetics.sbdelivery.data.database.dao.BasketDao
import studio.eyesthetics.sbdelivery.data.database.dao.BasketItemDao
import studio.eyesthetics.sbdelivery.data.database.entities.Basket
import studio.eyesthetics.sbdelivery.data.database.entities.BasketItemEntity
import studio.eyesthetics.sbdelivery.data.mappers.BasketEntityToBasketShortMapper
import studio.eyesthetics.sbdelivery.data.mappers.BasketItemToBasketItemEntity
import studio.eyesthetics.sbdelivery.data.mappers.BasketResponseToBasketEntity
import studio.eyesthetics.sbdelivery.data.models.basket.BasketRequest
import studio.eyesthetics.sbdelivery.data.network.IBasketApi

class BasketRepository(
    private val basketApi: IBasketApi,
    private val basketDao: BasketDao,
    private val basketItemDao: BasketItemDao,
    private val basketMapper: BasketResponseToBasketEntity,
    private val basketItemMapper: BasketItemToBasketItemEntity,
    private val basketShortMapper: BasketEntityToBasketShortMapper
) : IBasketRepository {

    override fun getCachedBasket(): LiveData<Basket> {
        return basketDao.getBasket()
    }

    override fun getLiveTotal(): LiveData<Int> {
        return basketDao.getLiveTotal()
    }

    override suspend fun updateLocalBasket(basketItem: BasketItemEntity) {
        basketItemDao.upsert(basketItem)
        basketDao.updateBasketTotal(basketItemDao.getBasketItems().map { it.price * it.amount }.fold(0) { acc, i -> acc + i })
    }

    override suspend fun loadBasketFromNetwork() {
        val response = basketApi.getBasket()
        basketDao.insert(basketMapper.mapFromEntity(response))
        if (response.items.isNotEmpty())
            basketItemDao.upsert(basketItemMapper.mapFromListEntity(response.items))
    }

    override suspend fun updateLocalBasketPromo(promo: String) {
        basketDao.updateBasketPromo(promo)
    }

    override suspend fun updateBasket() {
        val basketRequest = BasketRequest(
             basketDao.getCachePromoCode(),
            basketShortMapper.mapFromListEntity(basketItemDao.getBasketItems())
        )
        val response = basketApi.updateBasket(basketRequest)
        basketDao.insert(basketMapper.mapFromEntity(response))
        if (response.items.isNotEmpty())
            basketItemDao.upsert(basketItemMapper.mapFromListEntity(response.items))
    }

    override suspend fun deleteBasketItem(itemId: String) {
        basketItemDao.deleteBasketItemById(itemId)
    }
}