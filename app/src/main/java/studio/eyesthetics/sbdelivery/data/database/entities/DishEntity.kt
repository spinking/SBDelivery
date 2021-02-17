package studio.eyesthetics.sbdelivery.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dish_table")
data class DishEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val image: String,
    @ColumnInfo(name = "old_price") val oldPrice: String,
    val price: Int,
    val rating: Float,
    val likes: Int,
    val category: String,
    @ColumnInfo(name = "comments_count") val commentsCount: Int,
    val active: Boolean,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long
)

@DatabaseView("""
    SELECT id, name, description, image, old_price, price, rating, likes, category,
    comments_count, active, created_at, updated_at, personal.is_favorite AS is_favorite
    FROM dish_table
    LEFT JOIN dish_personal_info AS personal ON personal.dish_id = id
""")
data class DishItem(
    val id: String,
    val name: String,
    val description: String,
    val image: String,
    @ColumnInfo(name = "old_price") val oldPrice: String,
    val price: Int,
    val rating: Float,
    val likes: Int,
    val category: String,
    @ColumnInfo(name = "comments_count") val commentsCount: Int,
    val active: Boolean,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean = false
)
