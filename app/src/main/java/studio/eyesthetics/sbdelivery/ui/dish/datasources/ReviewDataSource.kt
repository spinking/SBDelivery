package studio.eyesthetics.sbdelivery.ui.dish.datasources

import androidx.paging.PositionalDataSource
import studio.eyesthetics.sbdelivery.data.database.entities.ReviewEntity

class ReviewDataSource(
    private val strategy: ReviewStrategy
) : PositionalDataSource<ReviewEntity>() {
    private var pageNumber: Int = INITIAL_PAGE_NUMBER

    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<ReviewEntity>
    ) {
        val res = strategy.getItems("" ,params.requestedLoadSize, pageNumber)
        pageNumber ++
        callback.onResult(res, params.requestedStartPosition)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<ReviewEntity>) {
        val res = strategy.getItems("" ,params.loadSize, pageNumber)
        pageNumber++
        callback.onResult(res)
    }

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
    }
}