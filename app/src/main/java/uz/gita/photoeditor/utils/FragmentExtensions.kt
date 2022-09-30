package uz.gita.photoeditor.utils

import android.annotation.SuppressLint
import android.graphics.Rect
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive


fun Fragment.attachAdapterHorizontal(list: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    list.adapter = adapter
    list.layoutManager =
        LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
}

fun Fragment.attachAdapterVertical(list: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    list.adapter = adapter
    list.layoutManager = LinearLayoutManager(requireContext())
}

fun Fragment.detachAdapter(list: RecyclerView) {
    list.adapter = null
}

fun Fragment.softKeyboardChanges() = channelFlow<Boolean> {
    var lastState = true
    val listener: () -> Unit = listener@{
        val r = Rect()
        view?.getWindowVisibleDisplayFrame(r)
        val screenHeight: Int = view?.rootView?.height ?: 0
        val keypadHeight: Int = screenHeight - r.bottom
        val currentState = isResumed && isActive && keypadHeight > screenHeight * 0.15
        if (lastState == currentState) return@listener
        lastState = currentState
        if (currentState) {
            trySend(true)
        } else {
            trySend(false)
        }
    }
    view?.viewTreeObserver?.addOnGlobalLayoutListener(listener)
    awaitClose { view?.viewTreeObserver?.removeOnGlobalLayoutListener(listener) }
}


@SuppressLint("ResourceType")
fun Fragment.setStatusBarColor(resId: Int) = requireActivity().window.apply {
    statusBarColor = resources.getColor(resId)
}

@SuppressLint("ResourceType")
fun Fragment.setNavigationBarColor(resId: Int) = requireActivity().window.apply {
    navigationBarColor =
        resources.getColor(resId)
}

@SuppressLint("ResourceType")
fun Fragment.setStatusAndNavigationBarColor(resId: Int) = requireActivity().window.apply {
    statusBarColor = resources.getColor(resId)
    navigationBarColor = resources.getColor(resId)
}


fun Fragment.toast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(@StringRes stringResId: Int) {
    Toast.makeText(requireContext(), resources.getString(stringResId), Toast.LENGTH_SHORT).show()
}