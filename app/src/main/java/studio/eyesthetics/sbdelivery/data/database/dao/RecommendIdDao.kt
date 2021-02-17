package studio.eyesthetics.sbdelivery.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import studio.eyesthetics.sbdelivery.data.database.entities.RecommendIdEntity

@Dao
interface RecommendIdDao : BaseDao<RecommendIdEntity> {
    @Query("SELECT * FROM recommend_ids")
    fun findRecommendIds(): List<RecommendIdEntity>

    @Query("DELETE FROM recommend_ids")
    suspend fun deleteAllIds()
}