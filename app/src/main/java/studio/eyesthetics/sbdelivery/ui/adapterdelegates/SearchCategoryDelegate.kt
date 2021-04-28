package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.view.View
import androidx.core.view.isVisible
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
    private val clickListener: (CategoryEntity) -> Unit
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

    override fun isForViewType(item: SearchItem, items: MutableList<SearchItem>, position: Int): Boolean = item is CategoryEntity

    inner class SearchCategoryViewHolder(convertView: View) : ViewHolder(convertView) {
        fun bind(item: CategoryEntity) {

            tv_category.text = item.name
            iv_icon.apply {
                load(item.icon) {
                    diskCachePolicy(CachePolicy.ENABLED)
                }
                isVisible = item.icon.isNotEmpty()
            }

            itemView.setOnClickListener {
                clickListener.invoke(item)
            }
        }
    }
}