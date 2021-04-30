package studio.eyesthetics.sbdelivery.viewmodels

import androidx.core.os.bundleOf
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.database.entities.ReviewEntity
import studio.eyesthetics.sbdelivery.data.models.favorites.FavoriteChangeRequest
import studio.eyesthetics.sbdelivery.data.repositories.auth.IAuthRepository
import studio.eyesthetics.sbdelivery.data.repositories.favorite.IFavoriteRepository
import studio.eyesthetics.sbdelivery.data.repositories.reviews.IReviewRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.*
import java.util.concurrent.Executors
import javax.inject.Inject

class DishViewModel(
    handle: SavedStateHandle,
    private val favoriteRepository: IFavoriteRepository,
    private val reviewRepository: IReviewRepository,
    private val authRepository: IAuthRepository
) : BaseViewModel<DishState>(handle, DishState()) {

    init {
        subscribeOnDataSource(authRepository.isAuth()) { isAuth, state ->
            state.copy(isAuth = isAuth)
        }
    }

    private var isLoadingInitial = false
    private var isLoadingAfter = false

    private val reviews = Transformations.switchMap(state.map { it.dishId }.distinctUntilChanged()) {
        buildPagedList(reviewRepository.getReviews(currentState.dishId))
    }

    fun observeReviews(owner: LifecycleOwner, onChange: (PagedList<ReviewEntity>) -> Unit) {
        reviews.observe(owner, Observer { onChange(it) })
    }

    private val listConfig by lazy {
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .setPrefetchDistance(10)
            .setInitialLoadSizeHint(20)
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

    private fun itemAtEndHandle(lastReview: ReviewEntity) {
        if (isLoadingAfter) return
        else isLoadingAfter = true

        launchSafety(null, { isLoadingAfter = false }) {
            reviewRepository.loadReviewsFromNetwork(
                dishId = currentState.dishId,
                offset = reviews.value?.size ?: 0,
                limit = listConfig.pageSize
            )
        }
    }

    fun handleDishId(dishId: String) {
        updateState { it.copy(dishId = dishId) }
    }

    fun handleAddReview(message: String) {
        notify(Notify.TextMessage(message))
    }

    fun handleAddToBasket(message: String) {
        launchSafety {
            //TODO add to basket
            updateState { it.copy(itemsCount = 1, isDecrementButtonActive = false) }
            notify(Notify.TextMessage(message))
        }
    }

    fun handleClickReview() {
        if (currentState.isAuth) {
            navigate(NavigationCommand.To(R.id.reviewDialogFragment, bundleOf("dishId" to currentState.dishId)))
        } else {
            navigate(NavigationCommand.StartLogin(R.id.dishFragment))
        }
    }
}

class DishViewModelFactory @Inject constructor(
    private val favoriteRepository: IFavoriteRepository,
    private val reviewRepository: IReviewRepository,
    private val authRepository: IAuthRepository
) : IViewModelFactory<DishViewModel> {
    override fun create(handle: SavedStateHandle): DishViewModel {
        return DishViewModel(handle, favoriteRepository, reviewRepository, authRepository)
    }
}

data class DishState(
    val isAuth: Boolean = false,
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
        zeroLoadingHandle.invoke()
    }

    override fun onItemAtEndLoaded(itemAtEnd: ReviewEntity) {
        itemAtEndHandle.invoke(itemAtEnd)
    }
}