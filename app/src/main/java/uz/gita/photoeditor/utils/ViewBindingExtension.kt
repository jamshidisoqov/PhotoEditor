package uz.gita.photoeditor.utils

import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ViewBinding.scope(block: () -> Unit) {
    block.invoke()
}
