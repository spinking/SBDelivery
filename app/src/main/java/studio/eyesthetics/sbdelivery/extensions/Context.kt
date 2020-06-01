package studio.eyesthetics.sbdelivery.extensions

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.dpToPx (dp: Float): Float {
    return TypedValue.applyDimension (TypedValue.COMPLEX_UNIT_DIP , dp, this.resources.displayMetrics)
}

fun Context.color(colorRes: Int) = ContextCompat.getColor(this, colorRes)

val Context.isConnected : Boolean
    get() {
        return(getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .activeNetworkInfo?.isConnected == true
    }

fun Context.dpToIntPx(dp: Int): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        this.resources.displayMetrics
    ).toInt()
}