package studio.eyesthetics.sbdelivery.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val dishId: String,
    val author: String,
    val date: String,
    val rating: Int,
    val text: String,
    val active: Boolean,
    val createdAt: Long,
    val updatedAt: Long
)