package studio.eyesthetics.sbdelivery.data.database.dao

import androidx.room.Dao
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
}