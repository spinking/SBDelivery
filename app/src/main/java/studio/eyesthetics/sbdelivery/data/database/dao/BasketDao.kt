package studio.eyesthetics.sbdelivery.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import studio.eyesthetics.sbdelivery.data.database.entities.Basket
import studio.eyesthetics.sbdelivery.data.database.entities.BasketEntity

@Dao
interface BasketDao : BaseDao<BasketEntity> {
    @Transaction
    suspend fun upsert(list: List<BasketEntity>) {
        insert(list)
            .mapIndexed{ index, recordId -> if (recordId == -1L) list[index] else null }
            .filterNotNull()
            .also { if (it.isNotEmpty()) update(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(basket: BasketEntity)

    @Query("SELECT * FROM basket WHERE id = 1")
    fun getLiveBasket(): LiveData<Basket>

    @Query("SELECT * FROM basket WHERE id = 1")
    fun getBasket(): BasketEntity

    @Query("UPDATE basket SET total = :total")
    fun updateBasketTotal(total: Int)
}