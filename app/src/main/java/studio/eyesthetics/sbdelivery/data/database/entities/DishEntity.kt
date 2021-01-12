package studio.eyesthetics.sbdelivery.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dish_table")
data class DishEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val image: String,
    @ColumnInfo(name = "old_price") val oldPrice: Int,
    val price: Int,
    val rating: Float,
    val likes: Int,
    val category: String,
    val active: Boolean,
    @ColumnInfo(name = "create_at") val createdAt: Long,
    @ColumnInfo(name = "update_at") val updatedAt: Long
)