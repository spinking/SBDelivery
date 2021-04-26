package studio.eyesthetics.sbdelivery.ui.categories.category.datasources

import androidx.paging.DataSource
import studio.eyesthetics.sbdelivery.data.database.entities.DishItem


class DishDataFactory(
    private val strategy: DishStrategy
) : DataSource.Factory<Int, DishItem>() {
    override fun create(): DataSource<Int, DishItem> {
        return DishDataSource(strategy)
    }
}