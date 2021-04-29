package studio.eyesthetics.sbdelivery.extensions

import kotlin.math.pow
import kotlin.math.roundToInt

fun Float.roundTo(numFractionDigits: Int = 2): Float {
    val factor = 10f.pow(numFractionDigits.toFloat())
    return (this * factor).roundToInt() / factor
}