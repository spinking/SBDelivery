package studio.eyesthetics.sbdelivery.extensions

import android.widget.TextView
import studio.eyesthetics.sbdelivery.R

fun TextView.setErrorColor(isError: Boolean) {
    resources.getColor(R.color.color_text_error, null)
    val color = resources.getColor(if (isError) R.color.color_text_error else R.color.color_label_gray, null)
    this.setTextColor(color)
}