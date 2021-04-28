package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import coil.load
import coil.request.CachePolicy
import kotlinx.android.synthetic.main.item_dish.*
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.database.entities.DishItem
import studio.eyesthetics.sbdelivery.data.models.SearchItem
import studio.eyesthetics.sbdelivery.extensions.dpToPx
import studio.eyesthetics.sbdelivery.extensions.formatToRub
import studio.eyesthetics.sbdelivery.ui.base.BaseAdapterDelegate

class DishSearchDelegate(
    private val displayWidth: Int = 0,
    private val dishClickListener: (DishItem) -> Unit,
    private val addClickListener: (String) -> Unit,
    private val addToFavoriteClickListener: (String, Boolean) -> Unit
) : BaseAdapterDelegate<SearchItem>() {
    override val layoutRes: Int = R.layout.item_dish
    private var currentWidth: Int = 0

    override fun createHolder(view: View): ViewHolder {
        currentWidth = ((displayWidth - 48.dpToPx()) / 2)
        val imageView = view.findViewById<ImageView>(R.id.iv_dish)
        val imageParams = imageView.layoutParams
        imageParams.width = currentWidth
        imageParams.height = currentWidth
        imageView.layoutParams = imageParams
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(item: SearchItem, holder: ViewHolder, payloads: MutableList<Any>) {
        val viewHolder = holder as DishViewHolder
        viewHolder.bind(item as DishItem)
    }

    override fun isForViewType(item: SearchItem, items: MutableList<SearchItem>, position: Int): Boolean = item is DishItem

    inner class DishViewHolder(convertView: View) : ViewHolder(convertView) {
        fun bind(item: DishItem) {
            (iv_dish as ImageView).load(item.image) {
                placeholder(R.drawable.placeholder_dish)
                diskCachePolicy(CachePolicy.ENABLED)
            }

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