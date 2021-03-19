package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.view.View
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.item_menu.*
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.models.menu.MenuItem
import studio.eyesthetics.sbdelivery.extensions.visible
import studio.eyesthetics.sbdelivery.ui.base.BaseAdapterDelegate

class MenuDelegate(
    private val listener: (Int) -> Unit
) : BaseAdapterDelegate<MenuItem>() {
    override val layoutRes: Int = R.layout.item_menu

    override fun createHolder(view: View): ViewHolder = MenuViewHolder(view)

    override fun onBindViewHolder(item: MenuItem, holder: ViewHolder, payloads: MutableList<Any>) {
        val viewHolder = holder as MenuViewHolder
        viewHolder.bind(item)
    }

    inner class MenuViewHolder(convertView: View) : ViewHolder(convertView) {
        fun bind(item: MenuItem) {

            tv_menu_item.text = itemView.context.getString(item.titleId)
            iv_icon.setImageDrawable(ResourcesCompat.getDrawable(itemView.context.resources, item.iconId, null))
            if (item.badgeCount != -1) {
                val badgeText = "+${item.badgeCount}"
                tv_badge.apply {
                    visible(true)
                    text = badgeText
                }
            }

            itemView.setOnClickListener {
                listener.invoke(item.destinationId)
            }
        }
    }
}