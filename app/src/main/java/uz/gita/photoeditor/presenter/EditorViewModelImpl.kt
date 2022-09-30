package uz.gita.photoeditor.presenter

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber
import uz.gita.photoeditor.navigation.Navigator
import uz.gita.photoeditor.ui.editor.EditorViewModel
import uz.gita.photoeditor.ui.tools.emoji.Emoji
import uz.gita.photoeditor.utils.eventFlow
import uz.gita.photoeditor.utils.eventValueFlow
import javax.inject.Inject

@HiltViewModel
class EditorViewModelImpl @Inject constructor(
    private val navigator: Navigator
) : EditorViewModel, ViewModel() {

    private var counter = 0

    override val imgUriFlow = eventValueFlow<Uri>()
    override val addEmojiFlow = eventValueFlow<Emoji>()
    override val removeEmojiFlow = eventValueFlow<Emoji>()
    override val unselectAllFlow = eventFlow()
    override val imgModifyingFlow = eventValueFlow<Boolean>()

    private val listEmojis = mutableListOf<Emoji>()

    private var isModifying = false

    override fun setImage(imgUri: Uri) {
        Timber.d("Setting Uri")
        viewModelScope.launch {
            imgUriFlow.emit(imgUri)
        }
    }

    override fun addEmoji(emoji: Emoji) {
        viewModelScope.launch {
            isModifying = true
            imgModifyingFlow.emit(isModifying)
            emoji.tag = ++counter
            listEmojis.add(emoji)
            addEmojiFlow.emit(emoji)
        }
    }


    override fun removeEmoji(emoji: Emoji) {
        Timber.d("Remove emoji $emoji")
        viewModelScope.launch {
            isModifying = true
            imgModifyingFlow.emit(isModifying)
            listEmojis.remove(emoji)
            removeEmojiFlow.emit(emoji)
        }
    }

    override fun unselectAll() {
        Timber.d("Unselect")
        viewModelScope.launch {
            unselectAllFlow.emit(Unit)
        }
    }

    override fun setModified() {
        viewModelScope.launch {
            isModifying = true
            imgModifyingFlow.emit(isModifying)
        }
    }

    override fun back() {
        viewModelScope.launch {
            navigator.back()
        }
    }

}