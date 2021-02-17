package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_dish.*
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.database.entities.DishItem
import studio.eyesthetics.sbdelivery.extensions.dpToPx
import studio.eyesthetics.sbdelivery.extensions.formatToRub
import studio.eyesthetics.sbdelivery.extensions.setMarginOptionally
import studio.eyesthetics.sbdelivery.ui.base.BaseAdapterDelegate

class RecommendDelegate(
    private val displayWidth: Int = 0,
    private val dishClickListener: (DishItem) -> Unit,
    private val addClickListener: (String) -> Unit,
    private val addToFavoriteClickListener: (String, Boolean) -> Unit
) : BaseAdapterDelegate<DishItem>() {
    override val layoutRes: Int = R.layout.item_dish

    override fun createHolder(view: View): ViewHolder {
        val params = view.layoutParams
        val width = ((displayWidth - 20.dpToPx()) * 0.45).toInt()
        params.width = width
        view.layoutParams = params
        return RecommendViewHolder(view)
    }

    override fun onBindViewHolder(item: DishItem, holder: ViewHolder, payloads: MutableList<Any>) {
        val viewHolder = holder as RecommendViewHolder
        viewHolder.bind(item)
    }

    inner class RecommendViewHolder(convertView: View) : ViewHolder(convertView) {
        fun bind(item: DishItem) {

            //TODO add placeholder
            Glide.with(itemView.context)
                .load(item.image)
                .into(iv_dish)

            tv_price.text = item.price.formatToRub()
            tv_title.text = item.name
            cb_favorite.isChecked = item.isFavorite
            tv_stock.isVisible = item.oldPrice.isNotEmpty()

            cb_favorite.setOnClickListener {
                addToFavoriteClickListener.invoke(item.id, cb_favorite.isChecked)
            }

            btn_add.setOnClickListener {
                addClickListener.invoke(item.id)
            }

            itemView.setOnClickListener {
                dishClickListener.invoke(item)
            }
        }
    }
}