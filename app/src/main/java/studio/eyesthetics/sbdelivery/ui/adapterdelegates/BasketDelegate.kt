package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.view.View
import kotlinx.android.synthetic.main.item_basket.*
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.database.entities.BasketItemEntity
import studio.eyesthetics.sbdelivery.data.models.BasketDelegateItem
import studio.eyesthetics.sbdelivery.extensions.formatToRub
import studio.eyesthetics.sbdelivery.ui.base.BaseAdapterDelegate

class BasketDelegate(
    private val onItemCountChanged: (String, Int, Int) -> Unit,
    private val onDeleteItem: (String) -> Unit
) : BaseAdapterDelegate<BasketDelegateItem>() {
    override val layoutRes: Int = R.layout.item_basket

    override fun createHolder(view: View): ViewHolder = BasketViewHolder(view)

    override fun onBindViewHolder(item: BasketDelegateItem, holder: ViewHolder, payloads: MutableList<Any>) {
        val viewHolder = holder as BasketViewHolder
        viewHolder.bind(item as BasketItemEntity)
    }

    override fun isForViewType(item: BasketDelegateItem, items: MutableList<BasketDelegateItem>, position: Int): Boolean = item is BasketItemEntity

    inner class BasketViewHolder(convertView: View) : ViewHolder(convertView) {
        fun bind(item: BasketItemEntity) {
            //TODO add image
            iv_picture
            tv_title
            btn_decrement.setOnClickListener {
                val currentCount = item.amount - 1
                if (currentCount > 0) {
                    item.amount = currentCount
                    tv_count.text = currentCount.toString()
                    tv_price.text = (currentCount * item.price).formatToRub()
                    onItemCountChanged.invoke(item.id, currentCount, item.price)
                } else {
                    onDeleteItem.invoke(item.id)
                }
            }
            tv_count.text = item.amount.toString()
            btn_increment.setOnClickListener {
                item.amount++
                tv_count.text = item.amount.toString()
                onItemCountChanged.invoke(item.id, item.amount, item.price)
            }
            val priceText = (item.amount * item.price).formatToRub()
            tv_price.text = priceText
        }
    }
}