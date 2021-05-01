package studio.eyesthetics.sbdelivery.ui.base

import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import studio.eyesthetics.sbdelivery.data.database.entities.BasketItemEntity

class DelegationAdapter<T>(diffCallback: DiffCallback<T>) : AsyncListDifferDelegationAdapter<T>(diffCallback) {

    val delegatesManager: AdapterDelegatesManager<List<T>>
        get() = super.delegatesManager

    init {
        items = listOf()
    }

    //костыль, так как diff util похоже меняет местами айтемы при их обновлении
    override fun setItems(items: MutableList<T>?) {
        if (items != null && items.isNotEmpty() && items[0] is BasketItemEntity) {
            items.sortBy { (it as BasketItemEntity).id }
        }
        super.setItems(items)
    }
}