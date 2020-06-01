package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class MainViewModel(
    private val handle: SavedStateHandle
) : BaseViewModel<RootState>(handle, RootState())



class MainViewModelFactory @Inject constructor(

) : IViewModelFactory<MainViewModel> {
    override fun create(handle: SavedStateHandle): MainViewModel {
        return MainViewModel(handle)
    }
}

data class RootState(
    val isAuth: Boolean = false
) : IViewModelState