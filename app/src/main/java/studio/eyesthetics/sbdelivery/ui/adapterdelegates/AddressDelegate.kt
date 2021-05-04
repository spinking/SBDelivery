package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.view.View
import kotlinx.android.synthetic.main.item_address.*
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.models.address.AddressResponse
import studio.eyesthetics.sbdelivery.ui.base.BaseAdapterDelegate

class AddressDelegate(
    private val onItemClick: (String) -> Unit
) : BaseAdapterDelegate<AddressResponse>() {
    override val layoutRes: Int = R.layout.item_address

    override fun createHolder(view: View): ViewHolder = AddressViewHolder(view)

    override fun onBindViewHolder(item: AddressResponse, holder: ViewHolder, payloads: MutableList<Any>) {
        val viewHolder = holder as AddressViewHolder
        viewHolder.bind(item)
    }

    inner class AddressViewHolder(convertView: View) : ViewHolder(convertView) {
        fun bind(item: AddressResponse) {
            val addressText = "${item.city}, ${item.house}, ${item.street}"
            tv_address.text = addressText

            itemView.setOnClickListener {
                onItemClick.invoke(addressText)
            }
        }
    }
}