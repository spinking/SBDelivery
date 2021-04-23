package studio.eyesthetics.sbdelivery.ui.categories.category.datasources

import androidx.paging.PositionalDataSource
import studio.eyesthetics.sbdelivery.data.database.entities.DishItem

class DishDataSource(
    private val strategy: DishStrategy
) : PositionalDataSource<DishItem>() {
    private var pageNumber: Int = INITIAL_PAGE_NUMBER

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<DishItem>) {
        val res = strategy.getItems(params.requestedLoadSize, pageNumber)
        pageNumber += 2
        callback.onResult(res, params.requestedStartPosition)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<DishItem>) {
        val res = strategy.getItems(params.loadSize, pageNumber)
        pageNumber++
        callback.onResult(res)
    }

    companion object {
        private const val INITIAL_PAGE_NUMBER = 1
    }
}