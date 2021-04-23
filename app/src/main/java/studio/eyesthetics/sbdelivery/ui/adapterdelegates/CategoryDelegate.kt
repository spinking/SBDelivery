package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.view.View
import coil.load
import coil.request.CachePolicy
import kotlinx.android.synthetic.main.item_category.*
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.database.entities.CategoryEntity
import studio.eyesthetics.sbdelivery.extensions.dpToPx
import studio.eyesthetics.sbdelivery.ui.base.BaseAdapterDelegate

class CategoryDelegate(
    private val displayWidth: Int = 0,
    private val clickListener: (String) -> Unit
) : BaseAdapterDelegate<CategoryEntity>() {
    override val layoutRes: Int = R.layout.item_category

    override fun createHolder(view: View): ViewHolder {
        val params = view.layoutParams
        val width = ((displayWidth - 64.dpToPx()) / 3)
        params.width = width
        params.height = width
        return CategoriesViewHolder(view)
    }

    override fun onBindViewHolder(item: CategoryEntity, holder: ViewHolder, payloads: MutableList<Any>) {
        val viewHolder = holder as CategoriesViewHolder
        viewHolder.bind(item)
    }

    inner class CategoriesViewHolder(convertView: View) : ViewHolder(convertView) {
        fun bind(item: CategoryEntity) {
            iv_category_icon.load(item.icon) {
                diskCachePolicy(CachePolicy.ENABLED)
            }
            tv_category_title.text = item.name

            itemView.setOnClickListener {
                clickListener.invoke(item.id)
            }
        }
    }
}