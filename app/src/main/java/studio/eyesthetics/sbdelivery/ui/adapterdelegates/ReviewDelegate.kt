package studio.eyesthetics.sbdelivery.ui.adapterdelegates

import android.view.View
import kotlinx.android.synthetic.main.item_review.*
import studio.eyesthetics.sbdelivery.R
import studio.eyesthetics.sbdelivery.data.database.entities.ReviewEntity
import studio.eyesthetics.sbdelivery.extensions.formatToDate
import studio.eyesthetics.sbdelivery.ui.base.BaseAdapterDelegate

class ReviewDelegate : BaseAdapterDelegate<ReviewEntity>() {
    override val layoutRes: Int = R.layout.item_review

    override fun createHolder(view: View): ViewHolder = ReviewViewHolder(view)

    override fun onBindViewHolder(item: ReviewEntity, holder: ViewHolder, payloads: MutableList<Any>) {
        val viewHolder = holder as ReviewViewHolder
        viewHolder.bind(item)
    }

    inner class ReviewViewHolder(convertView: View) : ViewHolder(convertView) {
        fun bind(item: ReviewEntity) {
            //TODO add date
            val nameText = "${item.author}, ${item.date.formatToDate()}"
            tv_name.text = nameText
            rb_rate_user.numStars = item.rating
            tv_review_description.text = item.text
        }
    }
}