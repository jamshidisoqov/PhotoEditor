package uz.gita.photoeditor.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateTimeFormat(): String {
    val sdf = SimpleDateFormat("dd/MM, hh:mm")
    sdf.timeZone = TimeZone.getTimeZone("GMT+5")
    val netDate = Date(this * 1000)
    return sdf.format(netDate)
}


fun Long.toDateFormat(): String {
    val sdf = SimpleDateFormat("EE, dd-MMMM")  // Pay, 18-august
    sdf.timeZone = TimeZone.getTimeZone("GMT+5")
    val netDate = Date(this * 1000)
    return sdf.format(netDate)
}

fun Long.toDateMonthAndTimeFormat(): String {
    val sdf = SimpleDateFormat("dd-MMMM, hh:mm")  // Pay, 18-august
    sdf.timeZone = TimeZone.getTimeZone("GMT+5")
    val netDate = Date(this * 1000)
    return sdf.format(netDate)
}

fun Long.toWeekAndDateMonthAndTimeFormat(): String {
    val sdf = SimpleDateFormat("EEEE, dd-MMMM, hh:mm")  // Pay, 18-august
    sdf.timeZone = TimeZone.getTimeZone("GMT+5")
    val netDate = Date(this * 1000)
    return sdf.format(netDate).titlecaseFirstCharIfItIsLowercase()
}

fun Long.toWeekAndDateMonthAndYearFormat(): String {
    val sdf = SimpleDateFormat("EEEE, dd-MMMM yyyy")  // Pay, 18-august
    sdf.timeZone = TimeZone.getTimeZone("GMT+5")
    val netDate = Date(this * 1000)
    return sdf.format(netDate).titlecaseFirstCharIfItIsLowercase()
}

fun Long.toDateOnlyFormat(): String {
    val sdf = SimpleDateFormat("dd")
    sdf.timeZone = TimeZone.getTimeZone("GMT+5")
    val netDate = Date(this * 1000)
    return sdf.format(netDate)
}


