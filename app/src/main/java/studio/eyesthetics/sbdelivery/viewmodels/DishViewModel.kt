package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.Transformations
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import studio.eyesthetics.sbdelivery.data.database.entities.ReviewEntity
import studio.eyesthetics.sbdelivery.data.models.favorites.FavoriteChangeRequest
import studio.eyesthetics.sbdelivery.data.repositories.favorite.IFavoriteRepository
import studio.eyesthetics.sbdelivery.data.repositories.reviews.IReviewRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import java.util.concurrent.Executors
import javax.inject.Inject

class DishViewModel(
    handle: SavedStateHandle,
    private val favoriteRepository: IFavoriteRepository,
    private val reviewRepository: IReviewRepository
) : BaseViewModel<DishState>(handle, DishState()) {

    private var isLoadingInitial = false
    private var isLoadingAfter = false

    private val reviews = Transformations.switchMap(state) {
        buildPagedList(reviewRepository.getReviews(currentState.dishId))
    }

    fun observeReviews(owner: LifecycleOwner, onChange: (PagedList<ReviewEntity>) -> Unit) {
        reviews.observe(owner, Observer { onChange(it) })
    }

    private val listConfig by lazy {
        PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(5)
            .build()
    }

    fun handleFavorite(dishId: String, isChecked: Boolean) {
        launchSafety {
            favoriteRepository.changeToFavorite(FavoriteChangeRequest(dishId, isChecked))
        }
    }

    fun handleCount(count: Int) {
        val currentCount = currentState.itemsCount + count
        updateState { it.copy(itemsCount = currentCount, isDecrementButtonActive = currentCount > 1) }
    }

    private fun buildPagedList(dataFactory: DataSource.Factory<Int, ReviewEntity>): LiveData<PagedList<ReviewEntity>> {
        val builder = LivePagedListBuilder<Int, ReviewEntity>(dataFactory, listConfig)
        builder.setBoundaryCallback(ReviewBoundaryCallback(::zeroLoadingHandle, ::itemAtEndHandle))
        return builder.setFetchExecutor(Executors.newSingleThreadExecutor()).build()
    }

    private fun zeroLoadingHandle() {
        updateState { it.copy(listIsEmpty = true) }
        if (isLoadingInitial) return
        else isLoadingInitial = true

        launchSafety(null, { isLoadingInitial = false }) {
            reviewRepository.loadReviewsFromNetwork(
                dishId = currentState.dishId,
                offset = 0,
                limit = listConfig.pageSize
            )
        }
    }

    private fun itemAtEndHandle(lastLoadDish: ReviewEntity) {
        if (isLoadingAfter) return
        else isLoadingAfter = true

        launchSafety(null, { isLoadingAfter = false }) {
            reviewRepository.loadReviewsFromNetwork(
                dishId = currentState.dishId,
                offset = 0,
                limit = listConfig.pageSize
            )
        }
    }
}

class DishViewModelFactory @Inject constructor(
    private val favoriteRepository: IFavoriteRepository,
    private val reviewRepository: IReviewRepository
) : IViewModelFactory<DishViewModel> {
    override fun create(handle: SavedStateHandle): DishViewModel {
        return DishViewModel(handle, favoriteRepository, reviewRepository)
    }
}

data class DishState(
    val dishId: String = "",
    val itemsCount: Int = 1,
    val isDecrementButtonActive: Boolean = false,
    val listIsEmpty: Boolean = false
) : IViewModelState

class ReviewBoundaryCallback(
    private val zeroLoadingHandle: () -> Unit,
    private val itemAtEndHandle: (itemAtEnd: ReviewEntity) -> Unit
) : PagedList.BoundaryCallback<ReviewEntity>() {
    override fun onZeroItemsLoaded() {
        zeroLoadingHandle
    }

    override fun onItemAtEndLoaded(itemAtEnd: ReviewEntity) {
        itemAtEndHandle(itemAtEnd)
    }
}