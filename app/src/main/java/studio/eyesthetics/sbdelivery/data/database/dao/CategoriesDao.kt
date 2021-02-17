package studio.eyesthetics.sbdelivery.data.database.dao

import androidx.room.Dao
import androidx.room.Transaction
import studio.eyesthetics.sbdelivery.data.database.entities.CategoryEntity

@Dao
interface CategoriesDao : BaseDao<CategoryEntity> {
    @Transaction
    suspend fun upsert(list: List<CategoryEntity>) {
        insert(list)
            .mapIndexed{ index, recordId -> if (recordId == -1L) list[index] else null }
            .filterNotNull()
            .also { if (it.isNotEmpty()) update(it) }
    }
}