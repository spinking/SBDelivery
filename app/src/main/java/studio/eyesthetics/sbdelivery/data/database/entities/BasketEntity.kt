package studio.eyesthetics.sbdelivery.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "basket"
)
data class BasketEntity(
    @PrimaryKey
    val id: Long,
    var promocode: String,
    val promotext: String,
    val total: Int
)