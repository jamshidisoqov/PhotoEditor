package uz.gita.photoeditor.utils

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets

/**
 * Created by Sherzodbek Muhammadiev on 28.01.2020
 */


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.disable() {
    alpha = 0.16f
    isClickable = false
    isEnabled = false
}

fun View.enable() {
    alpha = 1f
    isClickable = true
    isEnabled = true
}

fun View.pressed() {
    alpha = 0.5f
}

fun View.loading() {
    alpha = 0.5f
    isClickable = false
    isEnabled = false
}

fun View.enableOrDisable(state: Boolean) = if (state) enable() else disable()
fun View.disableOrEnable(state: Boolean) = if (state) disable() else enable()
fun View.visibleOrGone(state: Boolean) = if (state) visible() else gone()
fun View.goneOrVisible(state: Boolean) = if (state) gone() else visible()
fun View.visibleOrInvisible(state: Boolean) = if (state) visible() else invisible()

inline val View.inflater: LayoutInflater
    get() = (tag as? LayoutInflater) ?: LayoutInflater.from(
        context
    ).apply { tag = this }


@SuppressLint("ClickableViewAccessibility")
fun View.setCustomOnTouchListener(
    block: (Unit) -> Unit
) {
    var rect: Rect? = null
    this.setOnTouchListener { v, event ->

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                rect = Rect(v.left, v.top, v.right, v.bottom)
                pressed()
            }
            MotionEvent.ACTION_UP -> {
                enable()
                if (rect!!.contains(v.left + event.x.toInt(), v.top + event.y.toInt())) {
                    block.invoke(Unit)
                }
            }
        }
        true
    }
}
