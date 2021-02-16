package studio.eyesthetics.sbdelivery.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import studio.eyesthetics.sbdelivery.data.database.entities.DishPersonalInfoEntity

@Dao
interface DishPersonalInfoDao : BaseDao<DishPersonalInfoEntity> {

    @Query("UPDATE dish_personal_info SET is_favorite = NOT is_favorite WHERE dish_id = :id")
    suspend fun toggleFavorite(id: String): Int

    @Transaction
    suspend fun toggleFavoriteOrInsert(id: String): Boolean {
        if (toggleFavorite(id) == 0) insert(DishPersonalInfoEntity(id, true))
        return isFavorite(id)
    }

    @Query("SELECT is_favorite FROM dish_personal_info WHERE dish_id = :id")
    suspend fun isFavorite(id: String): Boolean
}