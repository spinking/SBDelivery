package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class HomeViewModel(
    handle: SavedStateHandle
) : BaseViewModel<HomeState>(handle, HomeState()) {

}

class HomeViewModelFactory @Inject constructor(

) : IViewModelFactory<HomeViewModel> {
    override fun create(handle: SavedStateHandle): HomeViewModel {
        return HomeViewModel(handle)
    }
}

class HomeState : IViewModelState