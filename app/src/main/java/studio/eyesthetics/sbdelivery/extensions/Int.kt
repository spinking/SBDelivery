package studio.eyesthetics.sbdelivery.extensions

import android.content.res.Resources
import android.util.TypedValue

val Int.spToPixels: Float
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity)

val Int.dpToPixels: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.pxToDimensionPixels: Int
    get() = (this / Resources.getSystem().displayMetrics.density.toInt())

fun Int.dpToPx() : Int {
    val displayMetrics = Resources.getSystem().displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), displayMetrics).toInt()
}

/*fun Int.formatToCost() : String {
    return "$this.00 ₽"
}*/
