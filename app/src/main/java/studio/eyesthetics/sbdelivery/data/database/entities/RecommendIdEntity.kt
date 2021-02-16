package studio.eyesthetics.sbdelivery.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recommend_ids")
data class RecommendIdEntity(
    @PrimaryKey val id: String
)