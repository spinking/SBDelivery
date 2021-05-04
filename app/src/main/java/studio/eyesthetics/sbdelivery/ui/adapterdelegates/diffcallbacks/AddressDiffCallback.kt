package studio.eyesthetics.sbdelivery.ui.adapterdelegates.diffcallbacks

import studio.eyesthetics.sbdelivery.data.models.address.AddressResponse
import studio.eyesthetics.sbdelivery.ui.base.DiffCallback

class AddressDiffCallback : DiffCallback<AddressResponse>() {
    override fun sameId(oldItem: AddressResponse, newItem: AddressResponse): Boolean = oldItem == newItem

    override fun sameItem(oldItem: AddressResponse, newItem: AddressResponse): Boolean = oldItem == newItem
}