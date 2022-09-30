package uz.gita.photoeditor.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

fun eventFlow() = MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

fun <T> eventValueFlow() = MutableSharedFlow<T>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

fun <T> MutableSharedFlow<T>.click(value: T, scope: CoroutineScope) {
    scope.launch { emit(value) }
}

fun MutableSharedFlow<Unit>.click(scope: CoroutineScope) { scope.launch { emit(Unit) } }
suspend fun MutableSharedFlow<Unit>.click() { emit(Unit) }