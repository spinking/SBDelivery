package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.view.View
import kotlinx.android.synthetic.main.item_tab_category.*
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.database.entities.CategoryEntity
import studio.eyesthetics.sbdelivery.ui.adapterdelegates.managers.CategoryClickManager
import studio.eyesthetics.sbdelivery.ui.base.BaseAdapterDelegate

class TabCategoryDelegate(
    private val displayWidth: Int = 0,
    private val buttonManager: CategoryClickManager,
    private val clickListener: (String) -> Unit
) : BaseAdapterDelegate<CategoryEntity>() {
    override val layoutRes: Int = R.layout.item_tab_category
    private var currentWidth: Int = 0

    override fun createHolder(view: View): ViewHolder {
        currentWidth = ((displayWidth / listSize))
        val params = view.layoutParams
        params.width = currentWidth
        view.layoutParams = params
        return TabCategoryViewHolder(view)
    }

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
            tv_tab_title.text = item.name
            if (currentPosition == 0) {
                buttonManager.initButton(tv_tab_title)
                clickListener.invoke(item.id)
            }

            itemView.setOnClickListener {
                buttonManager.checkButton(tv_tab_title)
                clickListener.invoke(item.id)
            }
        }
    }
}