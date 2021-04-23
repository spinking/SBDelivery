package studio.eyesthetics.sbdelivery.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import studio.eyesthetics.sbdelivery.data.database.entities.CategoryEntity
import studio.eyesthetics.sbdelivery.data.repositories.categories.ICategoryRepository
import studio.eyesthetics.sbdelivery.viewmodels.base.BaseViewModel
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelFactory
import studio.eyesthetics.sbdelivery.viewmodels.base.IViewModelState
import javax.inject.Inject

class CategoriesViewModel(
    handle: SavedStateHandle,
    private val categoryRepository: ICategoryRepository
) : BaseViewModel<CategoriesState>(handle, CategoriesState()) {

    private val categories = categoryRepository.getCategories()

    fun observeCategories(owner: LifecycleOwner, onChange: (List<CategoryEntity>) -> Unit) {
        categories.observe(owner, Observer { onChange(it) })
    }
}

class CategoriesViewModelFactory @Inject constructor(
    private val categoryRepository: ICategoryRepository

) : IViewModelFactory<CategoriesViewModel> {
    override fun create(handle: SavedStateHandle): CategoriesViewModel {
        return CategoriesViewModel(handle, categoryRepository)
    }
}

class CategoriesState : IViewModelState