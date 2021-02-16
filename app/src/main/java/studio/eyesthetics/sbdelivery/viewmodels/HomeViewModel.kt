package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.async
import studio.eyesthetics.sbdelivery.data.database.entities.DishEntity
import studio.eyesthetics.sbdelivery.data.repositories.dishes.IDishesRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class HomeViewModel(
    handle: SavedStateHandle,
    private val dishesRepository: IDishesRepository
) : BaseViewModel<HomeState>(handle, HomeState()) {

    init {
        getDishes()
    }

    private val recommendDishes = MutableLiveData<List<DishEntity>>()
    private val bestDishes = MutableLiveData<List<DishEntity>>()
    private val popularDishes = MutableLiveData<List<DishEntity>>()

    fun observeRecommend(owner: LifecycleOwner, onChange: (List<DishEntity>) -> Unit) {
        recommendDishes.observe(owner, Observer { onChange(it) })
    }

    fun observeBest(owner: LifecycleOwner, onChange: (List<DishEntity>) -> Unit) {
        bestDishes.observe(owner, Observer { onChange(it) })
    }

    fun observePopular(owner: LifecycleOwner, onChange: (List<DishEntity>) -> Unit) {
        popularDishes.observe(owner, Observer { onChange(it) })
    }

    private fun getDishes() {
        launchSafety {
            val recommendRequest = async { dishesRepository.getRecommendDishes() }
            val bestRequest = async { dishesRepository.getBestDishes() }
            val popularRequest = async { dishesRepository.getPopularDishes() }

            val recommendResponse = recommendRequest.await()
            val bestResponse  = bestRequest.await()
            val popularResponse = popularRequest.await()

            recommendDishes.value = recommendResponse
            bestDishes.value = bestResponse
            popularDishes.value = popularResponse
        }
    }
}

class HomeViewModelFactory @Inject constructor(
    private val dishesRepository: IDishesRepository
) : IViewModelFactory<HomeViewModel> {
    override fun create(handle: SavedStateHandle): HomeViewModel {
        return HomeViewModel(handle, dishesRepository)
    }
}

class HomeState : IViewModelState