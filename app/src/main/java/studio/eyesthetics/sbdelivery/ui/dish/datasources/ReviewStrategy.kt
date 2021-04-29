package studio.eyesthetics.sbdelivery.ui.dish.datasources

import studio.eyesthetics.sbdelivery.data.database.entities.ReviewEntity

sealed class ReviewStrategy {
    abstract fun getItems(dishId: String, offset: Int, limit: Int): List<ReviewEntity>
    class ReviewByDishId(
        private val itemProvider: (String, Int, Int) -> List<ReviewEntity>
    ) : ReviewStrategy() {
        override fun getItems(dishId: String, offset: Int, limit: Int): List<ReviewEntity> {
            return itemProvider(dishId, offset, limit)
        }
    }
}