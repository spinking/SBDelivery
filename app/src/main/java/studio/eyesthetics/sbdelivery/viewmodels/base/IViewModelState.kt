package studio.eyesthetics.sbdelivery.viewmodels.base

import androidx.lifecycle.SavedStateHandle

interface IViewModelState {
    fun save(outState: SavedStateHandle) {
        //default empty implementation
    }

    fun restore(savedState: SavedStateHandle) : IViewModelState {
        return this
    }
}