package studio.eyesthetics.sbdelivery.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
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
}