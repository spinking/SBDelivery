package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class BasketViewModel(
    handle: SavedStateHandle
) : BaseViewModel<BasketState>(handle, BasketState()) {

}

class BasketViewModelFactory @Inject constructor(

) : IViewModelFactory<BasketViewModel> {
    override fun create(handle: SavedStateHandle): BasketViewModel {
        return BasketViewModel(handle)
    }
}

class BasketState : IViewModelState