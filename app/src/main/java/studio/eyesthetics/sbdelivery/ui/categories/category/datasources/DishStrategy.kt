package studio.eyesthetics.sbdelivery.ui.categories.category.datasources

import studio.eyesthetics.sbdelivery.data.database.entities.DishItem

sealed class DishStrategy {
    abstract fun getItems(size: Int, start: Int): List<DishItem>
    class DishesByCategoryId(
        private val itemProvider: (Int, Int, String) -> List<DishItem>,
        private val categoryId: String
    ) : DishStrategy() {
        override fun getItems(size: Int, start: Int): List<DishItem> {
            return itemProvider(size, start, categoryId)
        }
    }
}