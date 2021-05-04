package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.text.InputType
import android.view.View
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.item_promocode.*
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.models.BasketDelegateItem
import studio.eyesthetics.sbdelivery.data.models.basket.PromoItem
import studio.eyesthetics.sbdelivery.ui.base.BaseAdapterDelegate

class PromoDelegate(
    private val onSendPromoClick: (String) -> Unit
) : BaseAdapterDelegate<BasketDelegateItem>() {
    override val layoutRes: Int = R.layout.item_promocode

    override fun createHolder(view: View): ViewHolder = PromoViewHolder(view)

    override fun onBindViewHolder(item: BasketDelegateItem, holder: ViewHolder, payloads: MutableList<Any>) {
        val viewHolder = holder as PromoViewHolder
        viewHolder.bind(item as PromoItem)
    }

    override fun isForItem(item: BasketDelegateItem, items: MutableList<BasketDelegateItem>, position: Int): Boolean = item is PromoItem

    inner class PromoViewHolder(convertView: View) : ViewHolder(convertView) {
        fun bind(item: PromoItem) {
            et_promo.apply {
                setText(item.promocode)
                inputType = if (item.promocode.isNotEmpty()) InputType.TYPE_NULL else InputType.TYPE_CLASS_TEXT
                addTextChangedListener {
                    btn_send_promo.isEnabled = it?.trim().isNullOrEmpty().not()
                }
            }
            btn_send_promo.apply {
                setTextColor(itemView.context.getColorStateList(R.color.color_state_list_promo))
                isEnabled = item.promocode.isNotEmpty()
                text = if (item.promocode.isNotEmpty()) CANCEL else ACCEPT
                setOnClickListener {
                    if (text == ACCEPT) {
                        onSendPromoClick.invoke(et_promo.text.toString())
                        //TODO change it after getting response
                        text = CANCEL
                        et_promo.clearFocus()
                    } else {
                        onSendPromoClick.invoke("")
                        //TODO change it fater getting response
                        text = ACCEPT
                        btn_send_promo.isActivated = false
                        et_promo.apply {
                            setText("")
                            inputType = InputType.TYPE_CLASS_TEXT
                        }
                    }
                }
            }
            tv_discount.text = item.promotext
        }
    }

    companion object {
        private const val ACCEPT = "Применить"
        private const val CANCEL = "Отменить"
    }
}