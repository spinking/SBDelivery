package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import studio.eyesthetics.sbdelivery.data.database.entities.DishItem
import studio.eyesthetics.sbdelivery.data.repositories.dishes.IDishesRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import java.util.concurrent.Executors
import javax.inject.Inject

class CategoryViewModel(
    handle: SavedStateHandle,
    private val dishesRepository: IDishesRepository
) : BaseViewModel<CategoryState>(handle, CategoryState()) {

    private var isLoadingInitial = false
    private var isLoadingAfter = false

    private val dishes = Transformations.switchMap(
        state.map { it.categoryId }.distinctUntilChanged()
    ) {
        buildPagedList(dishesRepository.getDishes(it))
    }

    private val listConfig by lazy {
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .setPrefetchDistance(10)
            .setInitialLoadSizeHint(20)
            .build()
    }

    private fun buildPagedList(dataFactory: DataSource.Factory<Int, DishItem>): LiveData<PagedList<DishItem>> {
        val builder = LivePagedListBuilder<Int, DishItem>(dataFactory, listConfig)
        builder.setBoundaryCallback(DishBoundaryCallback(::zeroLoadingHandle, ::itemAtEndHandle))
        return builder.setFetchExecutor(Executors.newSingleThreadExecutor()).build()
    }

    private fun zeroLoadingHandle() {
        updateState { it.copy(listIsEmpty = true) }
        if (isLoadingInitial) return
        else isLoadingInitial = true

        launchSafety(null, { isLoadingInitial = false }) {
            dishesRepository.loadDishesFromNetworkById(
                0,
                listConfig.pageSize,
                currentState.categoryId
            )
        }
    }

    private fun itemAtEndHandle(lastLoadDish: DishItem) {
        if (isLoadingAfter) return
        else isLoadingAfter = true

        launchSafety(null, { isLoadingAfter = false }) {
            dishesRepository.loadDishesFromNetworkById(
                offset = 0,
                limit = listConfig.pageSize,
                categoryId = currentState.categoryId
            )
        }
    }

    fun observeDishes(owner: LifecycleOwner, onChange: (PagedList<DishItem>) -> Unit) {
        dishes.observe(owner, Observer { onChange(it) })
    }

    fun handleCategoryId(categoryId: String) {
        updateState { it.copy(categoryId = categoryId) }
    }
}

class CategoryViewModelFactory @Inject constructor(
    private val dishesRepository: IDishesRepository
) : IViewModelFactory<CategoryViewModel> {
    override fun create(handle: SavedStateHandle): CategoryViewModel {
        return CategoryViewModel(handle, dishesRepository)
    }
}

data class CategoryState(
    val categoryId: String = "",
    val listIsEmpty: Boolean = false
) : IViewModelState

class DishBoundaryCallback(
    private val zeroLoadingHandle: () -> Unit,
    private val itemAtEndHandle: (itemAtEnd: DishItem) -> Unit
) : PagedList.BoundaryCallback<DishItem>() {
    override fun onZeroItemsLoaded() {
        zeroLoadingHandle
    }

    override fun onItemAtEndLoaded(itemAtEnd: DishItem) {
        itemAtEndHandle(itemAtEnd)
    }
}