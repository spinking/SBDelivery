package studio.eyesthetics.sbdelivery.data.database.dao

import androidx.room.*
import retrofit2.http.DELETE
import studio.eyesthetics.sbdelivery.data.database.entities.BasketItemEntity

@Dao
interface BasketItemDao : BaseDao<BasketItemEntity> {
    @Transaction
    suspend fun upsert(list: List<BasketItemEntity>) {
        insert(list)
            .mapIndexed{ index, recordId -> if (recordId == -1L) list[index] else null }
            .filterNotNull()
            .also { if (it.isNotEmpty()) update(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(basketItem: BasketItemEntity)

    @Query("SELECT * FROM basket_item")
    fun getBasketItems(): List<BasketItemEntity>

    @Query("DELETE FROM basket_item WHERE id = :itemId")
    fun deleteBasketItemById(itemId: String)
}