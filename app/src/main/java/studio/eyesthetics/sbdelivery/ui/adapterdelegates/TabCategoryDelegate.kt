package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.view.View
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.database.entities.CategoryEntity
import studio.eyesthetics.sbdelivery.ui.base.BaseAdapterDelegate

class TabCategoryDelegate : BaseAdapterDelegate<CategoryEntity>() {
    override val layoutRes: Int = R.layout.item_tab_category

    override fun createHolder(view: View): ViewHolder = TabCategoryViewHolder(view)

    override fun onBindViewHolder(
        item: CategoryEntity,
        holder: ViewHolder,
        payloads: MutableList<Any>
    ) {
        val viewHolder = holder as TabCategoryViewHolder
        viewHolder.bind(item)
    }

    inner class TabCategoryViewHolder(convertView: View) : ViewHolder(convertView) {
        fun bind(item: CategoryEntity) {

        }
    }
}