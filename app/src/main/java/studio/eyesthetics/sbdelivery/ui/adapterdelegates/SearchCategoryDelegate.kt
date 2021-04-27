package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.view.View
import coil.load
import coil.request.CachePolicy
import kotlinx.android.synthetic.main.item_search_category.*
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.database.entities.CategoryEntity
import studio.eyesthetics.sbdelivery.data.models.SearchItem
import studio.eyesthetics.sbdelivery.extensions.dpToPx
import studio.eyesthetics.sbdelivery.ui.base.BaseAdapterDelegate

class SearchCategoryDelegate(
    private val displayWidth: Int = 0,
    private val clickListener: (String) -> Unit
) : BaseAdapterDelegate<SearchItem>() {
    override val layoutRes: Int = R.layout.item_search_category
    private var currentWidth: Int = 0

    override fun createHolder(view: View): ViewHolder {
        currentWidth = ((displayWidth - 48.dpToPx()) / 2)
        val params = view.layoutParams
        params.width = currentWidth
        view.layoutParams = params
        return SearchCategoryViewHolder(view)
    }

    override fun onBindViewHolder(item: SearchItem, holder: ViewHolder, payloads: MutableList<Any>) {
        val viewHolder = holder as SearchCategoryViewHolder
        viewHolder.bind(item as CategoryEntity)
    }

    inner class SearchCategoryViewHolder(convertView: View) : ViewHolder(convertView) {
        fun bind(item: CategoryEntity) {

            tv_category.text = item.name
            iv_icon.load(item.icon) {
                diskCachePolicy(CachePolicy.ENABLED)
            }

            itemView.setOnClickListener {
                clickListener.invoke(item.id)
            }
        }
    }
}