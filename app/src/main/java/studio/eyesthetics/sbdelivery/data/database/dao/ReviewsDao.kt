package studio.eyesthetics.sbdelivery.data.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import studio.eyesthetics.sbdelivery.data.database.entities.ReviewEntity

@Dao
interface ReviewsDao : BaseDao<ReviewEntity> {
    @Transaction
    suspend fun upsert(list: List<ReviewEntity>) {
        insert(list)
            .mapIndexed{ index, recordId -> if (recordId == -1L) list[index] else null }
            .filterNotNull()
            .also { if (it.isNotEmpty()) update(it) }
    }

    @Query("SELECT * FROM reviews WHERE dishId = :dishId")
    fun getReviews(dishId: String): DataSource.Factory<Int, ReviewEntity>
}