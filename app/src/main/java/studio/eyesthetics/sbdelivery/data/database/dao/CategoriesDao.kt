package studio.eyesthetics.sbdelivery.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
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

    @Query("SELECT * FROM categories")
    fun getCategories(): LiveData<List<CategoryEntity>>

    @Query("""
        SELECT * FROM categories
        WHERE LOWER(name) LIKE LOWER('%' || :query || '%')
        ORDER BY name ASC
    """)
    fun getCategoriesByName(query: String): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE parent == :categoryId")
    fun getCategoriesByParentId(categoryId: String): List<CategoryEntity>
}