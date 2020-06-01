package studio.eyesthetics.sbdelivery.extensions

import android.content.res.Resources

val Int.spToPixels: Float
    get() = (this * Resources.getSystem().displayMetrics.scaledDensity)

val Int.dpToPixels: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.pxToDimensionPixels: Int
    get() = (this / Resources.getSystem().displayMetrics.density.toInt())

/*fun Int.formatToCost() : String {
    return "$this.00 ₽"
}*/
