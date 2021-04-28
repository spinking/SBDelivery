package studio.eyesthetics.sbdelivery.data.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import studio.eyesthetics.sbdelivery.data.database.entities.DishEntity
import studio.eyesthetics.sbdelivery.data.database.entities.DishItem

@Dao
interface DishesDao : BaseDao<DishEntity>{
    @Transaction
    suspend fun upsert(list: List<DishEntity>) {
        insert(list)
            .mapIndexed{ index, recordId -> if (recordId == -1L) list[index] else null }
            .filterNotNull()
            .also { if (it.isNotEmpty()) update(it) }
    }

    @Query("""
        SELECT * FROM dish_table
        LEFT JOIN dish_personal_info AS personal ON personal.dish_id = id
        WHERE id IN (:ids)
    """)
    fun findRecommendDishes(ids: List<String>): LiveData<List<DishItem>>

    @Query("""
        SELECT * FROM dish_table
        LEFT JOIN dish_personal_info AS personal ON personal.dish_id = id
        WHERE rating >= 4.8 LIMIT 10
    """)
    fun findBestDishes(): LiveData<List<DishItem>>

    @Query("""
        SELECT * FROM dish_table
        LEFT JOIN dish_personal_info AS personal ON personal.dish_id = id
        ORDER BY likes DESC LIMIT 10""")
    fun findPopularDishes(): LiveData<List<DishItem>>


    @Query("""
        SELECT * FROM dish_table
        LEFT JOIN dish_personal_info AS personal ON personal.dish_id = id
        WHERE category == :categoryId
        ORDER BY name ASC
    """)
    fun findDishesByCategoryIdNameAsc(categoryId: String): DataSource.Factory<Int, DishItem>

    @Query("""
        SELECT * FROM dish_table
        LEFT JOIN dish_personal_info AS personal ON personal.dish_id = id
        WHERE category == :categoryId
        ORDER BY name DESC
    """)
    fun findDishesByCategoryIdNameDesc(categoryId: String): DataSource.Factory<Int, DishItem>

    @Query("""
        SELECT * FROM dish_table
        LEFT JOIN dish_personal_info AS personal ON personal.dish_id = id
        WHERE category == :categoryId
        ORDER BY likes ASC
    """)
    fun findDishesByCategoryIdLikesAsc(categoryId: String): DataSource.Factory<Int, DishItem>

    @Query("""
        SELECT * FROM dish_table
        LEFT JOIN dish_personal_info AS personal ON personal.dish_id = id
        WHERE category == :categoryId
        ORDER BY likes DESC
    """)
    fun findDishesByCategoryIdLikesDesc(categoryId: String): DataSource.Factory<Int, DishItem>

    @Query("""
        SELECT * FROM dish_table
        LEFT JOIN dish_personal_info AS personal ON personal.dish_id = id
        WHERE category == :categoryId
        ORDER BY rating ASC
    """)
    fun findDishesByCategoryIdRatingAsc(categoryId: String): DataSource.Factory<Int, DishItem>

    @Query("""
        SELECT * FROM dish_table
        LEFT JOIN dish_personal_info AS personal ON personal.dish_id = id
        WHERE category == :categoryId
        ORDER BY rating DESC
    """)
    fun findDishesByCategoryIdRatingDesc(categoryId: String): DataSource.Factory<Int, DishItem>

    @Query("""
        SELECT * FROM dish_table
        LEFT JOIN dish_personal_info AS personal ON personal.dish_id = id
        WHERE LOWER(name) LIKE LOWER('%' || :query || '%')
        ORDER BY name ASC
    """)
    fun findDishesByName(query: String): List<DishItem>
}