package studio.eyesthetics.sbdelivery.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suggestions")
class SuggestionEntity(
    @PrimaryKey
    val suggestion: String
)