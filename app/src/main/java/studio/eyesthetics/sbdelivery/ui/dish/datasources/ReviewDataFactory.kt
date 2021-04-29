package studio.eyesthetics.sbdelivery.ui.dish.datasources

import androidx.paging.DataSource
import studio.eyesthetics.sbdelivery.data.database.entities.ReviewEntity

class ReviewDataFactory(
    private val strategy: ReviewStrategy
) : DataSource.Factory<Int, ReviewEntity>() {
    override fun create(): DataSource<Int, ReviewEntity> {
        return ReviewDataSource(strategy)
    }
}