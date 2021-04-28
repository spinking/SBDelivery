package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.view.View
import kotlinx.android.synthetic.main.item_suggestion.*
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.database.entities.SuggestionEntity
import studio.eyesthetics.sbdelivery.ui.base.BaseAdapterDelegate

class SuggestionDelegate(
    private val deleteClickListener: (SuggestionEntity) -> Unit,
    private val clickListener: (SuggestionEntity) -> Unit
) : BaseAdapterDelegate<SuggestionEntity>() {
    override val layoutRes: Int = R.layout.item_suggestion

    override fun createHolder(view: View): ViewHolder = SuggestionViewHolder(view)

    override fun onBindViewHolder(
        item: SuggestionEntity,
        holder: ViewHolder,
        payloads: MutableList<Any>
    ) {
        val viewHolder = holder as SuggestionViewHolder
        viewHolder.bind(item)
    }

    inner class SuggestionViewHolder(convertView: View) : ViewHolder(convertView) {
        fun bind(item: SuggestionEntity) {
            tv_suggestion.text = item.suggestion

            btn_delete.setOnClickListener {
                deleteClickListener.invoke(item)
            }

            itemView.setOnClickListener {
                clickListener.invoke(item)
            }
        }
    }
}