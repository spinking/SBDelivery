package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.view.View
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.models.SearchItem
import studio.eyesthetics.sbdelivery.data.models.categories.EmptySearchItem
import studio.eyesthetics.sbdelivery.ui.base.BaseAdapterDelegate

class EmptySearchItemDelegate : BaseAdapterDelegate<SearchItem>() {
    override val layoutRes: Int = R.layout.item_empty_search

    override fun createHolder(view: View): ViewHolder = EmptySearchItemViewHolder(view)

    override fun onBindViewHolder(item: SearchItem, holder: ViewHolder, payloads: MutableList<Any>) {}

    override fun isForViewType(item: SearchItem, items: MutableList<SearchItem>, position: Int): Boolean = item is EmptySearchItem

    inner class EmptySearchItemViewHolder(convertView: View) : ViewHolder(convertView) {}
}