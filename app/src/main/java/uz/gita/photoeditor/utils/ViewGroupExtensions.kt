package uz.gita.photoeditor.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.children

fun ViewGroup.inflate(@LayoutRes resId: Int) =
    LayoutInflater.from(context).inflate(resId, this, false)

fun ViewGroup.removeViewByTag(tag: Any) {
    for (i in children) {
        if (i.tag == tag) {
            removeView(i)
        }
    }
}
