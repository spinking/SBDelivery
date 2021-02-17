package studio.eyesthetics.sbdelivery.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dish_personal_info")
data class DishPersonalInfoEntity(
    @PrimaryKey
    @ColumnInfo(name = "dish_id") val dishId: String,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean = false
)