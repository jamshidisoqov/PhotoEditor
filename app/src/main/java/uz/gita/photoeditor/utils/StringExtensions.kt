package uz.gita.photoeditor.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Sherzodbek Muhammadiev on 28.01.2020
 */


/**
 * Extension method to check if String is Email.
 */
fun String.isEmail(): Boolean {
    val p = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)\$".toRegex()
    return matches(p)
}


/**
 * Extension method to check if String is Number.
 */
fun String.isNumeric(): Boolean {
    val p = "^[0-9]+$".toRegex()
    return matches(p)
}


/**
 * check password contains number, uppercase and lowercase letters, and width 8
 */
fun String.isPassword(): Boolean {
    val p = "(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[^a-zA-Z]).{8,}".toRegex()
    return matches(p)
}

fun String.isNotPassword() = !isPassword()

///**
// * check to email address
// */
//
//fun String.isEmail(): Boolean {
//    val email = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
//    return matches(email.toRegex())
//}

//private val codes = arrayOf("71", "73", "99", "98", "95", "97", "91", "90", "93", "94", "33")
private val codes = arrayOf("99", "98", "97", "91", "90", "93", "94")
fun String.isPhone(): Boolean {
    val c13 =
        length == 13 && startsWith("+998") && takeLast(12).isNumber() && substring(4, 6) in codes
    val c12 = length == 12 && startsWith("998") && isNumber() && substring(3, 5) in codes
    val c9 = length == 9 && isNumber() && take(2) in codes
    return c9 || c12 || c13
}


fun String.isNotPhone() = !isPhone()
fun String.isNumber() = matches(Regex("^\\d+$"))
fun String.isNotNumber() = !isNumber()
fun String.toFullPhone(): String? {
    when (length) {
        13 -> if (startsWith("+998") && takeLast(12).isNumber() && substring(
                4,
                6
            ) in codes
        ) return this
        12 -> if (startsWith("998") && takeLast(12).isNumber() && substring(
                3,
                5
            ) in codes
        ) return "+$this"
        9 -> if (isNumber() && take(2) in codes) return "+998$this"
        else -> return null
    }
    return null
}

fun String.toRequestPhone(): String? {
    when (length) {
        9 -> if (takeLast(9).isNumber() && take(2) in codes) return "998$this"
        12 -> if (startsWith("998") && takeLast(12).isNumber() && substring(
                3,
                5
            ) in codes
        ) return this
    }

    return null
}

fun String.toRawPhone(): String = this.replace(" ", "")

fun String.toRawCardPan(): String = this.replace(" ", "")

fun String.isCardExpireDate(): Boolean = matches(Regex("(0[1-9]|10|11|12)/[0-9]{2}\$"))

/**
 * to card expire date format
 */

fun String.toCardExpireDate(): String = "${this.take(2)}/${this.takeLast(2)}"

/**
 * if it is phone number then returns masked phone number else returns null
 * */
fun String.toMaskedPhone(mask: Char = '*'): String? {
    val full = toFullPhone() ?: return null
    val pref = full.take(4)
    val code = full.substring(4, 6)
    val num = full.takeLast(2)
    return "$pref $code $mask$mask$mask $mask$mask $num"
}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}

val String.isVideo
    get() = takeLast(4).lowercase().trim() in arrayOf(
        ".mp4",
        ".avi",
        ".3gp",
        ".mpg",
        "mpeg"
    )
val String.isPhoto
    get() = takeLast(4).lowercase().trim() in arrayOf(
        ".jpg",
        ".png",
        ".gif",
        ".bmp",
        "jpeg"
    )


fun String.toFormat(template: String, mask: Char = '*'): String {
    if (template.isEmpty() || template.indexOf(mask) == -1) return this
    val result = StringBuilder()
    var index = 0
    template.forEach { c ->
        if (index >= length) return this
        result.append(if (c == mask) this[index++] else c)
    }
    return result.toString()
}

fun String.isUzCard() =
    this.matches(Regex("^8600 [0-9]{4} [0-9]{4} [0-9]{4}$")) || this.matches(Regex("^8600[0-9]{4}[0-9]{4}[0-9]{4}$"))


fun String.toMaskCard(isFull: Boolean = false, mask: Char = '*') =
    if (isFull) toFormat("**** **** **** ****", mask) else takeLast(8).toFormat("**** ****", mask)

fun String.toDateLong(): Long {
    val date1: Date = SimpleDateFormat("dd.MM.yyyy").parse(this)
    return date1.time / 1000
}

fun String.boldString(query: String?): Spannable {
    val spannable = SpannableStringBuilder(this)
    val q = query ?: return spannable
    val start = this.indexOf(q)
    val end = query.length + start
    spannable.setSpan(
        StyleSpan(Typeface.BOLD),
        start, end,
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )

    return spannable

}


fun String.titlecaseFirstCharIfItIsLowercase() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
}

