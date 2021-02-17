package studio.eyesthetics.sbdelivery.extensions

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop

fun View.setMarginOptionally(left:Int = marginStart, top : Int = marginTop, right : Int = marginEnd, bottom : Int = marginBottom) {
    val layoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    layoutParams.apply {
        leftMargin = left
        topMargin = top
        rightMargin = right
        bottomMargin = bottom
    }
    requestLayout()
}

fun View.setPaddingOptionally(left: Int = paddingStart, top: Int = paddingTop, right: Int = paddingEnd, bottom: Int = paddingBottom) {
    setPadding(left, top, right, bottom)
}

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun View.hideSoftKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}