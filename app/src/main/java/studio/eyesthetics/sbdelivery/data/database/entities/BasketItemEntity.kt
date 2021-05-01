package studio.eyesthetics.sbdelivery.data.database.entities

import androidx.room.*
import studio.eyesthetics.sbdelivery.data.models.BasketDelegateItem

@Entity(
    tableName = "basket_item",
    foreignKeys = [ForeignKey(
        entity = BasketEntity::class,
        parentColumns = ["id"],
        childColumns = ["basket_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class BasketItemEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "basket_id") val basketId: Long,
    var amount: Int,
    val price: Int
) : BasketDelegateItem

class Basket(
    @Embedded
    val basketInfo: BasketEntity,
    @Relation(parentColumn = "id", entity = BasketItemEntity::class, entityColumn = "basket_id")
    val items: List<BasketItemEntity>
)