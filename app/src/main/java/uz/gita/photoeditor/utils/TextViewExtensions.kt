package uz.gita.photoeditor.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.core.content.ContextCompat
import timber.log.Timber

/**
 * Created by Sherzodbek Muhammadiev on 28.01.2020
 */


/**
 * Extension method to set different color for substring TextView.
 */
fun TextView.setColorOfSubstring(substring: String, color: Int) {
    try {
        val spannable = SpannableString(text)
        val start = text.indexOf(substring)
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, color)), start, start + substring.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        text = spannable
    } catch (e: Exception) {
        Timber.d("exception in setColorOfSubstring, text=$text, substring=$substring")
    }
}


/**
 * Extension method to set different color for substring TextView.
 */
fun String.toColorString( color: Int): SpannableString {
    val spannable = SpannableString(this)
    try {
        spannable.setSpan(ForegroundColorSpan(color), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    } catch (e: Exception) {
        Timber.d("exception in setColorOfSubstring, text=$this")
    }
    return spannable
}
