package studio.eyesthetics.sbdelivery.data.database.dao

import androidx.room.Dao
import androidx.room.Transaction
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
}