package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class DishViewModel(
    handle: SavedStateHandle
) : BaseViewModel<DishState>(handle, DishState()) {

}

class DishViewModelFactory @Inject constructor(

) : IViewModelFactory<DishViewModel> {
    override fun create(handle: SavedStateHandle): DishViewModel {
        return DishViewModel(handle)
    }
}

class DishState : IViewModelState