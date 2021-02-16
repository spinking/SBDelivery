package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.view.View
import com.bumptech.glide.Glide
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.database.entities.DishEntity
import studio.eyesthetics.sbdelivery.extensions.dpToPx
import studio.eyesthetics.sbdelivery.extensions.setMarginOptionally
import studio.eyesthetics.sbdelivery.ui.base.BaseAdapterDelegate

class RecommendDelegate(
    private val displayWidth: Int = 0,
    private val dishClickListener: (Int) -> Unit
) : BaseAdapterDelegate<DishEntity>() {
    override val layoutRes: Int = R.layout.item_dish

    override fun createHolder(view: View): ViewHolder {
        val params = view.layoutParams
        val width = ((displayWidth - 20.dpToPx()) * 0.45).toInt()
        params.width = width
        view.layoutParams = params
        when (currentPosition) {
            0 ->  view.setMarginOptionally(left = DEFAULT_LEFT_MARGIN.dpToPx())
            listSize - 1 -> view.setMarginOptionally(right = DEFAULT_RIGHT_MARGIN.dpToPx())
        }
        return RecommendViewHolder(view)
    }

    override fun onBindViewHolder(item: DishEntity, holder: ViewHolder, payloads: MutableList<Any>) {
        val viewHolder = holder as RecommendViewHolder
        viewHolder.bind(item)
    }

    inner class RecommendViewHolder(convertView: View) : ViewHolder(convertView) {
        fun bind(item: DishEntity) {

/*            Glide.with(itemView.context)
                .load(item.photo)
                .placeholder(R.drawable.ic_placeholder_edit_profile)
                .into(iv_course)

            itemView.setOnClickListener {
                courseClickListener.invoke(item.id)
            }*/
        }
    }

    companion object {
        private const val DEFAULT_LEFT_MARGIN = 16
        private const val DEFAULT_RIGHT_MARGIN = 8
    }
}