package studio.eyesthetics.sbdelivery.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import studio.eyesthetics.sbdelivery.data.database.entities.DishEntity

@Dao
interface DishesDao : BaseDao<DishEntity>{
    @Transaction
    suspend fun upsert(list: List<DishEntity>) {
        insert(list)
            .mapIndexed{ index, recordId -> if (recordId == -1L) list[index] else null }
            .filterNotNull()
            .also { if (it.isNotEmpty()) update(it) }
    }

    @Query("SELECT * FROM dish_table WHERE id IN (:ids)")
    suspend fun findRecommendDishes(ids: List<String>): List<DishEntity>

    @Query("SELECT * FROM dish_table WHERE rating >= 4.8 LIMIT 10")
    suspend fun findBestDishes(): List<DishEntity>

    @Query("SELECT * FROM dish_table ORDER BY likes DESC LIMIT 10")
    suspend fun findPopularDishes(): List<DishEntity>

}