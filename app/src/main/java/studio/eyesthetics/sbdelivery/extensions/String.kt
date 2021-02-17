package studio.eyesthetics.sbdelivery.extensions

import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

fun String.truncate(index: Int = 16) :String {
    val str = this.trim()
    return if(str.length < index + 1) str else str.substring(0, index).trim () + "..."
}

fun String.truncateWithoutDots(index: Int = 3) :String {
    val str = this.trim()
    return if(str.length < index + 1) str else str.substring(0, index).trim ()
}

fun String.stripHtml(): String {
    var str = this
    str = str.replace(Regex("<.*?>"), "")
    str = str.replace(Regex("&.*?;"), "")
    str = str.replace(Regex("\\s+"), " ")
    return str
}

/*
fun String.formatDateWithTime() : String {

    val currDate = ISO8601Utils.parse(this, ParsePosition(0))
    val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val date = simpleDateFormat.format(currDate)

    val pattern = "(?<=T)(\\d{2}:\\d{2})".toRegex()
    val res = pattern.find(this, 0)
    var time = res?.value
    if(time == null) time = ""

    return "$time   $date"
}

fun String.formatDate() : String {
    val currDate = ISO8601Utils.parse(this, ParsePosition(0))
    val simpleDateFormat = SimpleDateFormat("d MMMM yyyy", Locale.getDefault())
    return simpleDateFormat.format(currDate)
}
*/

fun String.validEmail():Boolean {
    val pattern = "^(.+)@(.+)\\.(.+){2,64}$".toRegex()
    return this.matches(pattern)
}

fun String.formatToRub(): String = "$this ₽"

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean = this.length > 5

fun String.isValidName(): Boolean = this.matches("(.)*(\\d)(.)*".toRegex()).not()

/*
fun String.phoneMask() : String {
    val countryCode = this.substring(0, 1)
    val operatorCode = this.substring(1,4)
    val firstPart = this.substring(4, 7)
    val secondPart = this.substring(7, 9)
    val thirdPart = this.substring(9, 11)

    return "+$countryCode ($operatorCode) $firstPart-$secondPart-$thirdPart"

}*/
